package com.pk.et.infra.dao.custom;

import com.pk.et.infra.model.Configuration;
import com.pk.et.infra.model.User;

public interface IConfigurationDAO {
	Configuration findByUserAndConfigurationItemConfigKey(User user,String key);
}
