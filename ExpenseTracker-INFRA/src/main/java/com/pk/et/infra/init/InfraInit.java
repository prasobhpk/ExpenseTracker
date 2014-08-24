package com.pk.et.infra.init;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.model.ConfigType;
import com.pk.et.infra.model.ConfigurationItem;
import com.pk.et.infra.model.Name;
import com.pk.et.infra.model.Roles;
import com.pk.et.infra.model.User;
import com.pk.et.infra.model.UserAuthority;
import com.pk.et.infra.model.ValueType;
import com.pk.et.infra.service.IAuthorityService;
import com.pk.et.infra.service.IConfigurationItemService;
import com.pk.et.infra.service.IUserService;
import com.pk.et.infra.util.ETConstants;

public class InfraInit {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Qualifier("authorityService")
	@Autowired(required = true)
	private IAuthorityService authorityService;

	@Autowired(required = true)
	@Qualifier("securityManager")
	private com.pk.et.infra.security.SecurityManager securityManager;

	@Autowired(required = true)
	@Qualifier("userService")
	private IUserService userService;

	// @Autowired(required = true)
	// @Qualifier("paramsService")
	// private IWMParamsService paramsService;

	@Autowired(required = true)
	@Qualifier("configurationItemService")
	private IConfigurationItemService configurationItemService;

	@PostConstruct
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
		if (!this.userService.userExists("admin")) {
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
				user = this.userService.createUser(user);
				System.out.println("Admin User created..");
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		if (this.configurationItemService.getCount() == 0) {
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

				item = new ConfigurationItem(ETConstants.PROXY_URL_KEY,
						ValueType.SINGLE_VALUED, false,
						"proxy.int.world.socgen", ConfigType.APPLICATION);
				items.add(item);

				item = new ConfigurationItem(ETConstants.PROXY_PORT_KEY,
						ValueType.SINGLE_VALUED, false, "8080",
						ConfigType.APPLICATION);
				items.add(item);
				
				item = new ConfigurationItem(ETConstants.PROXY_USER,
						ValueType.SINGLE_VALUED, false, "prasobh.kalathil",
						ConfigType.APPLICATION);
				items.add(item);
				
				item = new ConfigurationItem(ETConstants.PROXY_PASSWORD,
						ValueType.SINGLE_VALUED, false, "usha@123",
						ConfigType.APPLICATION);
				items.add(item);

				item = new ConfigurationItem("PROXY_ACTIVE",
						ValueType.SINGLE_VALUED, false, "true",
						ConfigType.APPLICATION);
				items.add(item);

				this.configurationItemService.save(items);
			} catch (final Exception e) {
				this.log.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}