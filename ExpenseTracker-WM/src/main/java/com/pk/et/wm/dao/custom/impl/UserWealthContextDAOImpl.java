package com.pk.et.wm.dao.custom.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import com.pk.et.infra.dao.custom.impl.GenericDAO;
import com.pk.et.infra.model.User;
import com.pk.et.infra.model.User_;
import com.pk.et.wm.dao.custom.IUserWealthContextDAO;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.Portfolio;
import com.pk.et.wm.model.UserWealthContext;
import com.pk.et.wm.model.UserWealthContext_;

public class UserWealthContextDAOImpl extends GenericDAO implements IUserWealthContextDAO{

	public List<Equity> getFavStocks(User user) {
		List<Equity> lst=Collections.emptyList();
		try {
			log.debug("Get the favourite stocks for the user : {}",user);
			Map<String, ? super Object> paramMap = new HashMap<String, Object>();
			paramMap.put("user.id",  user.getId());
			UserWealthContext wealthContext = findUniqueByCriteria(UserWealthContext.class,paramMap);
			if(wealthContext!=null){
				lst=new ArrayList<Equity>();
				for(Equity equity:wealthContext.getFavStocks()){
					lst.add(equity);
				}
			}
		} catch (Exception e) {
			log.error("Error while getting the favourite stocks ==>{}",e.getMessage());
		}
		return lst;
		
	}

	public List<Portfolio> getPortfolios(User user) {
		List<Portfolio> lst=Collections.emptyList();
		try {
			log.debug("Get the favourite stocks for the user : {}",user);
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<Portfolio> cr = cb.createQuery(Portfolio.class);
			Root<UserWealthContext> root = cr.from(UserWealthContext.class);
			Join<UserWealthContext, Portfolio> folios=root.join(UserWealthContext_.portfolios);
			cr.select(folios);
			cr.where(cb.equal(root.get(UserWealthContext_.user).get(User_.id), user.getId()));
			lst=em.createQuery(cr).getResultList();
		} catch (Exception e) {
			log.error("Error while getting the favourite stocks ==>{}",e.getMessage());
		}
		return lst;
		
	}

}
