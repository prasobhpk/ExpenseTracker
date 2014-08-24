package com.pk.et.infra.dao;

import java.util.List;

import com.pk.et.infra.dao.custom.ISessionRegistryItemDAO;
import com.pk.et.infra.model.SessionRegistryItem;
import com.pk.et.infra.repository.ETRepository;

public interface SessionRegistryItemDAO extends ETRepository<SessionRegistryItem, Long>,ISessionRegistryItemDAO{
	
	SessionRegistryItem findBySessionId(String sessionId);
	
	List<SessionRegistryItem> findByExpired(boolean expired);
}
