package com.pk.et.wm.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.wm.model.BrokerageStructure;

public interface IBrokerageStructureService {
	@Transactional(readOnly = true)
	List<BrokerageStructure> getBrokerList();

	@Transactional
	BrokerageStructure addBrokerage(BrokerageStructure brokerage);

	@Transactional(readOnly=true)
	BigDecimal calculateBrokerage(BigDecimal amount,BrokerageStructure structure);
}
