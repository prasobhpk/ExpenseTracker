package com.pk.et.infra.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public class ExpenseTrackerPermissionEvaluator implements PermissionEvaluator {

	public boolean hasPermission(final Authentication auth,
			final Object target, final Object permission) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasPermission(final Authentication arg0,
			final Serializable arg1, final String arg2, final Object arg3) {
		// TODO Auto-generated method stub
		return false;
	}

}
