package com.pk.et.wm.dao.custom.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pk.et.infra.dao.custom.impl.GenericDAO;
import com.pk.et.wm.dao.custom.IMarkToMarketDAO;
import com.pk.et.wm.model.MarkToMarket;

public class MarkToMarketDAOImpl extends GenericDAO implements IMarkToMarketDAO {
	public int getCountBySymbolAndSeriesAndTradeDate(final String symbol,
			final String series, final Date tradeDate) {
		final Map<String, ? super Object> paramMap = new HashMap<String, Object>();
		paramMap.put("symbol", symbol);
		paramMap.put("series", series);
		paramMap.put("tradeDate", tradeDate);
		return findCountByCriteria(MarkToMarket.class, paramMap);
	}
}
