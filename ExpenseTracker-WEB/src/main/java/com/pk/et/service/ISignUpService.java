package com.pk.et.service;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.model.User;

public interface ISignUpService {
	@Transactional
	boolean signUp(User user);
}
