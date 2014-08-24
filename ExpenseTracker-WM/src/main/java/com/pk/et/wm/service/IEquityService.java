package com.pk.et.wm.service;

import java.util.List;

import javax.persistence.PersistenceException;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.wm.model.Equity;

public interface IEquityService {
	@Transactional
	Equity saveCompany(Equity equity);

	@Transactional(rollbackFor=PersistenceException.class)
	void saveCompanies(List<Equity> equities);

	@Transactional(readOnly = true)
	List<Equity> search(String name);

	@Transactional(readOnly = true)
	Equity getBySymbol(String symbol);

}
