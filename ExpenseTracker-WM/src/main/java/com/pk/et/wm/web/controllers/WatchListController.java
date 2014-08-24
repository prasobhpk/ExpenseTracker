package com.pk.et.wm.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pk.et.infra.model.User;
import com.pk.et.infra.service.IUserContextService;
import com.pk.et.infra.util.ETConstants;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.service.IUserWealthContextService;
import com.pk.et.wm.util.ReddiffMoneyUtil;

@Controller
@RequestMapping(value = "/wm")
public class WatchListController {

	@Autowired(required = true)
	@Qualifier("reddiffMoneyUtil")
	private ReddiffMoneyUtil reddiffMoneyUtil;

	@Autowired(required = true)
	@Qualifier("userContextService")
	private IUserContextService userContextService;

	@Autowired(required = true)
	@Qualifier("wealthContextService")
	private IUserWealthContextService wealthContextService;

	@RequestMapping(value = "/watchlist", method = RequestMethod.GET)
	public ModelAndView showMarket() {
		User user = userContextService.getCurrentUser();
		ModelAndView mv = new ModelAndView("liveMarket");
		//to show the menu
		mv.addObject("showLiveBox", true);
		List<Equity> stocks = wealthContextService.getFavStocks(user);
		mv.addObject("cmpList",stocks);
		mv.addObject("cmpListStr", reddiffMoneyUtil.favListAsString(stocks));
		mv.addObject(ETConstants.CURRENT_VIEW, "wm/watchlist");
		return mv;
	}
	@RequestMapping(value = "/watchlist/add", method = RequestMethod.GET)
	public ModelAndView addFav(
			@RequestParam(value = "id") String cmpCode) {
		User user = userContextService.getCurrentUser();
		ModelAndView mv = new ModelAndView("liveMarket");
		//to show the menu
		mv.addObject("showLiveBox", true);
		if (cmpCode != null && !"".equals(cmpCode)) {
			wealthContextService.addFavStock(cmpCode, user);
		}
		List<Equity> companies = wealthContextService.getFavStocks(user);
		mv.addObject("cmpList", companies);
		mv.addObject("cmpListStr",reddiffMoneyUtil.favListAsString(companies));
		mv.addObject(ETConstants.CURRENT_VIEW, "wm/watchlist");
		return mv;
	}
	@RequestMapping(value = "/watchlist/delete", method = RequestMethod.GET)
	public ModelAndView deleteFav(
			@RequestParam(value = "id") String cmpCode) {
		User user = userContextService.getCurrentUser();
		ModelAndView mv = new ModelAndView("liveMarket");
		//to show the menu
		mv.addObject("showLiveBox", true);
		if (cmpCode != null && !"".equals(cmpCode)) {
			wealthContextService.deleteFavStock(cmpCode, user);
		}
		List<Equity> companies = wealthContextService.getFavStocks(user);
		mv.addObject("cmpList", companies);
		mv.addObject("cmpListStr",reddiffMoneyUtil.favListAsString(companies));
		mv.addObject(ETConstants.CURRENT_VIEW, "wm/watchlist");
		return mv;
	}

	@RequestMapping("/watchlist/liveStatus")
	public ModelAndView getLiveStockStatus(@RequestParam("companylist") String list){
		ModelAndView mv=new ModelAndView("stockList");
		mv.addAllObjects(reddiffMoneyUtil.getLiveStocksStatus(list));
		return mv;
	}
	
}
