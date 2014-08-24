package com.pk.et.infra.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class ItemFailureLoggerListener extends
		ItemListenerSupport<Object, Object> {

	private static final Logger LOG = LoggerFactory
			.getLogger(ItemFailureLoggerListener.class);

	@Override
	public void onReadError(final Exception ex) {
		LOG.error("Encountered error on read", ex);
	}

	@Override
	public void onWriteError(final Exception exception,
			final List<? extends Object> items) {
		LOG.error("Encountered error on write " + items, exception);
	}
}
