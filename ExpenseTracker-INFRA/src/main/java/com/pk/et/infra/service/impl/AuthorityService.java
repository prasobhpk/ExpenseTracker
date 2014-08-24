package com.pk.et.infra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.infra.dao.AuthorityDAO;
import com.pk.et.infra.model.Roles;
import com.pk.et.infra.model.UserAuthority;
import com.pk.et.infra.service.IAuthorityService;

@Service("authorityService")
public class AuthorityService implements IAuthorityService {
	@Autowired
	private AuthorityDAO authorityDAO;

	public UserAuthority getAuthority(final Roles role) {
		return this.authorityDAO.findByRole(role);
	}

	public UserAuthority saveAuthority(final UserAuthority authority) {
		return this.authorityDAO.save(authority);
	}

	public long getCount() {
		return this.authorityDAO.count();
	}
}
