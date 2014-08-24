package com.pk.et.exp.dao.custom.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.pk.et.exp.dao.custom.IUserExpenseDAO;
import com.pk.et.exp.model.Expense;
import com.pk.et.exp.model.ExpenseType;
import com.pk.et.exp.model.ExpenseType_;
import com.pk.et.exp.model.Expense_;
import com.pk.et.exp.model.UserExpense;
import com.pk.et.exp.model.UserExpense_;
import com.pk.et.infra.dao.custom.impl.GenericDAO;
import com.pk.et.infra.model.User;
import com.pk.et.infra.model.User_;
import com.pk.et.infra.util.DateUtil;
import com.pk.et.infra.util.JQResponse;
import com.pk.et.infra.util.JSONRequest;
import com.pk.et.infra.util.SearchRule;

public class UserExpenseDAOImpl extends GenericDAO implements IUserExpenseDAO {
	@PersistenceContext
	private EntityManager em;

	public List<Expense> getExpenses(final int year, final int month,
			final User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Integer> getAvailExpYears(final User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getYearRange(final User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Expense> getExpenses(final Date from, final Date to,
			final User user) {
		return null;
	}

	public boolean addExpense(final Expense expense, final UserExpense user) {
		return user.addExpense(expense);
	}

	public boolean addExpenseType(final ExpenseType type, final UserExpense user) {
		return user.addExpenseType(type);
	}

	public List<ExpenseType> getExpenseTypes(final User user) {
		try {
			final CriteriaBuilder cb = this.em.getCriteriaBuilder();
			final CriteriaQuery<ExpenseType> cr = cb
					.createQuery(ExpenseType.class);
			final Root<UserExpense> root = cr.from(UserExpense.class);
			final Join<UserExpense, ExpenseType> expTypes = root
					.join(UserExpense_.expenseTypes);
			cr.select(expTypes);
			cr.where(cb.equal(root.get(UserExpense_.user).get(User_.id),
					user.getId()));
			return this.em.createQuery(cr).getResultList();
		} catch (final NoResultException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public JQResponse<Expense> getExpenses(final JSONRequest req,
			final User user) {

		List<Expense> gridModel = null;

		int totalPages = 0;

		int records = 0;

		// Calcalate until rows are selected

		int to = (req.getRows() * req.getPage());

		// Calculate the first r! ow to read

		final int from = to - req.getRows();

		// Criteria to Build SQL

		final CriteriaBuilder cb = getCriteriaBuilder();

		final CriteriaQuery<Expense> cr = cb.createQuery(Expense.class);

		final CriteriaQuery<Long> countQ = cb.createQuery(Long.class);

		final Root<Expense> root = cr.from(Expense.class);

		final Root<Expense> root1 = countQ.from(Expense.class);

		final List<Predicate> predicates = new ArrayList<Predicate>();

		predicates.add(cb.equal(
				root.get(Expense_.userExpense).get(UserExpense_.user)
						.get(User_.id), user.getId()));

		Predicate p = null;

		// take the messages of the particular user

		if (req.isSearch() && (req.getSearchFilter() != null)) {

			for (final SearchRule filter : req.getSearchFilter().getRules()) {

				if ("expDate".equals(filter.getField())) {

					// criteria.createCriteria("from").add(

					// Restrictions.eq("userName", filter.getData()));

					final Date dt = DateUtil.getDate(filter.getData(),
							"MM/dd/yyyy");

					if (dt != null) {

						if ("lt".equals(filter.getOp())) {

							p = cb.lessThan(root.get(Expense_.expDate), dt);

						} else if ("eq".equals(filter.getOp())) {

							p = cb.equal(root.get(Expense_.expDate), dt);

						} else if ("gt".equals(filter.getOp())) {

							p = cb.greaterThan(root.get(Expense_.expDate), dt);

						}

						if (p != null) {

							predicates.add(p);

							p = null;

						}

					}

				} else if ("expense".equals(filter.getField())) {

					try {

						final int val = Integer.valueOf(filter.getData());

						if ("lt".equals(filter.getOp())) {

							p = cb.lessThan(root.get(Expense_.expense), val);

						} else if ("eq".equals(filter.getOp())) {

							p = cb.equal(root.get(Expense_.expense), val);

						} else if ("gt".equals(filter.getOp())) {

							p = cb.greaterThan(root.get(Expense_.expense), val);

						}

					} catch (final NumberFormatException e) {

						System.out.println(e.getMessage());

					}

					if (p != null) {

						predicates.add(p);

						p = null;

					}

				} else if ("description".equals(filter.getField())) {

					if ("eq".equals(filter.getOp())) {

						p = cb.equal(root.get(Expense_.description),
								filter.getData());

					} else if ("bw".equals(filter.getOp())) {

						p = cb.like(root.get(Expense_.description),
								filter.getData() + "%");

					}

					if (p != null) {

						predicates.add(p);

						p = null;

					}

				} else if ("active".equals(filter.getField())) {

					final Boolean val = Boolean.parseBoolean(filter.getData());

					if ("eq".equals(filter.getOp())) {

						p = cb.equal(root.get(Expense_.active), val);

					}

					if (p != null) {

						predicates.add(p);

						p = null;

					}

				} else if ("expenseType.type".equals(filter.getField())) {
					if ("eq".equals(filter.getOp())) {
						p = cb.equal(
								root.get(Expense_.expenseType).get(
										ExpenseType_.type), filter.getData());
					}

					if (p != null) {

						predicates.add(p);

						p = null;

					}
				}

			}

			if ("AND".equals(req.getSearchFilter().getGroupOp())) {

				p = cb.and(predicates.toArray(new Predicate[predicates.size()]));

			} else if ("OR".equals(req.getSearchFilter().getGroupOp())) {

				p = cb.or(predicates.toArray(new Predicate[predicates.size()]));

			}

		} else {

			if (predicates.size() == 1) {

				p = predicates.get(0);

			}

		}

		// Count Orders cq.select(cb.count(cq.from(MyEntity.class)));

		countQ.select(cb.countDistinct(root1));

		countQ.where(p);

		records = this.em.createQuery(countQ).getSingleResult().intValue();

		// Handle Order By

		Order order = null;

		if ((req.getSortField() != null) && !req.getSortField().equals("")) {

			if (req.getSortOrder().equals("asc")) {
				order = cb.asc(root.get(req.getSortField()));
			} else {
				order = cb.desc(root.get(req.getSortField()));
			}

		} else {

			order = cb.desc(root.get(Expense_.expDate));

		}

		root.fetch(Expense_.expenseType);
		cr.select(root);

		cr.where(p);

		cr.orderBy(order);

		final TypedQuery<Expense> query = this.em.createQuery(cr);

		query.setFirstResult(from);

		query.setMaxResults(req.getRows());

		// Get Customers by Criteria

		gridModel = query.getResultList();

		// Set to = max rows

		if (to > records) {
			to = records;
		}

		// Calculate total Pages

		totalPages = (int) Math.ceil((double) records / (double) req.getRows());

		return new JQResponse<Expense>(req.getPage(), records, totalPages,
				gridModel);

	}

	public JQResponse<ExpenseType> getExpenseTypes(final JSONRequest req,
			final User user) {
		List<ExpenseType> gridModel = null;
		int totalPages = 0;
		int records = 0;
		// Calcalate until rows are selected
		int to = (req.getRows() * req.getPage());
		// Calculate the first r! ow to read
		final int from = to - req.getRows();
		// Criteria to Build SQL
		final Map<String, ? super Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userExpense.user.id", user.getId());
		records = findCountByCriteria(ExpenseType.class, paramMap);

		final CriteriaBuilder cb = this.em.getCriteriaBuilder();
		final CriteriaQuery<ExpenseType> typeQ = cb
				.createQuery(ExpenseType.class);
		final Root<ExpenseType> root = typeQ.from(ExpenseType.class);
		final Predicate p = cb.equal(
				root.get(ExpenseType_.userExpense).get(UserExpense_.user)
						.get(User_.id), user.getId());

		// Handle Order By
		final Order[] order = new Order[2];
		order[0] = cb.desc(root.get(ExpenseType_.type));
		if ((req.getSortField() != null) && !req.getSortField().equals("")) {
			if (req.getSortOrder().equals("asc")) {
				order[1] = cb.asc(root.get(req.getSortField()));
			} else {
				order[1] = cb.desc(root.get(req.getSortField()));
			}
		}
		typeQ.select(root);
		typeQ.where(p);
		typeQ.orderBy(order);
		final TypedQuery<ExpenseType> query = this.em.createQuery(typeQ);
		query.setFirstResult(from);
		query.setMaxResults(req.getRows());
		// Get Customers by Criteria
		gridModel = query.getResultList();

		// Set to = max rows
		if (to > records) {
			to = records;
		}

		// Calculate total Pages
		totalPages = (int) Math.ceil((double) records / (double) req.getRows());
		return new JQResponse<ExpenseType>(req.getPage(), records, totalPages,
				gridModel);
	}

}
