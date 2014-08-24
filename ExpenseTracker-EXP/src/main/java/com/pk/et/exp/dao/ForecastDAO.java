package com.pk.et.exp.dao;

import com.pk.et.exp.dao.custom.IForecastDAO;
import com.pk.et.exp.model.Forecast;
import com.pk.et.infra.repository.ETRepository;

public interface ForecastDAO extends
		ETRepository<Forecast, Long>, IForecastDAO {

}
