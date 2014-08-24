package com.pk.et.wm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.infra.model.User;
import com.pk.et.wm.dao.EquityDAO;
import com.pk.et.wm.dao.UserWealthContextDAO;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.UserWealthContext;
import com.pk.et.wm.service.IUserWealthContextService;

@Service("wealthContextService")
public class UserWealthContextService implements IUserWealthContextService {
	@Autowired
	private UserWealthContextDAO wealthContextDAO;
	
	@Autowired
	private EquityDAO equityDAO;

	public void addFavStock(String cmpCode, User user) {
		UserWealthContext wealthContext = wealthContextDAO.findByUserId(user.getId());
		Equity cmp = equityDAO.findBySymbol(cmpCode);
		if (cmp != null) {
			wealthContext.addFavStock(cmp);
		} else {
			System.out.println("Company does not exist......");
		}
		
	}

	public void deleteFavStock(String cmpCode, User user) {
		UserWealthContext wealthContext = wealthContextDAO.findByUserId(user.getId());
		Equity cmp = equityDAO.findBySymbol(cmpCode);
		if (cmp != null) {
			wealthContext.getFavStocks().remove(cmp);
		} else {
			System.out.println("Company does not exist in fav list......");
		}
		
	}

	public List<Equity> getFavStocks(User user) {
		return wealthContextDAO.getFavStocks(user);
	}

}
