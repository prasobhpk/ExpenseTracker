package com.pk.et.exp.rest.resource.impl;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pk.et.exp.model.Expense;
import com.pk.et.exp.rest.resource.IExpenseResource;
import com.pk.et.exp.service.IUserExpenseService;
import com.pk.et.exp.util.Util;
import com.pk.et.infra.model.User;
import com.pk.et.infra.util.JQResponse;
import com.pk.et.infra.util.JSONRequest;

@Service("expenseResource")
public class ExpenseResource implements IExpenseResource {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	@Qualifier("userExpenseService")
	private IUserExpenseService userExpenseService;

	@Context
	private MessageContext context;

	public JQResponse<Expense> getExpenses(final boolean search,
			final Integer page, final Integer rows, final String sidx,
			final String sord) {
		final HttpSession session = this.context.getHttpServletRequest()
				.getSession();
		final User user = (User) session.getAttribute("SESSION_USER");
		final JSONRequest jsonRequest = new JSONRequest();
		jsonRequest.setPage(page);
		jsonRequest.setRows(rows);
		jsonRequest.setSortField(sidx);
		jsonRequest.setSortOrder(sord);

		if (search) {
			jsonRequest.setSearch(search);
			jsonRequest.setSearchFilter(Util.getSearchFilter(this.context
					.getHttpServletRequest().getParameter("filters")));
		}
		return this.userExpenseService.getExpenses(jsonRequest, user);
	}

	public Response editExpense(final String action, final long expId,
			final Integer amount, final String desc, final boolean active) {
		final Expense exp = new Expense();
		exp.setId(expId);
		if ("edit".equals(action)) {
			exp.setExpense(amount);
			exp.setDescription(desc);
			exp.setActive(active);
			this.userExpenseService.updateExpense(exp);
		} else if ("del".equals(action)) {
			this.userExpenseService.deleteExpense(exp.getId());
		}
		return Response.ok("success").build();
	}

	public Response getExpensesFor(final int year) {
		final HttpSession session = this.context.getHttpServletRequest()
				.getSession();
		final User user = (User) session.getAttribute("SESSION_USER");
		return Response.ok(this.userExpenseService.getExpenses(year, user))
				.build();
	}

	public Response getExpenseYears() {
		final HttpSession session = this.context.getHttpServletRequest()
				.getSession();
		final User user = (User) session.getAttribute("SESSION_USER");
		final StringBuilder sb = new StringBuilder("<data>");
		final List<Number> years = this.userExpenseService.getYears(user);
		sb.append("<variable name=\"YEARS\">");
		for (final Number data : years) {
			sb.append("<row><column>" + data + "</column></row>");
		}
		sb.append("</variable>");
		sb.append("</data>");
		this.logger.debug("Expense years data for {0} is {1}", new Object[] {
				user, sb.toString() });
		return Response.ok(sb.toString()).build();
	}

}
