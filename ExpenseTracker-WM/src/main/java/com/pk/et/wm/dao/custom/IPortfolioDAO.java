package com.pk.et.wm.dao.custom;

import java.util.List;

import javax.persistence.Tuple;

import com.pk.et.infra.model.User;

public interface IPortfolioDAO{
	List<Tuple> getPortfolioSummary(User user);
}
