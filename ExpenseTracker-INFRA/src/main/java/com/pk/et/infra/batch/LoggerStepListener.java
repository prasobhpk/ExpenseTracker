package com.pk.et.infra.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 * This class should be remove from the core when the import module will be
 * separated from the web app.
 * 
 * Log some informations at before and after a step execution.
 */
public class LoggerStepListener implements StepExecutionListener {

	private static final Logger LOG = LoggerFactory
			.getLogger(LoggerStepListener.class);

	public void beforeStep(final StepExecution stepExecution) {

	}

	public ExitStatus afterStep(final StepExecution stepExecution) {
		final String msg = "Step completed with the exit status [ "
				+ stepExecution.getExitStatus().getExitCode()
				+ " ] and the followings :" //
				+ "\n\t Step [ "
				+ stepExecution.getStepName()
				+ " ]" //
				+ "\n\t items reads = "
				+ stepExecution.getReadCount() //
				+ "\n\t items read skips = "
				+ stepExecution.getReadSkipCount() //
				+ "\n\t number of items filtered by processors = "
				+ stepExecution.getFilterCount() //
				+ "\n\t items write = "
				+ stepExecution.getWriteCount() //
				+ "\n\t items write skips = "
				+ stepExecution.getWriteSkipCount();

		LOG.info(msg);

		return stepExecution.getExitStatus();
	}

}
