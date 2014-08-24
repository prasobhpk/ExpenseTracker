package com.pk.et.wm.service.impl;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.infra.model.User;
import com.pk.et.wm.dao.HoldingDAO;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.Holding;
import com.pk.et.wm.model.Portfolio;
import com.pk.et.wm.service.IHoldingService;

@Service("holdingService")
public class HoldingService implements IHoldingService {
	// @Qualifier("holdingDAO")
	@Autowired(required = true)
	private HoldingDAO holdingDAO;

	public List<Holding> getHoldingsByfolio(final User user, final Long folioId) {
		return this.holdingDAO.getHoldingsByfolio(user, folioId);
	}

	public List<Tuple> getPortfolioHoldingSummary(final User user) {
		return this.holdingDAO.getPortfolioHoldingSummary(user);
	}

	public Holding getHoldingByfolio(final User user, final Long folioId,
			final Long equityId) {
		final Portfolio portfolio = new Portfolio();
		portfolio.setId(folioId);
		final Equity equity = new Equity();
		equity.setId(equityId);
		return this.holdingDAO.getHolding(user, equity, portfolio);
	}
}
