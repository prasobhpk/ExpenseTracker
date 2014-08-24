package com.pk.et.wm.service;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.model.User;
import com.pk.et.wm.model.Portfolio;

public interface IPortfolioService {
	@Transactional(readOnly = true)
	List<Portfolio> getPortfolios(User user);

	@Transactional
	Portfolio addPortfolio(Portfolio portfolio, User user);

	@Transactional(readOnly = true)
	List<Tuple> getPortfolioSummary(User user);
	
	@Transactional(readOnly=true)
	Portfolio getPortfolio(Long folioId);
}
