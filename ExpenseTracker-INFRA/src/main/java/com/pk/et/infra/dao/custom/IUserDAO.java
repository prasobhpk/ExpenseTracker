package com.pk.et.infra.dao.custom;

public interface IUserDAO {
	boolean changePassowrd(Long userId, String oldPassword, String newPassword);

	boolean userExists(String userName);
}
