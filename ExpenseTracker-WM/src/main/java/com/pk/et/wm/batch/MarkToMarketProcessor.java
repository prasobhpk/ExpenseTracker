package com.pk.et.wm.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.pk.et.wm.dao.EquityDAO;
import com.pk.et.wm.dao.MarkToMarketDAO;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.model.MarkToMarket;

public class MarkToMarketProcessor implements
		ItemProcessor<MarkToMarket, MarkToMarket> {

	@Autowired
	private EquityDAO equityDAO;

	@Autowired
	private MarkToMarketDAO markToMarketDAO;

	private static final Logger LOG = LoggerFactory
			.getLogger(MarkToMarketProcessor.class);

	public MarkToMarket process(final MarkToMarket mtm) throws Exception {

		if (this.markToMarketDAO.getCountBySymbolAndSeriesAndTradeDate(
				mtm.getSymbol(), mtm.getSymbol(), mtm.getTradeDate()) > 0) {
			LOG.debug(
					"[MTM Processing]Skipping item as it already exists :{}-{}-{}",
					new Object[] { mtm.getSymbol(), mtm.getSeries(),
							mtm.getTradeDate() });
			return null;
		}
		LOG.debug("MTM Processing for stock :{}", mtm.getSymbol());
		Equity stock = null;
		stock = this.equityDAO.findByNseCode(mtm.getSymbol());
		LOG.debug("Loading Equity for nse code :{}", mtm.getSymbol());
		if (stock == null && !"EQ".equals(mtm.getSeries())) {
			LOG.debug(
					"Could not find  Equity for nse code :{} , so trying by appending series :{}",
					new Object[] { mtm.getSymbol(), mtm.getSeries() });
			stock = this.equityDAO.findByNseCode(mtm.getSymbol()
					+ mtm.getSeries());
		}
		if (stock != null) {
			LOG.debug("Binding Equity on mtm :{}", mtm.getSymbol());
			mtm.setUnderlying(stock);
		} else {
			LOG.debug(
					"No Equity found on db for symbol :{} for binding mtm with stock",
					mtm.getSymbol());
		}

		return mtm;
	}

}
