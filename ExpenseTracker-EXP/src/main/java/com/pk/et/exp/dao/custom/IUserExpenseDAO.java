package com.pk.et.exp.dao.custom;

import java.util.Date;
import java.util.List;

import com.pk.et.exp.model.Expense;
import com.pk.et.exp.model.ExpenseType;
import com.pk.et.exp.model.UserExpense;
import com.pk.et.infra.model.User;
import com.pk.et.infra.util.JQResponse;
import com.pk.et.infra.util.JSONRequest;

public interface IUserExpenseDAO {
	List<Expense> getExpenses(int year, int month, User user);

	List<Integer> getAvailExpYears(User user);

	String getYearRange(User user);

	List<Expense> getExpenses(Date from, Date to, User user);
	
	boolean addExpense(Expense expense, UserExpense user);

	boolean addExpenseType(ExpenseType type, UserExpense user);
	
	JQResponse<Expense> getExpenses(JSONRequest req, User user);

	JQResponse<ExpenseType> getExpenseTypes(JSONRequest req, User user);
	
	List<ExpenseType> getExpenseTypes (User user);
}
