package com.pk.et.infra.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.infra.dao.ConfigurationItemDAO;
import com.pk.et.infra.model.ConfigurationItem;
import com.pk.et.infra.service.IConfigurationItemService;

@Service("configurationItemService")
public class ConfigurationItemService implements IConfigurationItemService {
	private static final Logger LOG = LoggerFactory
			.getLogger(ConfigurationItemService.class);
	@Autowired(required = true)
	// @Qualifier("configurationItemDAO")
	private ConfigurationItemDAO configurationItemDAO;

	public ConfigurationItem save(final ConfigurationItem item) {
		LOG.debug("Saving ConfigurationItem : {}->{}",
				new Object[] { item.getConfigKey(), item.getDefaultValue() });
		return this.configurationItemDAO.save(item);
	}

	public long getCount() {
		return this.configurationItemDAO.count();
	}

	public ConfigurationItem findByKey(final String key) {
		return this.configurationItemDAO.findByConfigKey(key);
	}

	public List<ConfigurationItem> save(final List<ConfigurationItem> items) {
		return this.configurationItemDAO.save(items);
	}
}
