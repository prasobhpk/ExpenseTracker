package com.pk.et.infra.dao;

import com.pk.et.infra.dao.custom.IUserDAO;
import com.pk.et.infra.model.User;
import com.pk.et.infra.repository.ETRepository;

public interface UserDAO extends ETRepository<User, Long>,IUserDAO{
	User findByUsernameAndPassword(String username,String password);
	
	User findByUsername(String username);
}
