package com.pk.et.exp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.exp.dao.ForecastDAO;
import com.pk.et.exp.dao.UserExpenseDAO;
import com.pk.et.exp.model.Forecast;
import com.pk.et.exp.model.UserExpense;
import com.pk.et.exp.service.IForecastService;
import com.pk.et.infra.model.User;

@Service
public class ForecastService implements IForecastService {

	private static final Logger LOG = LoggerFactory
			.getLogger(ForecastService.class);

	@Autowired
	private ForecastDAO forecastDAO;

	@Autowired
	private UserExpenseDAO userExpenseDAO;

	public Forecast saveForecast(final Forecast forecast, final User user) {
		final UserExpense expenseContext = this.userExpenseDAO
				.findByUserId(user.getId());
		forecast.setUserExpense(expenseContext);
		return this.forecastDAO.save(forecast);
	}

	public Forecast updateForecast(final Forecast forecast, final User user) {
		final Forecast forecastToUpdate = this.forecastDAO.findOne(forecast
				.getId());
		BeanUtils.copyProperties(forecast, forecastToUpdate, new String[] {
				"id", "userExpense", "version" });
		return forecastToUpdate;

	}

}
