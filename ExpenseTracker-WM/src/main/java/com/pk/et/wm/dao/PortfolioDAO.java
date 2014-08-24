package com.pk.et.wm.dao;

import java.util.List;

import com.pk.et.infra.repository.ETRepository;
import com.pk.et.wm.dao.custom.IPortfolioDAO;
import com.pk.et.wm.model.Portfolio;

public interface PortfolioDAO extends ETRepository<Portfolio, Long>,IPortfolioDAO{
	List<Portfolio> findByWealthContextId(Long userId);
}
