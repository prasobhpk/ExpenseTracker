package com.pk.et.wm.dao.custom;

import java.util.Date;

public interface IMarkToMarketDAO {
	int getCountBySymbolAndSeriesAndTradeDate(final String symbol,
			final String series, final Date tradeDate);
}
