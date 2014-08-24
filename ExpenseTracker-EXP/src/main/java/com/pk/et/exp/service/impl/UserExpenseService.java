package com.pk.et.exp.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pk.et.exp.dao.ExpenseDAO;
import com.pk.et.exp.dao.ExpenseTypeDAO;
import com.pk.et.exp.dao.ForecastDAO;
import com.pk.et.exp.dao.UserExpenseDAO;
import com.pk.et.exp.model.Expense;
import com.pk.et.exp.model.ExpenseType;
import com.pk.et.exp.model.Forecast;
import com.pk.et.exp.model.ForecastType;
import com.pk.et.exp.model.UserExpense;
import com.pk.et.exp.service.IUserExpenseService;
import com.pk.et.exp.web.command.ExpensesCommand;
import com.pk.et.infra.model.User;
import com.pk.et.infra.service.IUserContextService;
import com.pk.et.infra.util.DateUtil;
import com.pk.et.infra.util.JQResponse;
import com.pk.et.infra.util.JSONRequest;

@Service("userExpenseService")
public class UserExpenseService implements IUserExpenseService {
	@Autowired
	private UserExpenseDAO userExpenseDAO;

	@Autowired
	private ExpenseDAO expenseDAO;

	@Autowired
	private ExpenseTypeDAO expenseTypeDAO;

	@Autowired
	private ForecastDAO forecastDAO;

	@Autowired(required = true)
	@Qualifier("userContextService")
	private IUserContextService userContextService;

	public UserExpense createExpenseContext(final User user) {
		final UserExpense userExpense = new UserExpense(user);
		return this.userExpenseDAO.save(userExpense);
	}

	public boolean addExpense(final Expense expense, final User user,
			final Long typeId) {
		final UserExpense u = this.userExpenseDAO.findByUserId(user.getId());
		expense.setExpenseType(this.expenseTypeDAO.getReference(typeId));
		return this.userExpenseDAO.addExpense(expense, u);
	}

	public void addExpenses(final List<? extends Expense> expenses) {
		final User sessionUser = this.userContextService.getCurrentUser();
		final UserExpense u = this.userExpenseDAO.findByUserId(sessionUser
				.getId());
		for (final Expense e : expenses) {
			ExpenseType type = this.expenseTypeDAO.findByType(e
					.getExpenseType().getType(), sessionUser);
			if (type == null) {
				type = e.getExpenseType();
				type.setUserExpense(u);
				type = this.expenseTypeDAO.save(type);
			}
			e.setExpenseType(type);
			u.addExpense(e);
		}

	}

	public void addExpenses(final List<Expense> expenses, final User user) {
		final UserExpense u = this.userExpenseDAO.findByUserId(user.getId());
		for (final Expense e : expenses) {
			u.addExpense(e);
		}
	}

	public void addExpenses(final ExpensesCommand command, final User user) {
		final UserExpense u = this.userExpenseDAO.findByUserId(user.getId());
		try {
			if (command != null) {
				for (final Expense expense : command.getExpenses()) {
					expense.setExpenseType(this.expenseTypeDAO.findOne(expense
							.getTypeId()));
					u.addExpense(expense);
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public Set<Expense> getExpenses(final User user) {
		return new HashSet<Expense>(this.expenseDAO.getExpenses(user));
	}

	public JQResponse<Expense> getExpenses(final JSONRequest req,
			final User user) {
		return this.userExpenseDAO.getExpenses(req, user);
	}

	public String getExpenses(final int year, final User user) {
		final StringBuilder sb = new StringBuilder("<data>");
		for (int i = 0; i < 12; i++) {
			sb.append("<variable name=\"" + DateUtil.getMonthName(i) + "\">");
			final List<Expense> expenses = this.expenseDAO.getExpenses(year,
					i + 1, user);
			if (expenses.size() > 0) {
				final Expense e = expenses.get(0);
				final int day = DateUtil.getDayOfMonth(e.getExpDate());
				System.out.println("Day of Month :" + day + " >>"
						+ e.getExpDate());
				if (day > 1) {
					for (int j = 1; j < day; j++) {
						sb.append("<row>");
						sb.append("<column>");
						sb.append("</column>");
						sb.append("</row>");
					}
				}
				Date prv = null;
				for (final Expense exp : expenses) {
					// to track the unfilled days..
					if (prv != null) {
						int diff = DateUtil.getDayOfMonth(exp.getExpDate())
								- DateUtil.getDayOfMonth(prv);
						while (diff > 1) {
							sb.append("<row>");
							sb.append("<column>");
							sb.append("</column>");
							sb.append("</row>");
							diff--;
						}
					}
					sb.append("<row><column>" + exp.getExpense()
							+ "</column></row>");
					prv = exp.getExpDate();
				}
			}
			sb.append("</variable>");
		}
		sb.append("</data>");
		return sb.toString();
	}

	public List<Expense> getExpenses(final Date from, final Date to,
			final User user) {
		return this.expenseDAO.getExpenses(from, to, user);
	}

	public boolean updateExpense(final Expense expense) {
		final Expense exp = this.expenseDAO.findOne(expense.getId());
		exp.setExpense(expense.getExpense());
		exp.setDescription(expense.getDescription());
		exp.setActive(expense.isActive());
		return !(null == this.expenseDAO.save((exp)));
	}

	public void deleteExpense(final long id) {
		this.expenseDAO.delete(id);
	}

	public List<Number> getYears(final User user) {
		return this.expenseDAO.getAvailExpYears(user);
	}

	public String getYearRange(final User user) {
		return this.expenseDAO.getYearRange(user);
	}

	public boolean addExpenseType(final ExpenseType type, final User user) {
		final UserExpense u = this.userExpenseDAO.findByUserId(user.getId());
		return this.userExpenseDAO.addExpenseType(type, u);
	}

	public List<ExpenseType> getExpenseTypes(final User u) {
		// return expenseTypeDAO.findByUserExpenseUserId(u.getId());
		return this.userExpenseDAO.getExpenseTypes(u);
	}

	public JQResponse<ExpenseType> getExpenseTypes(final JSONRequest req,
			final User user) {
		return this.userExpenseDAO.getExpenseTypes(req, user);
	}

	public void updateExpenseType(final ExpenseType type, final User user) {
		final ExpenseType tmp = this.expenseTypeDAO.findOne(type.getId());
		tmp.setType(type.getType());
		tmp.setDescription(type.getDescription());
		tmp.setShowInDashboard(type.isShowInDashboard());
		this.expenseTypeDAO.save(tmp);
	}

	public void deleteExpenseType(final Long typeId) {
		this.expenseTypeDAO.delete(typeId);
	}

	public boolean addForecastExpenses(final Forecast forecast, final User user) {
		final UserExpense userExpense = this.userExpenseDAO.findByUserId(user
				.getId());
		return userExpense.addForecast(forecast);
	}

	public List<Forecast> getAllForcastsByUserForMonth(final User user,
			final LocalDate month, final ForecastType forecastType) {
		return this.forecastDAO.getAllForcastsByUserForMonth(user, month,
				forecastType);
	}

	public BigDecimal findForecastAmountForMonth(final User user,
			final LocalDate month, final ForecastType forecastType) {
		return this.forecastDAO.findForecastAmountForMonth(user, month,
				forecastType);
	}

	public Forecast findForecast(final User user, final Long forecastId) {
		return this.forecastDAO.findOne(forecastId);
	}

}
