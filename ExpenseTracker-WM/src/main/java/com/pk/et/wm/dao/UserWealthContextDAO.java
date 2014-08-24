package com.pk.et.wm.dao;

import com.pk.et.infra.repository.ETRepository;
import com.pk.et.wm.dao.custom.IUserWealthContextDAO;
import com.pk.et.wm.model.UserWealthContext;

public interface UserWealthContextDAO extends ETRepository<UserWealthContext, Long>,IUserWealthContextDAO{
	UserWealthContext findByUserId(Long userId);
}
