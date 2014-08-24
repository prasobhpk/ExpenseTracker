package com.pk.et.wm.dao.custom;

import java.util.List;

import javax.persistence.Tuple;

import com.pk.et.infra.model.User;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.Holding;
import com.pk.et.wm.model.Portfolio;

public interface IHoldingDAO{
	Holding getHolding(User user, Equity equity,Portfolio portfolio);
	List<Holding> getHoldingsByfolio(User user,Long folioId);
	List<Tuple> getPortfolioHoldingSummary(User user);
}
