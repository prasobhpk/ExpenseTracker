package com.pk.et.infra.batch;

import org.springframework.batch.core.JobParametersBuilder;

/**
 * Helper class that provides parameters for spring-batch jobs. Spring-batch use
 * jobs parameters to generate unique job keys. A job cannot be executed several
 * times with the same parameters.
 * 
 */
public interface JobParametersFactory {

	/**
	 * Create a default jobParameters builder with the startDate (current date)
	 * and host already set.
	 * 
	 * @return A builder.
	 */
	public JobParametersBuilder createDefaultParametersBuilder();
}
