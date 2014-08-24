package com.pk.et.wm.web.controllers;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pk.et.infra.model.User;
import com.pk.et.infra.service.IUserContextService;
import com.pk.et.infra.util.ETConstants;
import com.pk.et.infra.util.Message;
import com.pk.et.wm.exceptions.WMException;
import com.pk.et.wm.model.BrokerageStructure;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.Transaction;
import com.pk.et.wm.service.IBrokerageStructureService;
import com.pk.et.wm.service.IEquityService;
import com.pk.et.wm.service.IHoldingService;
import com.pk.et.wm.service.IPortfolioService;
import com.pk.et.wm.service.ITransactionService;
import com.pk.et.wm.util.ReddiffMoneyUtil;

@Controller
@RequestMapping("/wm")
public class EquityController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired(required = true)
	@Qualifier("userContextService")
	private IUserContextService userContextService;
	
	@Autowired
	@Qualifier("brokerageStructureService")
	private IBrokerageStructureService brokerageStructureService;
	@Autowired
	@Qualifier("equityService")
	private IEquityService equityService;

	@Autowired(required = true)
	@Qualifier("reddiffMoneyUtil")
	private ReddiffMoneyUtil reddiffMoneyUtil;

	@Autowired(required = true)
	@Qualifier("portfolioService")
	private IPortfolioService portfolioService;

	@Autowired(required = true)
	@Qualifier("transactionService")
	private ITransactionService transactionService;

	@Autowired(required = true)
	@Qualifier("holdingService")
	private IHoldingService holdingService;
	
	@RequestMapping(value = "/equity", method = RequestMethod.GET)
	public ModelAndView showEquityForm(@RequestParam("symbol") String symbol) {
		User user=userContextService.getCurrentUser();
		ModelAndView mv = new ModelAndView("stockForm");
		Transaction txn = new Transaction();
		BrokerageStructure structure=new BrokerageStructure();
		txn.setBrokerageStructure(structure);
		//to show the menu
		mv.addObject("showLiveBox", true);
		mv.addObject("stock", equityService.getBySymbol(symbol));
		mv.addObject("txnDetails", txn);
		mv.addObject("brokList", brokerageStructureService.getBrokerList());
		mv.addObject("portfolios", portfolioService.getPortfolios(user));
		mv.addObject("jsonStockDtls",
				reddiffMoneyUtil.getLiveStocksStatus(symbol).get("stockData"));
		mv.addObject(ETConstants.CURRENT_VIEW,"/wm/equity");
		return mv;
	}

	@RequestMapping(value = "/equity", method = RequestMethod.POST)
	public ModelAndView buyEquity(HttpSession session,
			@ModelAttribute("txnDetails") @Valid Transaction txn, BindingResult result) {
		User user=userContextService.getCurrentUser();
		ModelAndView mv = new ModelAndView("equity");
		Message msg = new Message();
		if (result.hasErrors()) {
			mv.setViewName("stockForm");
		} else {
			// else save the transaction
			txn.setAmount(txn.getPrice().multiply(txn.getQuantity()));
			try {
				mv.setView(new RedirectView("portfolios"));
				txn=transactionService.processTransaction(txn,user);
				msg.setMsg("Transaction has been successfully processed....");
			} catch (WMException e) {
				mv.setViewName("stockForm");
				msg.setMsg(e.getMessage());
				msg.setStatus(ETConstants.ERROR);
				msg.setDetails(e.getClass()+" :: "+e.getMessage());
				log.debug("{} :: {}", new Object[] { e.getClass(), e.getMessage() });
				session.setAttribute(ETConstants.Exception_MSG_KEY, msg);
				return mv;
			}catch (Exception e) {
				mv.setViewName("stockForm");
				msg.setMsg("Error while processing transaction....");
				msg.setStatus(ETConstants.ERROR);
				msg.setDetails(e.getClass()+" :: "+e.getMessage());
				log.debug("{} :: {}", new Object[] { e.getClass(), e.getMessage() });
				session.setAttribute(ETConstants.Exception_MSG_KEY, msg);
				return mv;
			}
		}
		session.setAttribute(ETConstants.ACTION_MSG_KEY, msg);
		return mv;
	}

	@RequestMapping(value = "/equity", method = RequestMethod.PUT)
	public ModelAndView updateEquity(
			@ModelAttribute("stock") @Valid Equity stock, BindingResult result) {
		ModelAndView mv = new ModelAndView("equity");
		//to show the menu
		mv.addObject("showLiveBox", true);

		Message msg = new Message();
		// code to update the stock

		msg.setMsg("message");
		mv.addObject("message", msg);
		return mv;
	}

	@RequestMapping(value = "/equity", method = RequestMethod.DELETE)
	public ModelAndView deleteEquity(@RequestParam("stockId") Long stockId) {
		ModelAndView mv = new ModelAndView("equity");
		//to show the menu
		mv.addObject("showLiveBox", true);

		// code to delete the stock

		return mv;
	}
	
	

}
