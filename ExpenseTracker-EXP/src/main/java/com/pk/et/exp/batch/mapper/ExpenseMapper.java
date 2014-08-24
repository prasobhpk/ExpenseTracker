package com.pk.et.exp.batch.mapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import com.pk.et.exp.dao.ExpenseTypeDAO;
import com.pk.et.exp.model.Expense;
import com.pk.et.exp.model.ExpenseType;
import com.pk.et.exp.service.IUserExpenseService;
import com.pk.et.infra.model.User;
import com.pk.et.infra.service.IUserContextService;

@Component("expenseMapper")
public class ExpenseMapper implements FieldSetMapper<Expense> {
	@Autowired(required = true)
	@Qualifier("userContextService")
	private IUserContextService userContextService;
	@Autowired
	private ExpenseTypeDAO expenseTypeDAO;
	@Autowired
	private IUserExpenseService userExpenseService;
	private final static String DATE_PATTERN = "mm/DD/yy";

	public Expense mapFieldSet(final FieldSet fs) throws BindException {
		final User user = this.userContextService.getCurrentUser();
		final Expense expense = new Expense();
		expense.setDescription(fs.readString("description"));
		expense.setExpense(fs.readInt("expense"));
		expense.setExpDate(fs.readDate("expDate", DATE_PATTERN));
		final String type = fs.readString("type");
		if ((type != null) && (type != "")) {
			ExpenseType typ = this.expenseTypeDAO.findByType(type, user);
			// if the type does not exists create a new one....
			if (typ == null) {
				final ExpenseType expenseType = new ExpenseType(type);
				this.userExpenseService.addExpenseType(expenseType, user);
				typ = this.expenseTypeDAO.findByType(type, user);
			}
			expense.setTypeId(typ.getId());
		}
		return expense;
	}
}
