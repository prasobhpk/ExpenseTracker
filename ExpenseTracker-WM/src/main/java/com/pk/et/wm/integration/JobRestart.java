package com.pk.et.wm.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.annotation.ServiceActivator;

public class JobRestart {

	private static final Logger LOG = LoggerFactory.getLogger(JobRestart.class);

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	@Qualifier("importMtms")
	Job job;

	@ServiceActivator
	public void restartIfPossible(final JobExecution execution)
			throws JobInstanceAlreadyCompleteException,
			JobParametersInvalidException, JobRestartException,
			JobExecutionAlreadyRunningException {
		LOG.info("Restarting job...");
		this.jobLauncher.run(this.job, execution.getJobParameters());
	}
}
