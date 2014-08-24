package com.pk.et.infra.dao.custom.impl;

import java.util.HashMap;
import java.util.Map;

import com.pk.et.infra.dao.custom.ISessionRegistryItemDAO;
import com.pk.et.infra.model.SessionRegistryItem;

public class SessionRegistryItemDAOImpl extends GenericDAO implements ISessionRegistryItemDAO{

	public void removeItem(String sessionId) {
		Map<String, ? super Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sessionId", sessionId);
		SessionRegistryItem item= findUniqueByCriteria(SessionRegistryItem.class,paramMap);
		if(item!=null){
			em.remove(item);
		}
	}

}
