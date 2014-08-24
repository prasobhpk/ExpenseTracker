package com.pk.et.infra.security;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.pk.et.infra.model.User;
import com.pk.et.infra.service.IConfigurationService;
import com.pk.et.infra.util.ETConstants;

public class RoleBasedAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	@Qualifier("configurationService")
	private IConfigurationService configurationService;

	@Autowired
	@Qualifier("roleUrlMap")
	private Properties roleUrlMap;

	public void onAuthenticationSuccess(final HttpServletRequest request,
			final HttpServletResponse response,
			final Authentication authentication) throws IOException,
			ServletException {
		final HttpSession session = request.getSession();

		try {
			if (authentication.getPrincipal() instanceof UserDetails) {
				final User user = (User) authentication.getPrincipal();

				final Set<String> roles = AuthorityUtils
						.authorityListToSet(authentication.getAuthorities());
				this.log.debug("Available Roles for user : {} ==> {} ",
						new Object[] { user, roles });

				this.log.debug("Loading user configurations for user:: {} ",
						user);
				final Map<String, String> conf = this.configurationService
						.getUserConfigurations(user);

				this.log.debug("Loading user configurations:: {} to session",
						conf);
				session.setAttribute(ETConstants.USER_CONFIG_KEY, conf);

				this.log.debug("Loading user :: {} to session", user);
				session.setAttribute("SESSION_USER", user);

				this.log.debug(">>>>>>>>>>'Password expired and first time login' implementations are pending.. ");
				// code

				//
				if (conf.containsKey("HOME_VIEW")
						&& conf.get("HOME_VIEW") != null) {
					this.log.debug("The user has set a home page , url ==> {}",
							conf.get("HOME_VIEW"));
					response.sendRedirect(request.getContextPath() + "/"
							+ conf.get("HOME_VIEW"));
				} else {
					this.log.debug("No home page has been set.Forwarding to default home page depending up on the roles");
					if (roles.contains("ROLE_ADMIN")) {
						response.sendRedirect(request.getContextPath() + "/"
								+ this.roleUrlMap.get("ROLE_ADMIN"));
					} else if (roles.contains("ROLE_USER")) {
						response.sendRedirect(request.getContextPath() + "/"
								+ this.roleUrlMap.get("ROLE_USER"));
					} else {
						response.sendRedirect(request.getContextPath() + "/"
								+ ETConstants.LOGOUT_URL);
					}
				}

			}
		} catch (final Exception e) {
			this.log.error(">>>>>>>>>>" + e);
		}

	}

}
