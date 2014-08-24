package com.pk.et.wm.dao.custom.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.pk.et.infra.dao.ConfigurationDAO;
import com.pk.et.infra.dao.custom.impl.GenericDAO;
import com.pk.et.infra.model.Configuration;
import com.pk.et.infra.model.User;
import com.pk.et.infra.model.User_;
import com.pk.et.wm.dao.custom.IHoldingDAO;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.Holding;
import com.pk.et.wm.model.Holding_;
import com.pk.et.wm.model.Portfolio;
import com.pk.et.wm.model.Portfolio_;
import com.pk.et.wm.model.UserWealthContext_;

@Repository("holdingDAO")
public class HoldingDAOImpl extends GenericDAO implements
		IHoldingDAO {
	
	@Autowired(required = true)
	@Qualifier("configurationDAO")
	private ConfigurationDAO configurationDAO;
	
	public Holding getHolding(User user,Equity equity,Portfolio portfolio) {
		try {
			Map<String, ? super Object> paramMap = new HashMap<String, Object>();
			paramMap.put("wealthContext.user.id", user.getId());
			paramMap.put("portfolio.id", portfolio.getId());
			paramMap.put("instrument.id", equity.getId());
			return findUniqueByCriteria(Holding.class,paramMap,"instrument","brokerageStructure");
		} catch (Exception e) {
			log.debug("{} :: {}", new Object[] { e.getClass(), e.getMessage() });
			return null;
		}
	};
	
	public List<Holding> getHoldingsByfolio(User user,Long folioId){
		try {
			Map<String, ? super Object> paramMap = new HashMap<String, Object>();
			paramMap.put("wealthContext.user.id", user.getId());
			paramMap.put("portfolio.id", folioId);
			return findByCriteria(Holding.class,paramMap,"instrument","brokerageStructure");
		} catch (Exception e) {
			log.debug("{} :: {}", new Object[] { e.getClass(), e.getMessage() });
			return Collections.emptyList();
		}
	}
	public List<Tuple> getPortfolioHoldingSummary(User user) {
		log.debug("Entering getPortfolioHoldingSummary({})", user);
		List<Tuple> list = null;
		try {
			Configuration conf = configurationDAO.findByUserAndConfigurationItemConfigKey(user, "SHOW_TOTAL_WITH_FEE_SUMMARY");
			boolean iswithFee = true;
			if (conf != null
					&& !Boolean.parseBoolean(conf.getConfigItemValue())) {
				iswithFee = false;
			}
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Tuple> cr = cb.createTupleQuery();
			Root<Holding> root = cr.from(Holding.class);
			if(iswithFee){
				cr.multiselect(root.get(Holding_.portfolio).get(Portfolio_.id).alias("portfolio"),
						cb.sum(root.get(Holding_.totalAmount)).alias("total"));
			}else{
				cr.multiselect(root.get(Holding_.portfolio).get(Portfolio_.id).alias("portfolio"),
						cb.sum(root.get(Holding_.amount)).alias("total"));
			}
			cr.where(cb.equal(root.get(Holding_.wealthContext).get(UserWealthContext_.user).get(User_.id),
					user.getId()));
			cr.groupBy(root.get(Holding_.portfolio).get(Portfolio_.id));
			list = em.createQuery(cr).getResultList();
		} catch (Exception e) {
			log.debug("{} :: {}", new Object[] { e.getClass(), e.getMessage() });
			list = Collections.emptyList();
		}
		log.debug("Leaving getPortfolioHoldingSummary({})", user);
		return list;
	}

}
