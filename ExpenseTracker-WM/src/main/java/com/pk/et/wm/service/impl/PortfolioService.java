package com.pk.et.wm.service.impl;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.infra.model.User;
import com.pk.et.wm.dao.PortfolioDAO;
import com.pk.et.wm.dao.UserWealthContextDAO;
import com.pk.et.wm.model.Portfolio;
import com.pk.et.wm.model.UserWealthContext;
import com.pk.et.wm.service.IPortfolioService;

@Service("portfolioService")
public class PortfolioService implements IPortfolioService {
	@Autowired(required = true)
	// @Qualifier("portfolioDAO")
	private PortfolioDAO portfolioDAO;

	@Autowired(required = true)
	// @Qualifier("wealthContextDAO")
	private UserWealthContextDAO wealthContextDAO;

	public List<Portfolio> getPortfolios(final User user) {
		return this.wealthContextDAO.getPortfolios(user);
	}

	public Portfolio addPortfolio(final Portfolio portfolio, final User user) {
		final UserWealthContext wealthContext = this.wealthContextDAO
				.findByUserId(user.getId());
		portfolio.setWealthContext(wealthContext);
		return this.portfolioDAO.save(portfolio);
	}

	public List<Tuple> getPortfolioSummary(final User user) {
		return this.portfolioDAO.getPortfolioSummary(user);
	}

	public Portfolio getPortfolio(final Long folioId) {
		return this.portfolioDAO.findOne(folioId);
	}

}
