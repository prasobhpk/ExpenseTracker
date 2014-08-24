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

import com.pk.et.exp.model.ExpenseType;
import com.pk.et.exp.rest.resource.IExpenseTypeResource;
import com.pk.et.exp.service.IUserExpenseService;
import com.pk.et.infra.model.User;
import com.pk.et.infra.util.JQResponse;
import com.pk.et.infra.util.JSONRequest;
@Service("expenseTypeResource")
public class ExpenseTypeResource implements IExpenseTypeResource {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired(required = true)
	@Qualifier("userExpenseService")
	private IUserExpenseService userExpenseService;
	
	@Context 
	private MessageContext context;

	public JQResponse<ExpenseType> getExpenseTypes(boolean search,
			Integer page, Integer rows, String sidx, String sord) {
		
		HttpSession session=context.getHttpServletRequest().getSession();
		User user=(User)session.getAttribute("SESSION_USER");
		
		JSONRequest jsonRequest = new JSONRequest();
		jsonRequest.setPage(page);
		jsonRequest.setRows(rows);
		jsonRequest.setSortField(sidx);
		jsonRequest.setSortOrder(sord);
		return userExpenseService.getExpenseTypes(jsonRequest,user);
	}

	public Response editExpenseType(String action, long id, String type,
			String desc, boolean flag) {
		
		HttpSession session=context.getHttpServletRequest().getSession();
		User user=(User)session.getAttribute("SESSION_USER");
		
		ExpenseType expType = new ExpenseType();
		expType.setId(id);
		if ("edit".equals(action)) {
			expType.setType(type);
			expType.setDescription(desc);
			expType.setShowInDashboard(flag);
			userExpenseService.updateExpenseType(expType,user);
		} else if ("del".equals(action)) {
			userExpenseService.deleteExpenseType(expType.getId());
		}
		return Response.ok("success").build();
	}
	
	public List<ExpenseType> getExpeseTypesByUser(){
		HttpSession session=context.getHttpServletRequest().getSession();
		User user=(User)session.getAttribute("SESSION_USER");
		return userExpenseService.getExpenseTypes(user);
	}

}
