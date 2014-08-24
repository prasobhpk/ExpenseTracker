package com.pk.et.infra.service.impl;

import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.infra.model.JobUniqueInstance;
import com.pk.et.infra.service.IJobUniqueInstanceService;
import com.pk.et.infra.service.ILockService;
import com.pk.et.infra.util.Lock;

/**
 * Implementation of {@link LockService} based on {@link JobUniqueInstance}.
 */
@Service
public class LockService implements ILockService {

	@Autowired
	IJobUniqueInstanceService jobUniqueInstanceService;

	private static final Logger LOG = LoggerFactory
			.getLogger(LockService.class);

	public void waitForTheLock(final Lock lock, final String owner) {
		final String lockName = lock.getName();
		LOG.info("Trying to get the lock {} for {}.", lockName, owner);
		boolean firtsLoop = true;
		while (!takeLock(lockName, owner)) {
			try {
				if (firtsLoop) {
					LOG.info(
							"lock {} is already taken by an other process. Waiting for it ...",
							lockName);
				}
				firtsLoop = false;
				Thread.sleep(10000);
			} catch (final InterruptedException e) {
				LOG.error("error while waiting for taking lock {} for {}",
						lockName, owner);
			}
		}
		LOG.info("Lock {} acquired for {}.", lockName, owner);
	}

	public void releaseLock(final String jobName, final boolean isSuccess) {
		final JobUniqueInstance job = this.jobUniqueInstanceService
				.findByName(jobName);
		if (job != null) {
			job.updateTerminalStatus(isSuccess);
			LOG.debug("lock {} released", new Object[] { jobName });
		}
	}

	public void releaseLock(final Lock lock) {
		releaseLock(lock.getName(), true);
	}

	public boolean takeLock(final String jobName, final String owner) {
		try {
			return this.jobUniqueInstanceService.takeLock(jobName, owner);
		} catch (final OptimisticLockException e) {
			LOG.info(
					"Lock has been concurrently taken on job {}, exception is handled silently.",
					new Object[] { jobName });
			return false;
		}
	}

}
