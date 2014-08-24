package com.pk.et.exp.dao.custom;

import com.pk.et.exp.model.ExpenseType;
import com.pk.et.infra.model.User;

public interface IExpenseTypeDAO{
	ExpenseType findByType(String type, User user);
}
