package com.pk.et.wm.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.pk.et.wm.dao.MarkToMarketDAO;
import com.pk.et.wm.model.MarkToMarket;

public class MarkToMarketWriter implements ItemWriter<MarkToMarket> {

	private static final Logger LOG = LoggerFactory
			.getLogger(MarkToMarketWriter.class);
	@Autowired
	private MarkToMarketDAO markToMarketDAO;

	public void write(final List<? extends MarkToMarket> mtms) throws Exception {
		LOG.debug("Persisting {} mtms to DB ", mtms.size());
		try {
			this.markToMarketDAO.save(mtms);
		} catch (final Exception e) {
			LOG.error("Error while saving mtms", e);
		}
	}
}
