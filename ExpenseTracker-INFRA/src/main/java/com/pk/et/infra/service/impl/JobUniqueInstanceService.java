package com.pk.et.infra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.infra.dao.JobUniqueInstanceDAO;
import com.pk.et.infra.model.JobStatus;
import com.pk.et.infra.model.JobUniqueInstance;
import com.pk.et.infra.service.IJobUniqueInstanceService;

@Service
public class JobUniqueInstanceService implements IJobUniqueInstanceService {
	@Autowired
	JobUniqueInstanceDAO jobUniqueInstanceDAO;

	public boolean takeLock(final String name, final String owner) {
		JobUniqueInstance jobInstance = this.jobUniqueInstanceDAO
				.findByName(name);

		if (jobInstance == null) {
			jobInstance = createJobInstance(name);
		}
		final JobStatus previousStatus = jobInstance.getStatus();
		if (jobInstance.isRequestable()) {
			updateStatusToPending(jobInstance, owner);
		}
		return previousStatus.isRequestable();
	}

	private JobUniqueInstance createJobInstance(final String name) {
		final JobUniqueInstance instance = JobUniqueInstance.create();
		instance.setStatus(JobStatus.COMPLETED);
		instance.setName(name);
		return this.jobUniqueInstanceDAO.save(instance);
	}

	private void updateStatusToPending(final JobUniqueInstance jobInstance,
			final String owner) {
		jobInstance.setStatus(JobStatus.PENDING);
		jobInstance.setStartDate(jobInstance.getTimeProvider()
				.getCurrentDateTime());
		jobInstance.setEndDate(null);
		jobInstance.setOwner(owner);
	}

	public JobUniqueInstance findByName(final String name) {
		return this.jobUniqueInstanceDAO.findByName(name);
	}
}
