package com.pk.et.infra.dao;

import java.util.List;

import com.pk.et.infra.model.ConfigType;
import com.pk.et.infra.model.ConfigurationItem;
import com.pk.et.infra.repository.ETRepository;

public interface ConfigurationItemDAO extends
		ETRepository<ConfigurationItem, Long> {
	
	List<ConfigurationItem> findByConfigType(ConfigType configType);

	ConfigurationItem findByConfigKey(String configKey);
}
