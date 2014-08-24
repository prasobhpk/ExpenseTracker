package com.pk.et.wm.util;

import java.io.StringReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.pk.et.infra.model.ConfigurationItem;
import com.pk.et.infra.rest.providers.ContextResolverForMOXY;
import com.pk.et.infra.service.IConfigurationItemService;
import com.pk.et.infra.util.ETConstants;
import com.pk.et.infra.util.Message;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.MarkToMarket;
import com.pk.et.wm.model.MarketStatus;

@Component("reddiffMoneyUtil")
public class ReddiffMoneyUtil {
	private static final Logger LOG = LoggerFactory
			.getLogger(ReddiffMoneyUtil.class);
	private String proxy;
	private String port;
	private String proxyUser;
	private String proxyPassword;
	private boolean isProxyUsed;

	@Autowired(required = true)
	@Qualifier("configurationItemService")
	private IConfigurationItemService configurationItemService;

	@Autowired
	@Qualifier("contextResolverForMOXY")
	private ContextResolverForMOXY contextResolverForMOXY;

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;

	@PostConstruct
	@Transactional
	private void init() {
		try {
			final ConfigurationItem proxyUsed = this.configurationItemService
					.findByKey(ETConstants.PROXY_USED_KEY);
			final ConfigurationItem proxyURL = this.configurationItemService
					.findByKey(ETConstants.PROXY_URL_KEY);
			final ConfigurationItem proxyPort = this.configurationItemService
					.findByKey(ETConstants.PROXY_PORT_KEY);
			final ConfigurationItem proxyUser = this.configurationItemService
					.findByKey(ETConstants.PROXY_USER);
			final ConfigurationItem proxyPassword = this.configurationItemService
					.findByKey(ETConstants.PROXY_PASSWORD);
			final boolean proxyActive = Boolean.parseBoolean(proxyUsed
					.getDefaultValue());
			if (proxyActive) {
				this.isProxyUsed = true;
				this.proxy = proxyURL.getDefaultValue();
				this.port = proxyPort.getDefaultValue();
				this.proxyUser = proxyUser.getDefaultValue();
				this.proxyPassword = proxyPassword.getDefaultValue();
				final String httpUser = this.proxyUser;
				final String httpPwd = this.proxyPassword;
				System.setProperty("http.proxyHost", this.proxy);
				System.setProperty("http.proxyPort", this.port);
				System.setProperty("http.proxyUser", this.proxyUser);
				System.setProperty("http.proxyPassword", this.proxyPassword);

				Authenticator.setDefault(new Authenticator() {
					@Override
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(httpUser, httpPwd
								.toCharArray());
					}
				});
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, Equity> readCompanies(final String url) throws Exception {
		final String[] langs = new String[] { "A", "B", "C", "D", "E", "F",
				"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z", "OTHERS" };
		final String baseurl = "http://money.rediff.com/companies/";
		final Map<String, Equity> stocks = new HashMap<String, Equity>();
		Equity stock = null;
		try {
			for (final String lang : langs) {
				final String content = readFromUrl(baseurl + lang.toLowerCase());
				LOG.info("Loading content from :"
						+ (baseurl + lang.toLowerCase()));
				// add new base tag
				final Pattern headPattern = Pattern.compile(
						"<table class=\"dataTable\">(.*?)</table>",
						Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				final Matcher headMatcher = headPattern.matcher(content);

				while (headMatcher.find()) {

					String txt = headMatcher.group();
					txt = txt.substring(txt.indexOf("<tbody>") + 7,
							txt.indexOf("</tbody>"));
					final String[] rows = txt.split("</tr>.*?<tr.*?>");
					for (int j = 0; j < rows.length; j++) {
						rows[j] = rows[j]
								.substring(
										rows[j].indexOf(">",
												rows[j].indexOf("<td")) + 1,
										rows[j].lastIndexOf("</td>") + 1)
								.replaceAll("<$", "");
						final String[] columns = rows[j]
								.split("</td>.*?<td.*?>");
						if (columns.length == 3) {
							String cmpName = columns[0];
							String uniqCode = cmpName.substring(
									cmpName.indexOf("http://") + 7,
									cmpName.indexOf(">"));
							uniqCode = uniqCode.substring(
									uniqCode.lastIndexOf("/") + 1,
									uniqCode.indexOf("\""));
							cmpName = cmpName.substring(
									cmpName.indexOf(">") + 1,
									cmpName.lastIndexOf("</a>"));
							cmpName = cmpName.replaceAll("&amp;", "&");
							stock = new Equity(cmpName.trim(), uniqCode.trim(),
									columns[1].trim(), columns[2].trim());
							stocks.put(stock.getSymbol(), stock);
						}
					}
				}
			}

			return stocks;
		} catch (final Exception e) {
			e.printStackTrace();
			return Collections.emptyMap();
		}
	}

	public Map<String, String> getNSECodeMap() throws Exception {
		final String[] langs = new String[] { "A", "B", "C", "D", "E", "F",
				"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z", "OTHERS" };
		final String baseurl = "http://money.rediff.com/companies/nseall/";
		final Map<String, String> nseCodeMap = new HashMap<String, String>();
		try {
			for (final String lang : langs) {
				final String content = readFromUrl(baseurl + lang.toLowerCase());
				LOG.info("Loading content from :"
						+ (baseurl + lang.toLowerCase()));
				// add new base tag
				final Pattern headPattern = Pattern.compile(
						"<table class=\"dataTable\">(.*?)</table>",
						Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				final Matcher headMatcher = headPattern.matcher(content);

				while (headMatcher.find()) {

					String txt = headMatcher.group();
					txt = txt.substring(txt.indexOf("<tbody>") + 7,
							txt.indexOf("</tbody>"));
					final String[] rows = txt.split("</tr>.*?<tr.*?>");
					for (int j = 0; j < rows.length; j++) {
						rows[j] = rows[j]
								.substring(
										rows[j].indexOf(">",
												rows[j].indexOf("<td")) + 1,
										rows[j].lastIndexOf("</td>") + 1)
								.replaceAll("<$", "");
						final String[] columns = rows[j]
								.split("</td>.*?<td.*?>");
						if (columns.length == 2) {
							final String cmpName = columns[0];
							String uniqCode = cmpName.substring(
									cmpName.indexOf("http://") + 7,
									cmpName.indexOf(">"));
							uniqCode = uniqCode.substring(
									uniqCode.lastIndexOf("/") + 1,
									uniqCode.indexOf("\"")).trim();
							final String nseCode = columns[1].trim();
							if (!StringUtils.isEmpty(nseCode)
									&& !StringUtils.isEmpty(uniqCode)) {
								nseCodeMap.put(uniqCode, nseCode);
							}
						}
					}
				}
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}
		return nseCodeMap;
	}

	private String readFromUrl(final String url) throws RestClientException {
		return this.restTemplate.getForObject(url, String.class);
	}

	public Map<String, ? extends Object> getLiveStocksStatus(
			final String stockList) {
		LOG.info(
				"Proxy >>> {}{}",
				new Object[] { System.getProperty("http.proxyHost"),
						System.getProperty("http.proxyPort") });
		final Message message = new Message();
		String responseText = "";
		try {
			responseText = readFromUrl("http://portfolio.rediff.com/money1/watchlist_status.php?companylist="
					+ stockList);
			message.setStatus(ETConstants.SUCCESS);
			LOG.debug("Response>>>>>", responseText);

		} catch (final RestClientException e) {
			message.setStatus(ETConstants.FAIL);
			message.setMsg("Unable to get live stock details from server.Please contact administrator");
			message.setDetails("Exception details::" + e.getMessage());
			e.printStackTrace();
		}
		final Map<String, ? super Object> responseMap = new HashMap<String, Object>();
		responseMap.put("message", message);
		responseMap.put("stockData", responseText);

		return responseMap;

	}

	public List<MarkToMarket> getMtms(final String stocks) {
		LOG.debug("Get MTMs for {}", stocks);
		try {
			final Map<String, ? extends Object> response = this
					.getLiveStocksStatus(stocks);
			final Message msg = (Message) response.get("message");
			String mtmJsonString = "{}";
			if (msg.getStatus().equals(ETConstants.SUCCESS)) {
				final String jsonStockDetails = response.get("stockData")
						.toString();
				// general method, same as with data binding
				final ObjectMapper mapper = new ObjectMapper();
				final JsonNode rootNode = mapper.readValue(jsonStockDetails,
						JsonNode.class);
				final String player = rootNode.get(0).toString();
				final String mtms = rootNode.get(1).toString();
				// format the json response to match the domain model
				mtmJsonString = "{\"marketPlayer\":" + player + ",\"mtms\":"
						+ mtms + "}";
				LOG.debug("json formatted mtms string : {}", mtmJsonString);
				// umarshall mtms
				final JAXBContext jc = this.contextResolverForMOXY
						.getContext(MarkToMarket.class);
				final Unmarshaller unmarshaller = jc.createUnmarshaller();
				unmarshaller.setProperty("eclipselink.media-type",
						"application/json");
				unmarshaller.setProperty(
						UnmarshallerProperties.JSON_INCLUDE_ROOT, false);
				final StreamSource source = new StreamSource(new StringReader(
						mtmJsonString));
				final JAXBElement<MarketStatus> jaxbElement = unmarshaller
						.unmarshal(source, MarketStatus.class);
				final MarketStatus status = jaxbElement.getValue();
				if (status != null) {
					return status.getMtms();
				}
			} else {
				// log
				LOG.info("Error while getting mtms : {}", msg.getDetails());
			}

		} catch (final Exception e) {
			LOG.info("Error while getting mtms : {}", e.getMessage());
		}
		return Collections.emptyList();
	}

	public String favListAsString(final List<Equity> stocks) {
		final StringBuilder sb = new StringBuilder("");
		for (final Equity stock : stocks) {
			sb.append(stock.getSymbol());
			sb.append("|");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	public String getProxy() {
		return this.proxy;
	}

	public void setProxy(final String proxy) {
		this.proxy = proxy;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(final String port) {
		this.port = port;
	}

	public boolean isProxyUsed() {
		return this.isProxyUsed;
	}

	public void setProxyUsed(final boolean isProxyUsed) {
		this.isProxyUsed = isProxyUsed;
	}

	public void setProxyUser(final String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public void setProxyPassword(final String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

}
