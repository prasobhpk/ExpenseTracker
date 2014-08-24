package com.pk.et.exp.rest.resource;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.pk.et.exp.model.ExpenseType;
import com.pk.et.infra.util.JQResponse;

@Path("expenseTypes")
public interface IExpenseTypeResource {

	@GET
	@Produces(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/list")
	JQResponse<ExpenseType> getExpenseTypes(
			@QueryParam("_search") boolean search,
			@QueryParam("page") Integer page,
			@QueryParam("rows") Integer rows,
			@QueryParam("sidx") String sidx, 
			@QueryParam("sord") String sord
			);

	@POST
	@Produces(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/edit")
	Response editExpenseType(
			@FormParam("oper") String action,
			@FormParam("id") long id,
			@FormParam("type") String type,
			@FormParam("description") String desc,
			@FormParam("showInDashboard") boolean flag);
	
	@GET
	@Produces(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/all")
	List<ExpenseType> getExpeseTypesByUser();
}
