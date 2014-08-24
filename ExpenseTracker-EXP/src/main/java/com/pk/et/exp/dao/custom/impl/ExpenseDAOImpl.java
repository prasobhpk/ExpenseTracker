package com.pk.et.exp.dao.custom.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.pk.et.exp.dao.custom.IExpenseDAO;
import com.pk.et.exp.model.Expense;
import com.pk.et.exp.model.ExpenseType_;
import com.pk.et.exp.model.Expense_;
import com.pk.et.exp.model.UserExpense_;
import com.pk.et.infra.dao.custom.impl.GenericDAO;
import com.pk.et.infra.model.User;
import com.pk.et.infra.model.User_;
import com.pk.et.infra.util.DatePart;

public class ExpenseDAOImpl extends GenericDAO implements
		IExpenseDAO {

	public List<Expense> getExpenses(int year, int month, User user) {

		// String sql =
		// "select new Expense(e.expDate,sum(e.expense))from Expense e  where e.user.id=:uid and year(e.expDate)=:year and month(e.expDate)=:month group by e.expDate order by e.expDate asc";
		// Query q = getSession().createQuery(sql);
		// q.setParameter("year", year);
		// q.setParameter("month", month);
		// q.setParameter("uid", user.getId());
		// @SuppressWarnings("unchecked")
		// List<Expense> expList = (List<Expense>) q.list();
		// return expList;
		log.debug("Entering getExpenses(year={}, month={},user={})",
				new Object[] { year, month, user });
		List<Expense> expList = null;
		try {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<Expense> cr = cb.createQuery(Expense.class);
			Root<Expense> root = cr.from(Expense.class);
			cr.select(cb.construct(Expense.class, root.get(Expense_.expDate),
					cb.sum(root.get(Expense_.expense))));
			Predicate idRes = cb.equal(root.get(Expense_.userExpense).get(UserExpense_.user).get(User_.id),
					user.getId());
			Predicate yearRes = cb.equal(getDatePart(cb, root.get(Expense_.expDate), DatePart.YEAR),year);
			Predicate monthRes = cb.equal(getDatePart(cb, root.get(Expense_.expDate), DatePart.MONTH),month);// (month<10?"0"+month:""+month));
			Predicate showRes = cb
					.equal(root.get(Expense_.expenseType)
							.get(ExpenseType_.showInDashboard), true);
			Predicate activeRes=cb.equal(root.get(Expense_.active), true);
			cr.where(cb.and(idRes, yearRes, monthRes, showRes,activeRes));
			cr.groupBy(root.get(Expense_.expDate));
			cr.orderBy(cb.asc(root.get(Expense_.expDate)));
			TypedQuery<Expense> query = em.createQuery(cr);
			expList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			expList = Collections.emptyList();
		}
		log.debug("Leaving getExpenses(year,month,user): {}", expList);
		return expList;
	}

	public List<Expense> getExpenses(Date from, Date to, User user) {
		log.debug("Entering getExpenses(from={}, to={},user={})", new Object[] {
				from, to, user });
		// Criteria criteria = getSession().createCriteria(Expense.class);
		// criteria.add(Restrictions.eq("user.id", user.getId()));
		// criteria.add(Restrictions.between("expDate", from, to));
		// System.out.println(criteria.list().size());
		// @SuppressWarnings("unchecked")
		// List<Expense> expList = (List<Expense>) criteria.list();
		List<Expense> expList = null;
		try {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<Expense> cr = cb.createQuery(Expense.class);
			Root<Expense> root = cr.from(Expense.class);
			cr.select(root);
			Predicate idRes = cb.equal(root.get(Expense_.userExpense).get(UserExpense_.user).get(User_.id),user.getId());
			Predicate btw = cb.between(root.get(Expense_.expDate), from, to);
			cr.where(cb.and(idRes, btw));
			TypedQuery<Expense> query = em.createQuery(cr);
			expList = query.getResultList();
		} catch (Exception e) {
			expList = Collections.emptyList();
			e.printStackTrace();
		}
		log.debug("Leaving getExpenses(from,to,user): {}", expList);
		return expList;
	}
	
	public List<Expense> getExpenses(User user) {
		log.debug("Entering getExpenses(user={})", new Object[] {user});
		List<Expense> expList = null;
		try {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<Expense> cr = cb.createQuery(Expense.class);
			Root<Expense> root = cr.from(Expense.class);
			cr.select(root);
			Predicate idRes = cb.equal(root.get(Expense_.userExpense).get(UserExpense_.user).get(User_.id),user.getId());
			cr.where(idRes);
			TypedQuery<Expense> query = em.createQuery(cr);
			expList = query.getResultList();
		} catch (Exception e) {
			expList = Collections.emptyList();
			e.printStackTrace();
		}
		log.debug("Leaving getExpenses(from,to,user): {}", expList);
		return expList;
	}

	public List<Number> getAvailExpYears(User user) {
		log.debug("Entering getAvailExpYears(user={})", new Object[] { user });
		// String sql =
		// "select distinct year(expDate) as year from Expense where user.id=:uid order by year desc";
		// Query q = getSession().createQuery(sql);
		// q.setParameter("uid", user.getId());
		// @SuppressWarnings(value = "unchecked")
		// List<Object[]> years = (List<Object[]>) (q.list().size() > 0 ?
		// q.list()
		// : Collections.emptyList());
		List<Number> years = null;
		try {

			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<Number> cr = cb.createQuery(Number.class);
			Root<Expense> root = cr.from(Expense.class);
			Expression<Number> yearExp=getDatePart(cb, root.get(Expense_.expDate), DatePart.YEAR);
			cr.select(yearExp).distinct(true);
			Predicate idRes = cb.equal(root.get(Expense_.userExpense).get(UserExpense_.user).get(User_.id),user.getId());
			cr.where(idRes);
			cr.orderBy(cb.desc(yearExp));
			TypedQuery<Number> query = em.createQuery(cr);
			years=query.getResultList();
			//years=new ArrayList<Integer>(new TreeSet<Integer>(years));
		} catch (Exception e) {
			years = Collections.emptyList();
		}
		log.debug("Leaving getAvailExpYears(user): {}", years);
		return years;
	}

	public String getYearRange(User user) {
		log.debug("Entering getYearRange(user={})", new Object[] { user });
		// String sql =
		// "select min(year(expDate))||':'||max(year(expDate)) from Expense where user.id=:uid";
		// Query q = getSession().createQuery(sql);
		// q.setParameter("uid", user.getId());
		// Object data = q.uniqueResult();
		String data = null;
		try {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<Tuple> cr = cb.createTupleQuery();
			Root<Expense> root = cr.from(Expense.class);
			Expression<Number> yearExp=getDatePart(cb, root.get(Expense_.expDate), DatePart.YEAR);
			Expression<Number> minYearExp=cb.min(yearExp);
			Expression<Number> maxYearExp=cb.max(yearExp);
			cr.multiselect(minYearExp,maxYearExp);
			Predicate idRes = cb.equal(root.get(Expense_.userExpense).get(UserExpense_.user).get(User_.id),user.getId());
			cr.where(idRes);
			TypedQuery<Tuple> query = em.createQuery(cr);
			Tuple t = query.getSingleResult();
			
			data = t.get(0) + ":" + t.get(1);
		} catch (Exception e) {
			e.printStackTrace();
			data = "";
		}
		log.debug("Leaving getYearRange(user): {}", data);
		return data;
	}

}
