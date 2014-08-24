package com.pk.et.infra.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.dao.ConfigurationDAO;
import com.pk.et.infra.dao.ConfigurationItemDAO;
import com.pk.et.infra.dao.UserDAO;
import com.pk.et.infra.model.ConfigType;
import com.pk.et.infra.model.Configuration;
import com.pk.et.infra.model.ConfigurationItem;
import com.pk.et.infra.model.User;
import com.pk.et.infra.service.IUserService;

@Service("userService")
public class UserService implements IUserService, UserDetailsService {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private ConfigurationItemDAO configurationItemDAO;

	@Autowired
	private ConfigurationDAO configurationDAO;

	public User createUser(User user) {
		user = this.userDAO.save(user);
		final List<ConfigurationItem> items = this.configurationItemDAO
				.findByConfigType(ConfigType.USER);
		Configuration conf = null;
		for (final ConfigurationItem item : items) {
			conf = new Configuration();
			conf.setConfigItem(item);
			conf.setConfigItemValue(item.getDefaultValue());
			conf.setUser(user);
			this.configurationDAO.save(conf);
		}
		return user;
	}

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {
		return this.userDAO.findByUsername(username);
	}

	public boolean changePassowrd(final Long userId, final String oldPassword,
			final String newPassword) {
		boolean status = false;
		final User user = this.userDAO.findOne(userId);
		if (user.getPassword().equals(oldPassword)) {
			user.setPassword(newPassword);
			this.userDAO.save(user);
			status = true;
		}
		return status;
	}

	public boolean userExists(final String userName) {
		final Map<String, ? super Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", userName);
		return this.userDAO.findCountByCriteria(paramMap) > 0;
	}

}
