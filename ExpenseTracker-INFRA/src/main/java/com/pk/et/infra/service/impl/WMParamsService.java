package com.pk.et.infra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pk.et.infra.dao.WMParamsDAO;
import com.pk.et.infra.model.WMParams;
import com.pk.et.infra.service.IWMParamsService;

@Service("paramsService")
public class WMParamsService implements IWMParamsService {
	@Autowired(required = true)
	// @Qualifier("wmParamsDAO")
	private WMParamsDAO wmParamsDAO;

	public WMParams getParam() {
		if (exists()) {
			return this.wmParamsDAO.findAll().get(0);
		} else {
			return null;
		}
	}

	public boolean exists() {
		return this.wmParamsDAO.count() > 0;
	}

	public WMParams save(final WMParams params) {
		return this.wmParamsDAO.save(params);
	}

}
