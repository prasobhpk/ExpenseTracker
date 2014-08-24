package com.pk.et.exp.dao.custom.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;

import com.pk.et.exp.dao.custom.IForecastDAO;
import com.pk.et.exp.model.Forecast;
import com.pk.et.exp.model.ForecastType;
import com.pk.et.exp.model.Forecast_;
import com.pk.et.exp.model.UserExpense_;
import com.pk.et.infra.dao.custom.impl.GenericDAO;
import com.pk.et.infra.model.Period;
import com.pk.et.infra.model.User;
import com.pk.et.infra.model.User_;
import com.pk.et.infra.util.DatePart;

public class ForecastDAOImpl extends GenericDAO implements IForecastDAO {

	public List<Forecast> getAllForcastsByUserForMonth(final User user,
			final LocalDate month, final ForecastType forecastType) {
		this.log.debug(
				"Find all the Forecast for the month of :{}  for user :{}",
				new Object[] { month.getMonthOfYear(), user });
		List<Forecast> forecasts = null;
		try {
			final CriteriaBuilder cb = getCriteriaBuilder();
			final CriteriaQuery<Forecast> cr = cb.createQuery(Forecast.class);
			final Root<Forecast> root = cr.from(Forecast.class);
			cr.select(root);
			final Predicate predicateForMonthlyForeCast = getPredicateForMonthlyForeCast(
					user, month, forecastType, root, cb);
			cr.where(predicateForMonthlyForeCast);
			final TypedQuery<Forecast> query = this.em.createQuery(cr);
			forecasts = query.getResultList();
		} catch (final PersistenceException e) {
			forecasts = Collections.emptyList();
			this.log.debug(
					"Error while getting forecasts for user :{} ==>Message :{}",
					new Object[] { user, e.getMessage() });
		}
		this.log.debug("Leaving getAllForcastsByUserForMonth(user,month): {}",
				forecasts);
		return forecasts;
	}

	public BigDecimal findForecastAmountForMonth(final User user,
			final LocalDate month, final ForecastType forecastType) {
		this.log.debug(
				"Find total Forecast amount for the month of :{}  for user :{}",
				new Object[] { month.getMonthOfYear(), user });
		try {
			final CriteriaBuilder cb = getCriteriaBuilder();
			final CriteriaQuery<BigDecimal> cr = cb
					.createQuery(BigDecimal.class);
			final Root<Forecast> root = cr.from(Forecast.class);
			final Predicate predicateForMonthlyForeCast = getPredicateForMonthlyForeCast(
					user, month, forecastType, root, cb);
			cr.select(cb.sum(root.get(Forecast_.forecastAmount)));
			cr.where(predicateForMonthlyForeCast);
			final TypedQuery<BigDecimal> query = this.em.createQuery(cr);
			return query.getSingleResult();
		} catch (final PersistenceException e) {
			this.log.debug(
					"Error while getting total forecast amount for user :{} ==>Message :{}",
					new Object[] { user, e.getMessage() });
			return BigDecimal.ZERO;
		}
	}

	private Predicate getPredicateForMonthlyForeCast(final User user,
			final LocalDate month, final ForecastType forecastType,
			final Root<Forecast> root, final CriteriaBuilder cb) {
		final Predicate idRes = cb.equal(
				root.get(Forecast_.userExpense).get(UserExpense_.user)
						.get(User_.id), user.getId());
		final Predicate monthRes = cb.equal(
				getDatePart(cb, root.get(Forecast_.date), DatePart.MONTH),
				month.getMonthOfYear());
		final Predicate forecastTypeRes = cb.equal(
				root.get(Forecast_.forecastType), forecastType);
		final Predicate periodRes = cb.and(
				cb.equal(root.get(Forecast_.periodic), Boolean.TRUE),
				cb.equal(root.get(Forecast_.period), Period.MONTHLY));

		return cb.and(idRes, forecastTypeRes, cb.or(monthRes, periodRes));
	}
}
