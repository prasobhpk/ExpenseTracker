package com.pk.et.infra.dao.custom.impl;

import java.util.HashMap;
import java.util.Map;

import com.pk.et.infra.dao.custom.IConfigurationDAO;
import com.pk.et.infra.model.Configuration;
import com.pk.et.infra.model.User;

public class ConfigurationDAOImpl extends GenericDAO implements IConfigurationDAO{

	public Configuration findByUserAndConfigurationItemConfigKey(User user,
			String key) {
		Map<String, ? super Object> paramMap = new HashMap<String, Object>();
		paramMap.put("user.id", user.getId());
		paramMap.put("configItem.configKey", key);
		return findUniqueByCriteria(Configuration.class,paramMap);
	}

}
