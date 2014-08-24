package com.pk.et.wm.rest.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.pk.et.wm.model.Holding;

@Path("portfolio")
public interface IPortfolioResource {
	@GET
	@Path("/folioSummaryDBData")
	Response getFolioHoldingsDataForDB(@QueryParam("folioId") Long folioId);
	
	
	@GET
	@Path("/folioPerformanceDBData")
	Response getFolioPerformanceForDB(@QueryParam("folioId") Long folioId);
	
	@GET
	@Path("/holdingDetails")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Holding getHolding(@QueryParam("folioId") Long folioId,@QueryParam("equityId") Long equityId);
}
