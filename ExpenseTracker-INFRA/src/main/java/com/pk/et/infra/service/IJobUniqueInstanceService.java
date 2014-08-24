package com.pk.et.infra.service;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.model.JobUniqueInstance;

public interface IJobUniqueInstanceService {
	/**
	 * Try to take the lock on the job. If the lock is taken the job status will
	 * be updated to pending.
	 * 
	 * @param name
	 *            the job name
	 * @param owner
	 *            the job owner
	 * @return True if the job can be send otherwise false.
	 */
	@Transactional
	boolean takeLock(final String name, final String owner);

	@Transactional(readOnly = true)
	JobUniqueInstance findByName(String name);
}
