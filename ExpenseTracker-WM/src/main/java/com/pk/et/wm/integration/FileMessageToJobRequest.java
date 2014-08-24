package com.pk.et.wm.integration;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.messaging.Message;
import org.springframework.integration.annotation.Transformer;

public class FileMessageToJobRequest {
	private static final Logger LOG = LoggerFactory
			.getLogger(FileMessageToJobRequest.class);

	private Job job;

	private String fileParameterName;

	public void setFileParameterName(final String fileParameterName) {
		this.fileParameterName = fileParameterName;
	}

	public void setJob(final Job job) {
		this.job = job;
	}

	@Transformer
	public JobLaunchRequest toRequest(final Message<File> message) {
		final String filePath = message.getPayload().getAbsolutePath();
		LOG.info("===================Processing file : {}====================",
				filePath);
		final JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addString(this.fileParameterName, filePath);
		return new JobLaunchRequest(this.job,
				jobParametersBuilder.toJobParameters());
	}
}
