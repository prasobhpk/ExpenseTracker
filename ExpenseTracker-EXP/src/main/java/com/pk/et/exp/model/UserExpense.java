package com.pk.et.exp.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.model.User;
import com.pk.et.infra.util.ETConstants;

/**
 * Entity implementation class for Entity: UserExpense
 * 
 */
@Entity
@Table(name = "USER_EXPENSES")
public class UserExpense extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;

	public UserExpense() {
		super();
	}

	public UserExpense(final User user) {
		setUser(user);
	}

	private Long id;
	private User user;
	private Set<Expense> expenses = new HashSet<Expense>();
	private List<ExpenseType> expenseTypes = new ArrayList<ExpenseType>();
	private List<Forecast> forecasts = new ArrayList<Forecast>();

	@Override
	@Id
	@GeneratedValue(generator = "USER_EXP_GEN", strategy = GenerationType.TABLE)
	@Column(name = "USER_EXP_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	// @MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "USER_FK", unique = true)
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
		// if(user!=null)
		// this.id=user.getId();
	}

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "userExpense")
	@OrderBy("expDate")
	public Set<Expense> getExpenses() {
		return this.expenses;
	}

	public void setExpenses(final Set<Expense> expenses) {
		this.expenses = expenses;
	}

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "userExpense")
	public List<ExpenseType> getExpenseTypes() {
		return this.expenseTypes;
	}

	public void setExpenseTypes(final List<ExpenseType> expenseTypes) {
		this.expenseTypes = expenseTypes;
	}

	public boolean addExpense(final Expense expense) {
		expense.setUserExpense(this);
		return getExpenses().add(expense);
	}

	public boolean addExpenseType(final ExpenseType type) {
		type.setUserExpense(this);
		return getExpenseTypes().add(type);
	}

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "userExpense")
	public List<Forecast> getForecasts() {
		return this.forecasts;
	}

	public void setForecasts(final List<Forecast> forecasts) {
		this.forecasts = forecasts;
	}

	public boolean addForecast(final Forecast forecast) {
		forecast.setUserExpense(this);
		return getForecasts().add(forecast);
	}

	@Override
	public String toString() {
		return "UserExpenseContext [" + this.user + "]";
	}

}
