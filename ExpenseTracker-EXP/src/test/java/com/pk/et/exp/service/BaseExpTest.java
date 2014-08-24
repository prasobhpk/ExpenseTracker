package com.pk.et.exp.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.pk.et.exp.dao.UserExpenseDAO;
import com.pk.et.exp.model.UserExpense;
import com.pk.et.infra.dao.ConfigurationDAO;
import com.pk.et.infra.dao.ConfigurationItemDAO;
import com.pk.et.infra.dao.UserDAO;
import com.pk.et.infra.model.ConfigType;
import com.pk.et.infra.model.Configuration;
import com.pk.et.infra.model.ConfigurationItem;
import com.pk.et.infra.model.Name;
import com.pk.et.infra.model.Roles;
import com.pk.et.infra.model.User;
import com.pk.et.infra.model.UserAuthority;
import com.pk.et.infra.model.ValueType;
import com.pk.et.infra.service.IAuthorityService;
import com.pk.et.infra.util.TestUtil;

public abstract class BaseExpTest {

	@Autowired
	protected UserDAO userDAO;

	@Autowired(required = true)
	@Qualifier("securityManager")
	protected com.pk.et.infra.security.SecurityManager securityManager;

	@Qualifier("authorityService")
	@Autowired(required = true)
	protected IAuthorityService authorityService;

	@Autowired
	protected ConfigurationDAO configurationDAO;

	@Autowired
	protected ConfigurationItemDAO configurationItemDAO;

	protected User user = null;

	@Autowired
	private TestUtil testUtil;

	protected UserExpense expenseContext = null;

	@Autowired
	protected UserExpenseDAO userExpenseDAO;

	@Before
	public void expInit() {
		seedAuthorities();
	}

	@After
	public void cleanup() {
		this.testUtil.cleanDataBase();
	}

	@Transactional
	public User createUser(final String userName) {
		final User user = this.userDAO.save(create(userName));
		final List<ConfigurationItem> items = this.configurationItemDAO
				.findByConfigType(ConfigType.USER);
		Configuration conf = null;
		for (final ConfigurationItem item : items) {
			conf = new Configuration();
			conf.setConfigItem(item);
			conf.setConfigItemValue(item.getDefaultValue());
			conf.setUser(this.user);
			this.configurationDAO.save(conf);
		}
		return user;
	}

	private User create(final String userName) {
		final User user = new User();
		user.setUsername(userName);
		final Name name = new Name();
		name.setFirstName(userName);
		name.setLastName(userName);
		user.setName(name);
		user.setUsername(userName);
		user.setPassword(userName);
		user.setSalt(this.securityManager.getRandomSalt());
		user.getRoles()
				.add(this.authorityService.getAuthority(Roles.ROLE_USER));
		user.setPassword(this.securityManager.encodePassword(
				user.getPassword(), null));
		return user;
	}

	@Transactional
	private void seedAuthorities() {
		if (this.authorityService.getCount() == 0) {
			UserAuthority authority = null;
			for (final Roles r : Roles.values()) {
				authority = new UserAuthority();
				authority.setRole(r);
				this.authorityService.saveAuthority(authority);
			}
		}
		// seed Admin
		if (!this.userDAO.userExists("admin")) {
			try {

				User user = new User();
				final Name name = new Name();
				name.setFirstName("SUPER");
				name.setLastName("ADMIN");
				user.setName(name);
				user.setUsername("admin");
				user.setPassword("admin");
				user.setSalt(this.securityManager.getRandomSalt());
				user.getRoles().add(
						this.authorityService.getAuthority(Roles.ROLE_ADMIN));
				// user.getRoles().add(
				// authorityService.getAuthority(Roles.ROLE_USER));
				// user.setPassword(securityManager.asyEncrypt(user.getPassword()));
				user.setPassword(this.securityManager.encodePassword(
						user.getPassword(), null));
				user = this.userDAO.save(user);
				System.out.println("Admin User created..");
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		if (this.configurationItemDAO.count() == 0) {
			ConfigurationItem item = null;
			final List<ConfigurationItem> items = new ArrayList<ConfigurationItem>();
			try {
				item = new ConfigurationItem("SEARCH_MENU_REQ",
						ValueType.SINGLE_VALUED, false, "true", ConfigType.USER);
				items.add(item);

				item = new ConfigurationItem("MENU_STACK",
						ValueType.MULTI_VALUED, true, "{}", ConfigType.USER);
				items.add(item);

				item = new ConfigurationItem("SHOW_TOTAL_WITH_FEE_SUMMARY",
						ValueType.SINGLE_VALUED, false, "false",
						ConfigType.USER);
				items.add(item);

				item = new ConfigurationItem("SHOW_TOTAL_WITH_FEE_DETAILS",
						ValueType.SINGLE_VALUED, false, "false",
						ConfigType.USER);
				items.add(item);

				item = new ConfigurationItem(
						"SHOW_PROFIT_AFTER_BROKERAGE_PERFORMANCE",
						ValueType.SINGLE_VALUED, false, "true", ConfigType.USER);
				items.add(item);

				item = new ConfigurationItem("SHOW_REALIZED_PROFIT_DETAILS",
						ValueType.SINGLE_VALUED, false, "true", ConfigType.USER);
				items.add(item);

				item = new ConfigurationItem("HOME_VIEW",
						ValueType.SINGLE_VALUED, false, "home", ConfigType.USER);
				items.add(item);

				item = new ConfigurationItem("PROXY_URL",
						ValueType.SINGLE_VALUED, false,
						"proxysouth.i-flex.com", ConfigType.APPLICATION);
				items.add(item);

				item = new ConfigurationItem("PROXY_PORT",
						ValueType.SINGLE_VALUED, false, "8080",
						ConfigType.APPLICATION);
				items.add(item);

				item = new ConfigurationItem("PROXY_ACTIVE",
						ValueType.SINGLE_VALUED, false, "false",
						ConfigType.APPLICATION);
				items.add(item);

				this.configurationItemDAO.save(items);

			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Transactional
	protected UserExpense initExpeseContextForUser(final User user) {
		this.expenseContext = this.userExpenseDAO.save(new UserExpense(user));
		return this.expenseContext;
	}
}
