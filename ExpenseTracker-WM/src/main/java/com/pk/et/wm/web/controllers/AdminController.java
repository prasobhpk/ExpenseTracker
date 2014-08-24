package com.pk.et.wm.web.controllers;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pk.et.infra.model.ConfigurationItem;
import com.pk.et.infra.service.IConfigurationItemService;
import com.pk.et.infra.util.ETConstants;
import com.pk.et.infra.util.Message;
import com.pk.et.wm.model.BrokerageStructure;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.service.IBrokerageStructureService;
import com.pk.et.wm.service.IEquityService;
import com.pk.et.wm.util.ReddiffMoneyUtil;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger LOG = LoggerFactory
			.getLogger(AdminController.class);

	@Autowired(required = true)
	@Qualifier("equityService")
	private IEquityService equityService;

	@Autowired(required = true)
	@Qualifier("reddiffMoneyUtil")
	private ReddiffMoneyUtil reddiffMoneyUtil;

	@Autowired(required = true)
	@Qualifier("brokerageStructureService")
	private IBrokerageStructureService brokerageStructureService;

	@Autowired(required = true)
	@Qualifier("configurationItemService")
	private IConfigurationItemService configurationItemService;

	@RequestMapping(value = "/proxysettings", method = RequestMethod.GET)
	public ModelAndView showProxyForm() {
		final ModelAndView mv = new ModelAndView("proxyForm");
		mv.addObject(ETConstants.CURRENT_VIEW, "admin/proxysettings");
		try {
			final ConfigurationItem proxyURL = this.configurationItemService
					.findByKey(ETConstants.PROXY_URL_KEY);
			final ConfigurationItem proxyPort = this.configurationItemService
					.findByKey(ETConstants.PROXY_PORT_KEY);
			final ConfigurationItem proxyUsed = this.configurationItemService
					.findByKey(ETConstants.PROXY_USED_KEY);
			final ConfigurationItem proxyUser = this.configurationItemService
					.findByKey(ETConstants.PROXY_USER);
			final ConfigurationItem proxyPassword = this.configurationItemService
					.findByKey(ETConstants.PROXY_PASSWORD);
			mv.addObject("proxy", proxyURL.getDefaultValue());
			mv.addObject("port", proxyPort.getDefaultValue());
			mv.addObject("proxyUser", proxyUser.getDefaultValue());
			mv.addObject("proxyPassword", proxyPassword.getDefaultValue());
			mv.addObject("proxyActive",
					Boolean.parseBoolean(proxyUsed.getDefaultValue()));
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/proxysettings", method = RequestMethod.POST)
	public ModelAndView updateProxy(
			@RequestParam(value = "proxyActive", required = false) final boolean proxyActive,
			@RequestParam(value = "proxy") final String proxy,
			@RequestParam(value = "port") final String port,
			@RequestParam(value = "proxyUser") final String user,
			@RequestParam(value = "proxyPassword") final String password,
			final HttpSession session) {
		final ModelAndView mv = new ModelAndView(new RedirectView(
				"proxysettings"));
		if (proxy != null && !"".equals(proxy)) {
			try {
				final ConfigurationItem proxyURL = this.configurationItemService
						.findByKey(ETConstants.PROXY_URL_KEY);
				final ConfigurationItem proxyPort = this.configurationItemService
						.findByKey(ETConstants.PROXY_PORT_KEY);
				final ConfigurationItem proxyUser = this.configurationItemService
						.findByKey(ETConstants.PROXY_USER);
				final ConfigurationItem proxyPassword = this.configurationItemService
						.findByKey(ETConstants.PROXY_PASSWORD);
				final ConfigurationItem proxyUsed = this.configurationItemService
						.findByKey(ETConstants.PROXY_USED_KEY);
				if (proxyURL != null && proxyUsed != null) {
					proxyURL.setDefaultValue(proxy);
					proxyPort.setDefaultValue(port);
					proxyUser.setDefaultValue(user);
					proxyPassword.setDefaultValue(password);
					proxyUsed.setDefaultValue("true");
					final List<ConfigurationItem> items = new ArrayList<ConfigurationItem>();
					items.add(proxyURL);
					items.add(proxyPort);
					items.add(proxyUser);
					items.add(proxyPassword);
					items.add(proxyUsed);
					this.configurationItemService.save(items);

					this.reddiffMoneyUtil.setProxy(proxy);
					this.reddiffMoneyUtil.setPort(port);
					this.reddiffMoneyUtil.setProxyUser(user);
					this.reddiffMoneyUtil.setProxyPassword(password);
					this.reddiffMoneyUtil.setProxyUsed(true);

					System.setProperty("http.proxyHost", proxy);
					System.setProperty("http.proxyPort", port);
					System.setProperty("http.proxyUser", user);
					System.setProperty("http.proxyPassword", password);

					final String httpUser = user;
					final String httpPwd = password;

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
		final Message message = new Message();
		message.setMsg("Proxy settings have been updated successfully!");
		session.setAttribute(ETConstants.ACTION_MSG_KEY, message);
		return mv;
	}

	@RequestMapping(value = "/setupStockData", method = RequestMethod.GET)
	public ModelAndView showSetupStockForm() {
		final ModelAndView mv = new ModelAndView("setupStockForm");
		mv.addObject(ETConstants.CURRENT_VIEW, "admin/setupStockData");
		return mv;
	}

	@RequestMapping(value = "/setupStockData", method = RequestMethod.POST)
	public @ResponseBody
	Message hackURL(@RequestParam("hack") final String url) {
		final Message msg = new Message();
		try {
			final Map<String, Equity> cmps = this.reddiffMoneyUtil
					.readCompanies(url);
			final Map<String, String> nseCode = this.reddiffMoneyUtil
					.getNSECodeMap();
			for (final String symbol : nseCode.keySet()) {
				final Equity equity = cmps.get(symbol);
				if (equity != null) {
					equity.setNseCode(nseCode.get(symbol));
					LOG.info("Updating NSE CODE for the Stock :{}",
							equity.getName());
				}

			}
			/*
			 * for (Equity company : cmps) { try { try {
			 * equityService.saveCompany(company); } catch (PersistenceException
			 * e) { System.out.println(e.getMessage()); } } catch (Exception e)
			 * { System.out.println(e.getMessage()); } }
			 */
			this.equityService.saveCompanies(new ArrayList<Equity>(cmps
					.values()));
			msg.setMsg("Copmanies details are successfully pulled from rediff money");
		} catch (final Exception e) {
			e.printStackTrace();
			msg.setStatus("error");
			msg.setMsg("Error while getting companies details from rediff money");
		}
		return msg;
	}

	@RequestMapping(value = "/brokerage", method = RequestMethod.GET)
	public ModelAndView showBrokerageForm() {
		final ModelAndView mv = new ModelAndView("brokerageForm");
		final BrokerageStructure brokerageStructure = new BrokerageStructure();
		mv.addObject("brokerage", brokerageStructure);
		mv.addObject(ETConstants.CURRENT_VIEW, "/admin/brokerage");
		return mv;
	}

	@RequestMapping(value = "/brokerage", method = RequestMethod.POST)
	public ModelAndView saveBrokerageForm(
			@ModelAttribute("brokerage") @Valid final BrokerageStructure brokerage,
			final BindingResult result) {
		final ModelAndView mv = new ModelAndView();
		if (result.hasErrors()) {
			mv.setView(new RedirectView("brokerage"));
		} else {
			this.brokerageStructureService.addBrokerage(brokerage);
			mv.setViewName("brokerageForm");
		}
		return mv;
	}

}
