package com.pk.et.infra.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.infra.dao.ConfigurationDAO;
import com.pk.et.infra.dao.ConfigurationItemDAO;
import com.pk.et.infra.model.Configuration;
import com.pk.et.infra.model.ConfigurationItem;
import com.pk.et.infra.model.User;
import com.pk.et.infra.service.IConfigurationService;

@Service("configurationService")
public class ConfigurationService implements IConfigurationService {
	@Autowired(required = true)
	// @Qualifier("configurationDAO")
	private ConfigurationDAO configurationDAO;

	@Autowired(required = true)
	// @Qualifier("configurationItemDAO")
	private ConfigurationItemDAO configurationItemDAO;

	public Map<String, String> getUserConfigurations(final User user) {
		final List<Configuration> configs = this.configurationDAO
				.findByUser(user);
		final Map<String, String> confMap = new HashMap<String, String>();
		for (final Configuration conf : configs) {
			confMap.put(conf.getConfigItem().getConfigKey(),
					conf.getConfigItemValue());
		}
		return confMap;
	}

	public Configuration updateConfig(final String key, final String value,
			final User user) {
		Configuration config = this.configurationDAO
				.findByUserAndConfigurationItemConfigKey(user, key);
		if (config != null) {
			config.setConfigItemValue(value);
		} else {
			config = new Configuration();
			final ConfigurationItem item = this.configurationItemDAO
					.findByConfigKey(key);
			config.setConfigItemValue(value);
			config.setUser(user);
			config.setConfigItem(item);
			if (item != null) {
				this.configurationDAO.save(config);
			}
		}
		return config;
	}

	public Configuration getByKey(final String key, final User user) {
		return this.configurationDAO.findByUserAndConfigurationItemConfigKey(
				user, key);
	}

}
