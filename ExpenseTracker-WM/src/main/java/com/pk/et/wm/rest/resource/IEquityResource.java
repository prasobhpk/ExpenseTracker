package com.pk.et.wm.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.pk.et.infra.util.JsonResponse;
import com.pk.et.wm.model.Equity;

@Path("stocks")
public interface IEquityResource {

	@GET
	@Path("/search")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	JsonResponse<Equity> searchStocks(@QueryParam("cmpName") String stockName);
}
