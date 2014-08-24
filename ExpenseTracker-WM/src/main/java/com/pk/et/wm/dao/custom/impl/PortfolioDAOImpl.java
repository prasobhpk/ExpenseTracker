package com.pk.et.wm.dao.custom.impl;

import java.util.Collections;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.pk.et.infra.dao.ConfigurationDAO;
import com.pk.et.infra.dao.custom.impl.GenericDAO;
import com.pk.et.infra.model.Configuration;
import com.pk.et.infra.model.User;
import com.pk.et.infra.model.User_;
import com.pk.et.wm.dao.custom.IPortfolioDAO;
import com.pk.et.wm.model.Portfolio;
import com.pk.et.wm.model.Portfolio_;
import com.pk.et.wm.model.Transaction;
import com.pk.et.wm.model.Transaction_;
import com.pk.et.wm.model.UserWealthContext_;

@Repository("portfolioDAO")
public class PortfolioDAOImpl extends GenericDAO implements
		IPortfolioDAO {
	@Autowired(required = true)
	@Qualifier("configurationDAO")
	private ConfigurationDAO configurationDAO;


	public List<Tuple> getPortfolioSummary(User user) {
		log.debug("Entering getByPortfolio({})", user);
		List<Tuple> list = null;
		try {
			Configuration conf = configurationDAO.findByUserAndConfigurationItemConfigKey(user, "SHOW_TOTAL_WITH_FEE_SUMMARY");
			boolean iswithFee = true;
			if (conf != null
					&& !Boolean.parseBoolean(conf.getConfigItemValue())) {
				iswithFee = false;
			}
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<Tuple> cr = cb.createTupleQuery();
			Root<Portfolio> root = cr.from(Portfolio.class);
			Join<Portfolio, Transaction> txns = root
					.join(Portfolio_.transactions);
			// Path<Portfolio> portfolio=root;
			if(iswithFee){
				cr.multiselect(root.alias("portfolio"),
						cb.sum(txns.get(Transaction_.totalAmount)).alias("total"));
			}else{
				cr.multiselect(root.alias("portfolio"),
						cb.sum(txns.get(Transaction_.amount)).alias("total"));
			}
			cr.where(cb.equal(root.get(Portfolio_.wealthContext).get(UserWealthContext_.user).get(User_.id),
					user.getId()));
			cr.groupBy(root.get(Portfolio_.id));
			list = em.createQuery(cr).getResultList();
		} catch (Exception e) {
			log.debug("{} :: {}", new Object[] { e.getClass(), e.getMessage() });
			list = Collections.emptyList();
		}
		log.debug("Leaving getByPortfolio({})", user);
		return list;
	}

}
