package com.pk.et.wm.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.wm.dao.MarkToMarketDAO;
import com.pk.et.wm.service.IMarkToMarketService;

@Service
public class MarkToMarketService implements IMarkToMarketService {
	@Autowired
	private MarkToMarketDAO markToMarketDAO;

	public int getCountBySymbolAndSeriesAndTradeDate(final String symbol,
			final String series, final Date tradeDate) {
		return this.markToMarketDAO.getCountBySymbolAndSeriesAndTradeDate(
				symbol, series, tradeDate);
	}
}
