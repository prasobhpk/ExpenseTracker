package com.pk.et.infra.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

import com.pk.et.infra.batch.JobParametersFactory;
import com.pk.et.infra.service.ILockService;
import com.pk.et.infra.util.TimeProvider;

/**
 * Base class to execute a scheduled job.
 */
public abstract class ScheduledJobProcessor {

	private static final Logger LOG = LoggerFactory
			.getLogger(ScheduledJobProcessor.class);

	@Autowired
	protected ILockService lockService;

	@Autowired
	protected JobLauncher jobLauncher;
	@Autowired
	protected JobParametersFactory jobParametersFactory;

	@Autowired
	protected TimeProvider timeProvider;

	/**
	 * Process a job to execute. After the job has been executed, the process
	 * method will update its job instance status to Completed or Error.
	 * 
	 * @param job
	 *            the job to process.
	 */
	public final void process(final Job job) {
		boolean error = true;
		try {
			LOG.debug("Starting to execute {} ", job.getName());
			execute(job);
			error = false;
		} catch (final Exception e) {
			LOG.error(e.getMessage());
		} finally {
			String status = "completed";
			if (error) {
				status = "error";
			}
			LOG.debug("Finished to execute {} with a status {}", job.getName(),
					status);
			this.lockService.releaseLock(job.getName(), !error);
		}
	}

	/**
	 * Execute the given job.
	 * 
	 * @param job
	 *            the job to execute.
	 */
	protected abstract void execute(Job job);
}
