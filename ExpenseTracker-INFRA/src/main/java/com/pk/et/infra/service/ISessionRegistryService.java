package com.pk.et.infra.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.model.SessionRegistryItem;
import com.pk.et.infra.model.User;

public interface ISessionRegistryService {
	@Transactional(readOnly = true)
	List<Object> getAllPrincipals();

	@Transactional
	void registerNewSession(String sessionId, User principal);

	@Transactional
	void removeItem(String sessionId);

	@Transactional
	void refreshLastRequest(String sessionId);

	@Transactional(readOnly = true)
	List<SessionRegistryItem> getAllSessions(Object principal, boolean isExpired);

	@Transactional
	SessionRegistryItem loadBySessionId(String sessionId);
}
