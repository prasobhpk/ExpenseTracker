package com.pk.et.exp.dao.custom;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDate;

import com.pk.et.exp.model.Forecast;
import com.pk.et.exp.model.ForecastType;
import com.pk.et.infra.model.User;

public interface IForecastDAO {
	List<Forecast> getAllForcastsByUserForMonth(User user,
			LocalDate month, ForecastType forecastType);

	BigDecimal findForecastAmountForMonth(User user, LocalDate month, ForecastType forecastType);
}
