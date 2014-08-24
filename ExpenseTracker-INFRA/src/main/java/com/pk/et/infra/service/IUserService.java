package com.pk.et.infra.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.model.User;



@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
public interface IUserService {
	
	@Transactional
	// @PreAuthorize("hasPermission(#user,'create')")
	User createUser(User user);
	
	@Transactional
	boolean changePassowrd(Long userId, String oldPassword, String newPassword);

	@Transactional(readOnly = true)
	boolean userExists(String userName);
}
