package com.pk.et.infra.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.infra.dao.SessionRegistryItemDAO;
import com.pk.et.infra.model.SessionRegistryItem;
import com.pk.et.infra.model.User;
import com.pk.et.infra.service.ISessionRegistryService;

@Service("sessionRegistryService")
public class SessionRegistryService implements ISessionRegistryService {
	@Autowired(required = true)
	// @Qualifier("sessionRegistryItemDAO")
	private SessionRegistryItemDAO registryItemDAO;

	public List<Object> getAllPrincipals() {
		final List<Object> principals = new ArrayList<Object>();
		for (final SessionRegistryItem item : this.registryItemDAO.findAll()) {
			principals.add(item.getPrincipal());
		}
		return principals;
	}

	public void registerNewSession(final String sessionId, final User principal) {
		final SessionRegistryItem item = new SessionRegistryItem();
		item.setSessionId(sessionId);
		item.setPrincipal(principal);
		try {
			this.registryItemDAO.save(item);
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	public void removeItem(final String sessionId) {
		try {
			this.registryItemDAO.removeItem(sessionId);
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	public void refreshLastRequest(final String sessionId) {
		try {
			final SessionRegistryItem item = this.registryItemDAO
					.findBySessionId(sessionId);
			item.setLastRequest(new Date());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public List<SessionRegistryItem> getAllSessions(final Object principal,
			final boolean isExpired) {
		return this.registryItemDAO.findByExpired(isExpired);
	}

	public SessionRegistryItem loadBySessionId(final String sessionId) {
		SessionRegistryItem item = null;
		try {
			item = this.registryItemDAO.findBySessionId(sessionId);
		} catch (final Exception e) {
			// TODO: handle exception
		}
		// Refreshing the session last request
		if (item != null) {
			item.setLastRequest(new Date());
		}
		return item;
	}
}
