package com.pk.et.wm.dao;

import com.pk.et.infra.repository.ETRepository;
import com.pk.et.wm.dao.custom.IEquityDAO;
import com.pk.et.wm.model.Equity;

public interface EquityDAO extends ETRepository<Equity, Long>, IEquityDAO {
	// List<Equity> findByNameStartingWith(String name);
	// Equity findBySymbol(String code);
	Equity findByNseCode(String nseCode);
}
