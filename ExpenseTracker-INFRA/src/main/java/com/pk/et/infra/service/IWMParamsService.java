package com.pk.et.infra.service;

import org.springframework.transaction.annotation.Transactional;

import com.pk.et.infra.model.WMParams;

public interface IWMParamsService {
	@Transactional(readOnly = true)
	WMParams getParam();

	@Transactional(readOnly = true)
	boolean exists();

	@Transactional()
	WMParams save(WMParams params);
}
