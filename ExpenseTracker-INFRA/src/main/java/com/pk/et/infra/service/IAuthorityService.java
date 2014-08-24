package com.pk.et.infra.service;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.model.Roles;
import com.pk.et.infra.model.UserAuthority;

public interface IAuthorityService {
	@Transactional(readOnly = true)
	UserAuthority getAuthority(Roles role);

	@Transactional
	UserAuthority saveAuthority(UserAuthority authority);

	@Transactional(readOnly = true)
	long getCount();
}
