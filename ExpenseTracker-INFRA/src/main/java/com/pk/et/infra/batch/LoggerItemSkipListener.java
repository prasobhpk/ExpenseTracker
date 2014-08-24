package com.pk.et.infra.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;

/**
 * Log the items skipped during a step process.
 */
public class LoggerItemSkipListener implements SkipListener<Object, Object> {
	private static final Logger LOG = LoggerFactory
			.getLogger(LoggerItemSkipListener.class);

	public void onSkipInRead(final Throwable t) {
		LOG.error("skip the item on read because of Exception: ", t);
	}

	public void onSkipInWrite(final Object item, final Throwable t) {
		LOG.error("skip the item " + item + " on write because of Exception: ",
				t);
	}

	public void onSkipInProcess(final Object item, final Throwable t) {
		LOG.error("skip the item " + item
				+ " on process because of Exception: ", t);
	}
}
