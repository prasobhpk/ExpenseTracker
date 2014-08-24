package com.pk.et.exp.dao;

import com.pk.et.exp.dao.custom.IUserExpenseDAO;
import com.pk.et.exp.model.UserExpense;
import com.pk.et.infra.repository.ETRepository;


public interface UserExpenseDAO extends ETRepository<UserExpense, Long>,IUserExpenseDAO{
	UserExpense findByUserId(Long userId);
}
