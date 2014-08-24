package com.pk.et.infra.model;

/**
 * Status of a job's execution requested via a jms queue.
 */
public enum JobStatus {
	PENDING(false), IN_PROGRESS(false), COMPLETED(true), ERROR(false), RETRYABLE_ERROR(
			true);

	private final boolean requestable;

	JobStatus(final boolean requestable) {
		this.requestable = requestable;
	}

	public boolean isRequestable() {
		return this.requestable;
	}
}
