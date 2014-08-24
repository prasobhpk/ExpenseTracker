package com.pk.et.infra.dao;

import com.pk.et.infra.model.Roles;
import com.pk.et.infra.model.UserAuthority;
import com.pk.et.infra.repository.ETRepository;

public interface AuthorityDAO extends ETRepository<UserAuthority, Long>{
	UserAuthority findByRole(Roles roles);
}
