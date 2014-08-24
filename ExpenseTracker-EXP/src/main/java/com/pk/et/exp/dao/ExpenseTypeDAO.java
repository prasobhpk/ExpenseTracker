package com.pk.et.exp.dao;

import java.util.List;

import com.pk.et.exp.dao.custom.IExpenseTypeDAO;
import com.pk.et.exp.model.ExpenseType;
import com.pk.et.infra.repository.ETRepository;

public interface ExpenseTypeDAO extends ETRepository<ExpenseType, Long> ,IExpenseTypeDAO{
	List<ExpenseType> findByUserExpenseUserId(Long id);
}
