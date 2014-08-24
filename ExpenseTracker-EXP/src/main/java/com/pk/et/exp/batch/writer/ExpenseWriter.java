package com.pk.et.exp.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.pk.et.exp.model.Expense;
import com.pk.et.exp.service.IUserExpenseService;

@Component("expenseWriter")
public class ExpenseWriter implements ItemWriter<Expense> {
	@Autowired(required = true)
	@Qualifier("userExpenseService")
	private IUserExpenseService userExpenseService;

	public void write(final List<? extends Expense> expenses) throws Exception {
		this.userExpenseService.addExpenses(expenses);
	}
}
