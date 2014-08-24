package com.pk.et.exp.dao.custom;

import java.util.Date;
import java.util.List;

import com.pk.et.exp.model.Expense;
import com.pk.et.infra.model.User;

public interface IExpenseDAO {
	List<Expense> getExpenses(User user);
	
	List<Expense> getExpenses(int year, int month, User user);

	List<Number> getAvailExpYears(User user);

	String getYearRange(User user);

	List<Expense> getExpenses(Date from, Date to, User user);
}
