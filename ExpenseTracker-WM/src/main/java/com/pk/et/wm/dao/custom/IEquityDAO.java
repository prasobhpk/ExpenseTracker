package com.pk.et.wm.dao.custom;

import java.util.List;

import com.pk.et.wm.model.Equity;

public interface IEquityDAO {
	List<Equity> findByNameStartingWith(String name);

	Equity findBySymbol(String code);

}
