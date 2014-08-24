package com.pk.et.exp.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.springframework.transaction.annotation.Transactional;

import com.pk.et.exp.model.Expense;
import com.pk.et.exp.model.ExpenseType;
import com.pk.et.exp.model.Forecast;
import com.pk.et.exp.model.ForecastType;
import com.pk.et.exp.model.UserExpense;
import com.pk.et.exp.web.command.ExpensesCommand;
import com.pk.et.infra.model.User;
import com.pk.et.infra.util.JQResponse;
import com.pk.et.infra.util.JSONRequest;

public interface IUserExpenseService {

	@Transactional
	UserExpense createExpenseContext(User user);

	@Transactional(readOnly = true)
	JQResponse<Expense> getExpenses(JSONRequest req, User user);

	@Transactional
	boolean addExpense(Expense expense, User user, Long typeId);

	@Transactional
	boolean addExpenseType(ExpenseType type, User user);

	@Transactional
	void addExpenses(List<? extends Expense> expenses);

	@Transactional
	void addExpenses(List<Expense> expenses, User user);

	@Transactional
	void addExpenses(ExpensesCommand command, User user);

	@Transactional(readOnly = true)
	Set<Expense> getExpenses(User user);

	@Transactional
	boolean updateExpense(Expense expense);

	@Transactional
	void deleteExpense(long id);

	// For dashboard
	@Transactional(readOnly = true)
	String getExpenses(int year, User user);

	// For dashboard
	@Transactional(readOnly = true)
	List<Number> getYears(User user);

	// For datepicker
	@Transactional(readOnly = true)
	String getYearRange(User user);

	@Transactional(readOnly = true)
	List<Expense> getExpenses(Date from, Date to, User user);

	@Transactional(readOnly = true)
	List<ExpenseType> getExpenseTypes(User u);

	@Transactional(readOnly = true)
	JQResponse<ExpenseType> getExpenseTypes(JSONRequest req, User user);

	@Transactional
	void updateExpenseType(ExpenseType type, User user);

	@Transactional
	void deleteExpenseType(Long typeId);

	@Transactional
	boolean addForecastExpenses(Forecast forecast, User user);

	@Transactional(readOnly = true)
	List<Forecast> getAllForcastsByUserForMonth(User user, LocalDate month,
			ForecastType forecastType);

	@Transactional(readOnly = true)
	BigDecimal findForecastAmountForMonth(User user, LocalDate month,
			ForecastType forecastType);

	@Transactional(readOnly = true)
	Forecast findForecast(final User user, final Long forecastId);
}
