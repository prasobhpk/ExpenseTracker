package com.pk.et.infra.model;

import java.util.Date;

public class SessionInformation extends
		org.springframework.security.core.session.SessionInformation {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SessionInformation(Object principal, String sessionId,
			Date lastRequest) {
		super(principal, sessionId, lastRequest);
	}
	
}
