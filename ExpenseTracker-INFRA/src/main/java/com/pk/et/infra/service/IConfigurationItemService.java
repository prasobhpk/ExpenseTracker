package com.pk.et.infra.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.model.ConfigurationItem;

public interface IConfigurationItemService {
	@Transactional
	ConfigurationItem save(ConfigurationItem item);

	@Transactional(readOnly = true)
	long getCount();
	
	@Transactional(readOnly = true)
	ConfigurationItem findByKey(String key);
	
	List<ConfigurationItem> save(List<ConfigurationItem> items);
}
