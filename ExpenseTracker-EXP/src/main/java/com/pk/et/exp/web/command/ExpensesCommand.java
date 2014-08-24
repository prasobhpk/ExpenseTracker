package com.pk.et.exp.web.command;

import org.springframework.util.AutoPopulatingList;

import com.pk.et.exp.model.Expense;

public class ExpensesCommand {
	private AutoPopulatingList<Expense> expenses = new AutoPopulatingList<Expense>(
			Expense.class);

	public AutoPopulatingList<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(AutoPopulatingList<Expense> expenses) {
		this.expenses = expenses;
	}

}
