package com.pk.et.exp.rest.resource;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.pk.et.exp.model.Expense;
import com.pk.et.infra.util.JQResponse;

@Path("expenses")
public interface IExpenseResource {
	@GET
	@Produces(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/list")
	JQResponse<Expense> getExpenses(
			@QueryParam("_search") boolean search,
			@QueryParam("page") Integer page,
			@QueryParam("rows") Integer rows,
			@QueryParam("sidx") String sidx, 
			@QueryParam("sord") String sord
			);
	@POST
	@Produces(value = { MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("/edit")
	Response editExpense(
			@FormParam("oper") String action,
			@FormParam("id") long expId,
			@FormParam("expense") Integer amount,
			@FormParam("description") String desc,
			@FormParam("active") boolean active
			);
	
	@GET
	@Path("/db/forTheYear")
	Response getExpensesFor(
			@QueryParam("year") int year
			);
	
	@GET
	@Path("/db/years")
	Response getExpenseYears();
}
