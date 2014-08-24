package com.pk.et.wm.service;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.model.User;
import com.pk.et.wm.model.Holding;

public interface IHoldingService {
	@Transactional(readOnly = true)
	List<Holding> getHoldingsByfolio(User user, Long folioId);
	
	@Transactional(readOnly = true)
	Holding getHoldingByfolio(User user, Long folioId,Long equityId);

	@Transactional(readOnly = true)
	List<Tuple> getPortfolioHoldingSummary(User user);
}
