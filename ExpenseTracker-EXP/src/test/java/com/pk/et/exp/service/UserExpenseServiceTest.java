package com.pk.et.exp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pk.et.exp.model.Expense;
import com.pk.et.exp.model.Forecast;
import com.pk.et.exp.model.ForecastType;
import com.pk.et.infra.model.Period;
import com.pk.et.infra.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/spring/ExpenseTracker-EXP-context-test.xml" })
public class UserExpenseServiceTest extends BaseExpTest {
	private LocalDate date=LocalDate.now().withYear(2013).withMonthOfYear(3);

	@Autowired
	private IUserExpenseService userExpenseService;

	@Before
	public void init() {
		this.user = createUser("prasobhpk");
		initExpeseContextForUser(this.user);
		createExpenses();
		initTestDataForForecastTests();
	}

	@Test
	public void testGetYears() {
		final User u = this.user;
		assertEquals(3, this.userExpenseService.getYears(u).size());
	}

	@Test
	public void shouldBeAbleToCreateForecasts() {
		final Forecast forecastExpense = buildForecast("Rent",
				BigDecimal.valueOf(8500), LocalDate.now().withDayOfMonth(7),
				true, Period.MONTHLY, ForecastType.EXPENSE);
		final Forecast forecastIncome = buildForecast("Salary",
				BigDecimal.valueOf(55000), LocalDate.now().withDayOfMonth(1),
				true, Period.MONTHLY, ForecastType.INCOME);
		assertTrue(this.userExpenseService.addForecastExpenses(forecastExpense,
				this.user));
		assertTrue(this.userExpenseService.addForecastExpenses(forecastIncome,
				this.user));
	}

	@Test
	public void shouldReturnAllMonthlyExpenseForcasts() {
		// GIVEN

		// WHEN
		final List<Forecast> forecasts = this.userExpenseService
				.getAllForcastsByUserForMonth(this.user, LocalDate.now()
						.withYear(2013).withMonthOfYear(3).withDayOfMonth(17),
						ForecastType.EXPENSE);

		// THEN
		assertEquals(4, forecasts.size());
	}

	@Test
	public void shouldReturnTotalExpenseForecastAmountForAMonth() {
		// GIVEN

		// WHEN
		final BigDecimal forecastAmountForMarch = this.userExpenseService
				.findForecastAmountForMonth(this.user, LocalDate.now()
						.withYear(2013).withMonthOfYear(3).withDayOfMonth(17),
						ForecastType.EXPENSE);

		// THEN
		assertTrue(BigDecimal.valueOf(17000).compareTo(forecastAmountForMarch) == 0);
	}

	@Test
	public void shouldReturnAllMonthlyIncomeForcasts() {
		// GIVEN

		// WHEN
		final List<Forecast> forecasts = this.userExpenseService
				.getAllForcastsByUserForMonth(this.user, date,
						ForecastType.INCOME);

		// THEN
		assertEquals(2, forecasts.size());
	}

	@Test
	public void shouldReturnTotalIncomeForecastAmountForAMonth() {
		// GIVEN

		// WHEN
		final BigDecimal forecastAmountForMarch = this.userExpenseService
				.findForecastAmountForMonth(this.user, LocalDate.now()
						.withYear(2013).withMonthOfYear(3).withDayOfMonth(17),
						ForecastType.INCOME);

		// THEN
		assertTrue(BigDecimal.valueOf(56000).compareTo(forecastAmountForMarch) == 0);
	}

	@Transactional
	private void createExpenses() {
		final Expense expense1 = new Expense(LocalDate.now().minusYears(2)
				.toDate(), 100);
		final Expense expense2 = new Expense(LocalDate.now().minusYears(1)
				.toDate(), 101);
		final Expense expense3 = new Expense(LocalDate.now().toDate(), 102);
		final Expense expense4 = new Expense(LocalDate.now().toDate(), 103);

		final List<Expense> expenses = new ArrayList<Expense>();
		expenses.add(expense1);
		expenses.add(expense2);
		expenses.add(expense3);
		expenses.add(expense4);
		this.userExpenseService.addExpenses(expenses, this.user);

	}

	@Transactional
	private void initTestDataForForecastTests() {
		final Forecast forecast1 = buildForecast("Rent",
				BigDecimal.valueOf(8500), date.withDayOfMonth(7),
				true, Period.MONTHLY, ForecastType.EXPENSE);
		final Forecast forecast2 = buildForecast("Internet",
				BigDecimal.valueOf(1250), date.withDayOfMonth(12),
				true, Period.MONTHLY, ForecastType.EXPENSE);
		final Forecast forecast3 = buildForecast("Cable",
				BigDecimal.valueOf(250), date.withDayOfMonth(15),
				true, Period.MONTHLY, ForecastType.EXPENSE);
		final Forecast forecast4 = buildForecast("LIC",
				BigDecimal.valueOf(7000), date.withDayOfMonth(10),
				false, Period.NA, ForecastType.EXPENSE);

		final Forecast forecastIncome1 = buildForecast("Salary",
				BigDecimal.valueOf(55000), date.withDayOfMonth(1),
				true, Period.MONTHLY, ForecastType.INCOME);
		final Forecast forecastIncome2 = buildForecast("Stock",
				BigDecimal.valueOf(1000), date.withDayOfMonth(7),
				true, Period.NA, ForecastType.INCOME);
		final Forecast forecastIncome3 = buildForecast("Future",
				BigDecimal.valueOf(500), date.plusMonths(1), false,
				Period.NA, ForecastType.INCOME);

		this.userExpenseService.addForecastExpenses(forecast1, this.user);
		this.userExpenseService.addForecastExpenses(forecast2, this.user);
		this.userExpenseService.addForecastExpenses(forecast3, this.user);
		this.userExpenseService.addForecastExpenses(forecast4, this.user);

		this.userExpenseService.addForecastExpenses(forecastIncome1, this.user);
		this.userExpenseService.addForecastExpenses(forecastIncome2, this.user);
		this.userExpenseService.addForecastExpenses(forecastIncome3, this.user);

	}

	private Forecast buildForecast(final String title, final BigDecimal amount,
			final LocalDate date, final boolean periodic, final Period period,
			final ForecastType forecastType) {
		final Forecast forecast = new Forecast();
		forecast.setTitle(title);
		forecast.setForecastAmount(amount);
		forecast.setDate(date.toDate());
		if (periodic) {
			forecast.setPeriod(period);
			forecast.setPeriodic(true);
		}
		forecast.setForecastType(forecastType);
		return forecast;
	}

}
