package com.pk.et.wm.batch;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.listener.ItemListenerSupport;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.support.MessageBuilder;

import com.pk.et.infra.util.Notification;
import com.pk.et.wm.model.MarkToMarket;

public class MarkToMarketChunkListener extends
		ItemListenerSupport<MarkToMarket, MarkToMarket> {

	private static final Log logger = LogFactory
			.getLog(MarkToMarketChunkListener.class);

	@Autowired
	@Qualifier("chunkExecutions")
	MessageChannel chunkNotificationsChannel;

	@Override
	public void onReadError(final Exception ex) {
		if (ex instanceof FlatFileParseException) {
			final FlatFileParseException ffpe = (FlatFileParseException) ex;
			logger.error(String.format(
					"Error reading data on line '%s' - data: '%s'",
					ffpe.getLineNumber(), ffpe.getInput()));
		}
		this.chunkNotificationsChannel.send(MessageBuilder.withPayload(
				new Notification(ex.getMessage(), true)).build());
	}

	@Override
	public void onWriteError(final Exception ex,
			final List<? extends MarkToMarket> item) {
	}
}
