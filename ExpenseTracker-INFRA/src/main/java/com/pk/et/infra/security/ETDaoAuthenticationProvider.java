package com.pk.et.infra.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class ETDaoAuthenticationProvider extends DaoAuthenticationProvider {
	private final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * overrides implementation from DaoAuthenticationProvider. compares the
	 * hashed password submitted from page with the hashed password stored in
	 * database.
	 * 
	 */
	@Override
	protected void additionalAuthenticationChecks(
			final UserDetails userDetails,
			final UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		final String salt = getSaltSource().getSalt(userDetails).toString();
		this.log.debug("THE SALT IS :::::::" + salt);
		if (authentication.getCredentials() == null) {
			this.log.debug(">>>>>>Password is empty...");
			throw new BadCredentialsException(this.messages.getMessage(
					"ET-SEC.emptypwd", "Bad credentials"));
		}
		final String tokenHashedUiPwd = authentication.getCredentials()
				.toString();
		this.log.debug("Presented UI Password >>>" + tokenHashedUiPwd);

		final String saltTokenHashedUiPwd = getPasswordEncoder()
				.encodePassword(tokenHashedUiPwd, salt);
		this.log.debug("saltTokenHashedUiPwd is  " + saltTokenHashedUiPwd);

		final String token = ((WebAuthenticationDetails) authentication
				.getDetails()).getSessionId();
		this.log.debug("SESSION ID OR TOKEN IS :::::" + token);

		this.log.debug("password of database is  " + userDetails.getPassword());

		final String tokenHashedDbPwd = getPasswordEncoder().encodePassword(
				userDetails.getPassword(), token);

		this.log.debug("saltTokenHashedDbPwd Password >>>"
				+ getPasswordEncoder().encodePassword(tokenHashedDbPwd, salt));

		if (!getPasswordEncoder().isPasswordValid(saltTokenHashedUiPwd,
				tokenHashedDbPwd, salt)) {
			this.log.info("UNSUCCESSFUL LOGIN ############ Incorrect password");
			throw new BadCredentialsException(this.messages.getMessage(
					"ET-SEC.badpwd", "Bad credentials"));
		}
	}

	public PasswordEncoder getEncoder() {
		return getPasswordEncoder();
	}
}
