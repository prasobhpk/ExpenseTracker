package com.pk.et.infra.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.pk.et.infra.model.User;
import com.pk.et.infra.service.IUserContextService;

@Service("userContextService")
public class UserContextService implements IUserContextService {

	public User getCurrentUser() {
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return (User) principal;
		} else {
			return null;
		}
	}

}
