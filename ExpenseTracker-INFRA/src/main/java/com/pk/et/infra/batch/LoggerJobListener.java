package com.pk.et.infra.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * Logger job listener.
 */
@Component
public class LoggerJobListener implements JobExecutionListener {
	private static final Logger LOG = LoggerFactory
			.getLogger(LoggerJobListener.class);

	public void beforeJob(final JobExecution jobExecution) {
		LOG.info("beforeJob : STARTING");
		LOG.info("beforeJob JobParameters : " + jobExecution.getJobParameters());
	}

	public void afterJob(final JobExecution jobExecution) {
		LOG.info("afterJob STATUS + " + jobExecution.getStatus());
		LOG.info("afterJob : " + jobExecution);
	}

}
