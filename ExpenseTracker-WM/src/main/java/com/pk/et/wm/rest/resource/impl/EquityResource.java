package com.pk.et.wm.rest.resource.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pk.et.infra.util.JsonResponse;
import com.pk.et.wm.model.Equity;
import com.pk.et.wm.rest.resource.IEquityResource;
import com.pk.et.wm.service.IEquityService;

@Service("equityResource")
public class EquityResource implements IEquityResource{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	@Qualifier("equityService")
	private IEquityService equityService;
	
	public JsonResponse<Equity> searchStocks(String stockName) {
		JsonResponse<Equity> response=new JsonResponse<Equity>(equityService.search(stockName));
		return response;
	}

}
