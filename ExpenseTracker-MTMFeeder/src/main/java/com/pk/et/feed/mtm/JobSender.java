package com.pk.et.feed.mtm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.jms.Job;
import com.pk.et.infra.model.JobStatus;
import com.pk.et.infra.model.JobUniqueInstance;
import com.pk.et.infra.service.IJobUniqueInstanceService;
import com.pk.et.infra.service.ILockService;
import com.pk.et.infra.service.impl.LockService;
import com.pk.et.infra.util.TimeProvider;

/**
 * Job Sender.
 */
public class JobSender {

	private static final Logger LOG = LoggerFactory.getLogger(JobSender.class);

	@Autowired
	private MessageChannel scheduledJobDispatcher;

	@Autowired
	private TimeProvider timeProvider;

	@Autowired
	private ILockService lockService;

	@Autowired
	IJobUniqueInstanceService jobUniqueInstanceService;

	/**
	 * Send a job to a jms queue if there is not already a job sent and
	 * currently pending.
	 * 
	 * @param job
	 *            The job to send.
	 */
	public void sendJob(final Job job) {
		final String jobName = job.getName();
		if (this.lockService.takeLock(jobName, null)) {
			sendJobMessage(job);
		}

	}

	/**
	 * Send a message on jms wrapping the given job.
	 * 
	 * @param job
	 *            The job to send on jms.
	 */
	private void sendJobMessage(final Job job) {
		LOG.info(" {} requested", job.getName());
		final Message<Job> message = MessageBuilder.withPayload(job).build();
		boolean error = true;
		try {
			final boolean success = this.scheduledJobDispatcher.send(message);
			error = !success;
		} catch (final MessagingException e) {
			LOG.error(
					"MessagingException has been caught, the job {} has been flagged to {}",
					job.getName(), JobStatus.RETRYABLE_ERROR);
			LOG.error("MessagingException caught : ", e);
		} catch (final Exception exc) {
			LOG.error("Job {} has thrown an exception : {}", job.getName(), exc);
		} finally {
			if (error) {
				LOG.error("error while sending the job {}, flagged to {}",
						job.getName(), JobStatus.RETRYABLE_ERROR);
				updateJobInstanceToRetryableError(job);
			}
		}
	}

	@Transactional
	private void updateJobInstanceToRetryableError(final Job job) {
		final JobUniqueInstance jobInstance = this.jobUniqueInstanceService
				.findByName(job.getName());
		jobInstance.setStatus(JobStatus.RETRYABLE_ERROR);
		jobInstance.setEndDate(this.timeProvider.getCurrentDateTime());
	}

	public void setLockService(final LockService lockService) {
		this.lockService = lockService;
	}

	public void setTimeProvider(final TimeProvider timeProvider) {
		this.timeProvider = timeProvider;
	}

	public void setScheduledJobDispatcher(
			final MessageChannel scheduledJobDispatcher) {
		this.scheduledJobDispatcher = scheduledJobDispatcher;
	}

}
