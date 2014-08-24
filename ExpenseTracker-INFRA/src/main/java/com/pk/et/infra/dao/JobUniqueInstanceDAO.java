package com.pk.et.infra.dao;

import com.pk.et.infra.model.JobUniqueInstance;
import com.pk.et.infra.repository.ETRepository;

public interface JobUniqueInstanceDAO extends
		ETRepository<JobUniqueInstance, Long> {
	JobUniqueInstance findByName(String name);
}
