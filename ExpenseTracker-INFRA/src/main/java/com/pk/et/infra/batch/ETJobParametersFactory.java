package com.pk.et.infra.batch;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.infra.util.TimeProvider;

/**
 * Parameters factory that uses the current date and the local server network
 * name.
 * 
 */
@Service
public class ETJobParametersFactory implements JobParametersFactory {

	private static final Logger LOG = LoggerFactory
			.getLogger(ETJobParametersFactory.class);

	@Autowired
	private TimeProvider timeManager;

	public final JobParametersBuilder createDefaultParametersBuilder() {

		final Date currentDate = this.timeManager.getCurrentDate();
		String host;
		try {
			host = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (final UnknownHostException e) {
			if (LOG.isInfoEnabled()) {
				LOG.info("Job parameters creation error ", e);
			}
			host = "unknown";
		}
		return new JobParametersBuilder().addDate("currentDate", currentDate)
				.addString("host", host);
	}

	public void setTimeManager(final TimeProvider timeManager) {
		this.timeManager = timeManager;
	}
}
