package com.pk.et.exp.dao.custom.impl;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.pk.et.exp.dao.custom.IExpenseTypeDAO;
import com.pk.et.exp.model.ExpenseType;
import com.pk.et.exp.model.ExpenseType_;
import com.pk.et.exp.model.UserExpense_;
import com.pk.et.infra.dao.custom.impl.GenericDAO;
import com.pk.et.infra.model.User;
import com.pk.et.infra.model.User_;

public class ExpenseTypeDAOImpl extends GenericDAO implements IExpenseTypeDAO {
	public ExpenseType findByType(final String type, final User user) {
		try {
			final CriteriaBuilder cb = getCriteriaBuilder();
			final CriteriaQuery<ExpenseType> cr = cb
					.createQuery(ExpenseType.class);
			final Root<ExpenseType> root = cr.from(ExpenseType.class);
			cr.select(root);
			final Predicate idRes = cb.equal(root.get(ExpenseType_.userExpense)
					.get(UserExpense_.user).get(User_.id), user.getId());
			final Predicate typeRes = cb.equal(root.get(ExpenseType_.type),
					type);
			cr.where(cb.and(idRes, typeRes));
			return this.em.createQuery(cr).getSingleResult();
		} catch (final NoResultException e) {
			e.printStackTrace();
			return null;
		}
	}
}
