package com.pk.et.wm.web.controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pk.et.infra.model.User;
import com.pk.et.infra.service.IUserContextService;
import com.pk.et.infra.util.ETConstants;
import com.pk.et.infra.util.Message;
import com.pk.et.wm.model.Holding;
import com.pk.et.wm.model.Portfolio;
import com.pk.et.wm.service.IBrokerageStructureService;
import com.pk.et.wm.service.IHoldingService;
import com.pk.et.wm.service.IPortfolioService;
import com.pk.et.wm.util.ReddiffMoneyUtil;

@Controller
@RequestMapping(value = "/wm")
public class PortfolioController {

	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired(required = true)
	@Qualifier("userContextService")
	private IUserContextService userContextService;

	@Autowired
	@Qualifier("brokerageStructureService")
	private IBrokerageStructureService brokerageStructureService;

	// @Autowired
	// @Qualifier("equityService")
	// private IEquityService equityService;

	@Autowired(required = true)
	@Qualifier("reddiffMoneyUtil")
	private ReddiffMoneyUtil reddiffMoneyUtil;

	@Autowired(required = true)
	@Qualifier("portfolioService")
	private IPortfolioService portfolioService;

	// @Autowired(required = true)
	// @Qualifier("transactionService")
	// private ITransactionService transactionService;

	@Autowired(required = true)
	@Qualifier("holdingService")
	private IHoldingService holdingService;

	@RequestMapping(value = "/portfolios", method = RequestMethod.GET)
	public ModelAndView showPortfolios() {
		final ModelAndView mv = new ModelAndView("portfolios");
		final User user = this.userContextService.getCurrentUser();
		final List<Tuple> portfolios = this.holdingService
				.getPortfolioHoldingSummary(user);
		final List<Portfolio> lstpf = this.portfolioService.getPortfolios(user);
		final Map<Long, Portfolio> map = new HashMap<Long, Portfolio>();
		for (final Portfolio p : lstpf) {
			map.put(p.getId(), p);
		}
		final Map<Portfolio, BigDecimal> summary = new HashMap<Portfolio, BigDecimal>();
		for (final Tuple tuple : portfolios) {
			summary.put(map.get(tuple.get("portfolio", Long.class)),
					tuple.get("total", BigDecimal.class));
		}
		mv.addObject("summaryMap", summary);
		mv.addObject(ETConstants.CURRENT_VIEW, "wm/portfolios");
		return mv;
	}

	@RequestMapping(value = "/portfolio", method = RequestMethod.GET)
	public ModelAndView showPortfolioForm() {
		final ModelAndView mv = new ModelAndView("portfolioForm");
		final Portfolio portfolio = new Portfolio();
		mv.addObject("portfolio", portfolio);
		mv.addObject(ETConstants.CURRENT_VIEW, "wm/portfolio");
		return mv;
	}

	@RequestMapping(value = "/portfolio", method = RequestMethod.POST)
	public ModelAndView addPortfolio(final HttpSession session,
			@ModelAttribute("portfolio") @Valid Portfolio portfolio,
			final BindingResult result) {
		final ModelAndView mv = new ModelAndView("portfolioForm");
		final Message message = new Message();
		if (result.hasErrors()) {
			mv.setViewName("portfolioForm");
		} else {
			final User user = this.userContextService.getCurrentUser();
			try {
				portfolio = this.portfolioService.addPortfolio(portfolio, user);
				mv.setView(new RedirectView("portfolio"));
				message.setMsg("New Portfolio has been successfully saved...");
				this.log.debug("{} has been added successfully..", portfolio);
			} catch (final Exception e) {
				mv.setViewName("portfolioForm");
				message.setMsg("Error while saving portfolio....");
				message.setStatus(ETConstants.FAIL);
				message.setDetails(e.getClass() + " :: " + e.getMessage());
				this.log.debug("{}", message);
			}
			session.setAttribute("actionMSG", message);
		}
		return mv;
	}

	@RequestMapping(value = "/folioHoldings", method = RequestMethod.GET)
	public ModelAndView getFolioHoldings(
			@RequestParam("folioId") final Long folioId) {
		final ModelAndView mv = new ModelAndView("folioTxns");
		final User user = this.userContextService.getCurrentUser();
		final List<Holding> holdings = this.holdingService.getHoldingsByfolio(
				user, folioId);
		final StringBuilder stockList = new StringBuilder("");
		for (final Holding holding : holdings) {
			stockList.append(holding.getInstrument().getSymbol());
			stockList.append("|");
		}
		if (stockList.length() > 0) {
			stockList.deleteCharAt(stockList.length() - 1);
		}
		final Map<String, ? extends Object> response = this.reddiffMoneyUtil
				.getLiveStocksStatus(stockList.toString());
		Map<String, BigDecimal> priceMap = null;
		final Message msg = (Message) response.get("message");
		if (msg.getStatus().equals(ETConstants.SUCCESS)) {
			final String jsonStockDetails = response.get("stockData")
					.toString();
			// general method, same as with data binding
			final ObjectMapper mapper = new ObjectMapper();
			// (note: can also use more specific type, like ArrayNode or
			// ObjectNode!)
			try {
				final JsonNode rootNode = mapper.readValue(jsonStockDetails,
						JsonNode.class);
				priceMap = new HashMap<String, BigDecimal>();
				for (final JsonNode jsonStock : rootNode.get(1)) {
					priceMap.put(jsonStock.path("CompanyCode").getTextValue(),
							BigDecimal.valueOf(Double.parseDouble(jsonStock
									.path("LastTradedPrice").getTextValue())));
				}

			} catch (final Exception e) {
				// logger.debug("Error {} :: {} ",new
				// Object[]{e.getClass(),e.getMessage()});
				e.printStackTrace();
			}
		}

		final Map<String, BigDecimal> feeMap = new HashMap<String, BigDecimal>();
		for (final Holding holding : holdings) {
			final BigDecimal buyFee = holding.getFee();
			final BigDecimal mktValue = holding.getQuantity().multiply(
					priceMap.get(holding.getInstrument().getSymbol()));
			final BigDecimal brokerageForCurPrice = this.brokerageStructureService
					.calculateBrokerage(mktValue,
							holding.getBrokerageStructure());
			feeMap.put(holding.getInstrument().getSymbol(),
					buyFee.add(brokerageForCurPrice));
		}
		mv.addObject("feeMap", feeMap);
		mv.addObject("priceMap", priceMap);
		mv.addObject("holdings", holdings);
		return mv;
	}

	@RequestMapping(value = "/folioSummaryDB", method = RequestMethod.GET)
	public ModelAndView showFolioSummaryDB(
			@RequestParam("folioId") final Long folioId) {
		final ModelAndView mv = new ModelAndView("folioSummaryDB");
		final Portfolio folio = this.portfolioService.getPortfolio(folioId);
		mv.addObject("folio", folio);
		return mv;
	}

	@RequestMapping(value = "/folioPerformanceDB", method = RequestMethod.GET)
	public ModelAndView showFolioPerformanceDB(
			@RequestParam("folioId") final Long folioId) {
		final ModelAndView mv = new ModelAndView("folioPerformanceDB");
		final Portfolio folio = this.portfolioService.getPortfolio(folioId);
		mv.addObject("folio", folio);
		return mv;
	}

}
