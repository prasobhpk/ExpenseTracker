package com.pk.et.exp.service;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.exp.model.Forecast;
import com.pk.et.infra.model.User;

public interface IForecastService {

	@Transactional
	Forecast saveForecast(Forecast forecast, User user);

	@Transactional
	Forecast updateForecast(Forecast forecast, User user);
}
