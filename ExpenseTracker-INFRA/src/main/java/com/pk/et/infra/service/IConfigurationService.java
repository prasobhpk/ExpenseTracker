package com.pk.et.infra.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.model.Configuration;
import com.pk.et.infra.model.User;

public interface IConfigurationService {
	@Transactional(readOnly = true)
	Map<String, String> getUserConfigurations(User user);

	@Transactional
	Configuration updateConfig(String key, String value, User user);

	@Transactional(readOnly = true)
	Configuration getByKey(String key, User user);
}
