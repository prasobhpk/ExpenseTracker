package com.pk.et.infra.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;

import com.pk.et.infra.model.SessionRegistryItem;
import com.pk.et.infra.model.User;
import com.pk.et.infra.service.ISessionRegistryService;

public class ETSessionRegistry extends SessionRegistryImpl {
	private final Logger log = LoggerFactory.getLogger(getClass());
	private ISessionRegistryService registryService;

	@Autowired(required = true)
	@Qualifier("sessionRegistryService")
	public void setRegistryService(final ISessionRegistryService registryService) {
		this.registryService = registryService;
	}

	@Override
	public List<Object> getAllPrincipals() {
		this.log.debug("Loading all available principals in the session registry.....");
		return this.registryService.getAllPrincipals();
	}

	@Override
	public List<SessionInformation> getAllSessions(final Object principal,
			final boolean isExpired) {
		this.log.debug("Loading all session information available for the principal ::"
				+ principal);
		final List<SessionInformation> list = new ArrayList<SessionInformation>();
		SessionInformation information = null;
		for (final SessionRegistryItem item : this.registryService
				.getAllSessions(principal, isExpired)) {
			information = new SessionInformation(item.getPrincipal(),
					item.getSessionId(), item.getLastRequest());
			list.add(information);
		}
		return list;
	}

	@Override
	public SessionInformation getSessionInformation(final String sessionId) {
		this.log.debug("Loading session info for session id ::" + sessionId);
		/**
		 * Commented to avoid the db interaction for each web request
		 */
		/*
		 * SessionRegistryItem item =
		 * registryService.loadBySessionId(sessionId); if (item != null) {
		 * return new SessionInformation(item.getPrincipal(),
		 * item.getSessionId(), item.getLastRequest()); } return null;
		 */
		return super.getSessionInformation(sessionId);
	}

	@Override
	public void onApplicationEvent(final SessionDestroyedEvent event) {
		super.onApplicationEvent(event);
	}

	@Override
	public synchronized void registerNewSession(final String sessionId,
			final Object principal) {
		final User user = (User) principal;
		this.log.debug("Registering a new session for user ::"
				+ user.getUsername() + " with session id ::" + sessionId);
		this.registryService.registerNewSession(sessionId, user);
	}

	@Override
	public void removeSessionInformation(final String sessionId) {
		this.log.debug("Deleting session info with session id ::" + sessionId);
		if (sessionId != null && !"".equals(sessionId)) {
			this.registryService.removeItem(sessionId);
		}
	}

	@Override
	public void refreshLastRequest(final String sessionId) {
		this.log.debug("Refreshing Session ::" + sessionId);
		this.registryService.refreshLastRequest(sessionId);
	}
}
