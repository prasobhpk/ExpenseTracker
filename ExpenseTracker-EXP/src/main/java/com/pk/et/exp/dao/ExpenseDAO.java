package com.pk.et.exp.dao;

import com.pk.et.exp.dao.custom.IExpenseDAO;
import com.pk.et.exp.model.Expense;
import com.pk.et.infra.repository.ETRepository;

public interface ExpenseDAO extends ETRepository<Expense, Long>,IExpenseDAO {
}
