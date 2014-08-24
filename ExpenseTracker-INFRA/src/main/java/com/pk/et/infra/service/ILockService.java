package com.pk.et.infra.service;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.util.Lock;

/**
 * Service to manage lock executions.
 */
public interface ILockService {

	/**
	 * Try to get the lock on the job. If already locked, wait until it's
	 * released, then take it.
	 * 
	 * @param lockName
	 *            the job we are waiting for.
	 * @param owner
	 *            the owener of the job.
	 */
	void waitForTheLock(final Lock lock, final String owner);

	/**
	 * Release the lock. Set its status to COMPLETED if it's a success, to ERROR
	 * otherwise.
	 * 
	 * @param lockName
	 *            the job to release.
	 * @param isSuccess
	 *            the job status.
	 */
	void releaseLock(final String lockName, boolean isSuccess);

	/**
	 * Release the lock. Set its status to COMPLETED if it's a success, to ERROR
	 * otherwise.
	 * 
	 * @param lock
	 *            the lock to release.
	 * @param isSuccess
	 *            the job status.
	 */
	@Transactional
	void releaseLock(final Lock lock);

	/**
	 * Try to get the lock on the job. Return true if the lock is taken, false
	 * otherwise.
	 * 
	 * @param lockName
	 *            the lock to take.
	 * @param owner
	 *            the owner of the lock.
	 * @return true if the lock is taken, false otherwise.
	 */
	boolean takeLock(final String lockName, final String owner);

}
