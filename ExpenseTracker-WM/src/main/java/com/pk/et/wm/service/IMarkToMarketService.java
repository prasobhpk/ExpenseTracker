package com.pk.et.wm.service;

import java.util.Date;

public interface IMarkToMarketService {
	int getCountBySymbolAndSeriesAndTradeDate(final String symbol,
			final String series, final Date tradeDate);
}
