package com.pk.et.infra.dao;

import java.util.List;

import com.pk.et.infra.dao.custom.IConfigurationDAO;
import com.pk.et.infra.model.Configuration;
import com.pk.et.infra.model.User;
import com.pk.et.infra.repository.ETRepository;

public interface ConfigurationDAO extends ETRepository<Configuration, Long>,IConfigurationDAO{
	List<Configuration> findByUser(User user);
}
