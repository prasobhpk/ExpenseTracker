package com.pk.et.infra.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class SecurityManager {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	@Qualifier("etAuthenticationProvider")
	private ETDaoAuthenticationProvider myAuthenticationProvider;

	public SecurityManager() {

	}

	/**
	 * Encode a string using SHA 256 Alogorithm and return the resulting
	 * encrypted password. If exception, the plain credentials string is
	 * returned
	 * 
	 * @param password
	 *            Password or other credentials to use in authenticating this
	 *            username
	 * @param salt
	 *            Salt used to do the digest
	 * 
	 * @return encypted password based on the algorithm.
	 */
	public String encodePassword(final String password, final String salt) {
		final PasswordEncoder encoder = this.myAuthenticationProvider
				.getEncoder();
		final String encodedPwd = encoder.encodePassword(password, salt);
		return encodedPwd;
	}

	/** To get the Random Salt */
	public String getRandomSalt() {
		SecureRandom secureRandom = new SecureRandom();
		final byte[] bytes = new byte[20];
		try {
			secureRandom = SecureRandom.getInstance("SHA1PRNG");
		} catch (final NoSuchAlgorithmException ex) {
			this.log.warn("*********ALGORITHM NOT FOUND*************");
		}

		secureRandom.nextBytes(bytes);

		return String.valueOf(secureRandom.nextDouble());

	}

}
