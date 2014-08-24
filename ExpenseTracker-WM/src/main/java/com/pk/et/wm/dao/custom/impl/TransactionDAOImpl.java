package com.pk.et.wm.dao.custom.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.pk.et.infra.dao.ConfigurationDAO;
import com.pk.et.infra.dao.custom.impl.GenericDAO;
import com.pk.et.infra.model.Configuration;
import com.pk.et.infra.model.User;
import com.pk.et.wm.dao.custom.ITransactionDAO;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.Equity_;
import com.pk.et.wm.model.Portfolio;
import com.pk.et.wm.model.Portfolio_;
import com.pk.et.wm.model.Transaction;
import com.pk.et.wm.model.Transaction_;
import com.pk.et.wm.model.UserWealthContext_;

@Repository("transactionDAO")
public class TransactionDAOImpl extends GenericDAO implements
		ITransactionDAO {
	@Autowired(required = true)
	@Qualifier("configurationDAO")
	private ConfigurationDAO configurationDAO;

	public List<Transaction> getByPortfolio(User user, Long portfolioId) {
		log.debug("Entering getByPortfolio({})", user);
		List<Transaction> list = null;
		try {
			Configuration conf = configurationDAO.findByUserAndConfigurationItemConfigKey(user, "SHOW_TOTAL_WITH_FEE_DETAILS");
			boolean iswithFee = true;
			if (conf != null
					&& !Boolean.parseBoolean(conf.getConfigItemValue())) {
				iswithFee = false;
			}
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<Transaction> cr = cb.createQuery(Transaction.class);
			Root<Portfolio> root = cr.from(Portfolio.class);
			Join<Portfolio, Transaction> txns = root
					.join(Portfolio_.transactions);
			// Fetch<Transaction, BrokerageStructure>
			// brs=txns.fetch(Transaction_.brokerageStructure);
			if(iswithFee){
				cr.multiselect(txns.get(Transaction_.instrument),
						cb.sum(txns.get(Transaction_.quantity)),
						cb.sum(txns.get(Transaction_.amount)),
						cb.sum(txns.get(Transaction_.brokerage)),
						cb.sum(txns.get(Transaction_.totalAmount)),
						txns.get(Transaction_.brokerageStructure));
			}else{
				cr.multiselect(txns.get(Transaction_.instrument),
						cb.sum(txns.get(Transaction_.quantity)),
						cb.sum(txns.get(Transaction_.amount)),
						cb.sum(txns.get(Transaction_.brokerage)),
						cb.sum(txns.get(Transaction_.amount)),
						txns.get(Transaction_.brokerageStructure));
			}
			Predicate idRes = cb.equal(
					root.get(Portfolio_.wealthContext).get(UserWealthContext_.id), user.getId());
			Predicate folioRes = cb.equal(root.get(Portfolio_.id), portfolioId);
			cr.where(cb.and(idRes, folioRes));
			cr.groupBy(txns.get(Transaction_.instrument).get(Equity_.id));
			list = em.createQuery(cr).getResultList();
		} catch (Exception e) {
			log.debug("{} :: {}", new Object[] { e.getClass(), e.getMessage() });
			list = Collections.emptyList();
		}
		return list;
	}
	
	public List<Transaction> getByPortfolio(User user,Portfolio portfolio,Equity equity) {
		try {
			Map<String, ? super Object> paramMap = new HashMap<String, Object>();
			//paramMap.put("user.id", user.getId());
			paramMap.put("portfolio.id", portfolio.getId());
			paramMap.put("instrument.id", equity.getId());
			return findByCriteria(Transaction.class,paramMap);
		} catch (Exception e) {
			log.debug("{} :: {}", new Object[] { e.getClass(), e.getMessage() });
			return Collections.emptyList();
		}
	}

}
