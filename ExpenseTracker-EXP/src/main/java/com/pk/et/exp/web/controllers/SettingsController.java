package com.pk.et.exp.web.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pk.et.exp.model.ExpenseType;
import com.pk.et.exp.service.IUserExpenseService;
import com.pk.et.infra.model.User;
import com.pk.et.infra.service.IConfigurationService;
import com.pk.et.infra.service.IUserContextService;
import com.pk.et.infra.util.ETConstants;
import com.pk.et.infra.util.JQResponse;
import com.pk.et.infra.util.JSONRequest;
import com.pk.et.infra.util.Message;

@Controller
@RequestMapping("/settings")
public class SettingsController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired(required = true)
	@Qualifier("userContextService")
	private IUserContextService userContextService;

	@Autowired(required = true)
	@Qualifier("userExpenseService")
	private IUserExpenseService userExpenseService;

	@Autowired(required = true)
	@Qualifier("configurationService")
	private IConfigurationService configurationService;

	@ModelAttribute("type")
	public ExpenseType populateExpense(
			@RequestParam(required = false, value = "typeId") final Long id) {
		if (id != null) {
			// load from db..
			return null;
		}
		return new ExpenseType();
	}

	@RequestMapping(value = "/expenseType", method = RequestMethod.GET)
	public ModelAndView expenseForm() {
		final ModelAndView mv = new ModelAndView("settings");
		mv.addObject(ETConstants.CURRENT_VIEW, "settings/expenseType");
		return mv;
	}

	@RequestMapping(value = "/expenseType", method = RequestMethod.POST)
	public ModelAndView addExpenseType(
			@ModelAttribute("type") @Valid final ExpenseType expenseType,
			final BindingResult result, final HttpServletRequest request) {
		final ModelAndView mv = new ModelAndView();
		// if has validation errors then return to the form
		if (result.hasErrors()) {
			mv.setViewName("settings");
		} else {
			// else save the expense
			this.userExpenseService.addExpenseType(expenseType,
					this.userContextService.getCurrentUser());
			mv.setView(new RedirectView("expenseType"));
		}
		return mv;
	}

	@RequestMapping(value = "/expenseType/list", method = RequestMethod.GET)
	public @ResponseBody
	JQResponse<ExpenseType> getExpenseTypes(final HttpServletRequest request,
			@RequestParam("_search") final boolean search,
			@RequestParam("page") final Integer page,
			@RequestParam("rows") final Integer rows,
			@RequestParam("sidx") final String sidx,
			@RequestParam("sord") final String sord) throws IOException {
		final JSONRequest jsonRequest = new JSONRequest();
		jsonRequest.setPage(page);
		jsonRequest.setRows(rows);
		jsonRequest.setSortField(sidx);
		jsonRequest.setSortOrder(sord);
		return this.userExpenseService.getExpenseTypes(jsonRequest,
				this.userContextService.getCurrentUser());
	}

	@RequestMapping(value = "/expenseType/edit", method = RequestMethod.POST)
	public String editExpenseType(
			@RequestParam(value = "oper") final String action,
			@RequestParam(value = "id") final long id,
			@RequestParam(value = "type", required = false) final String type,
			@RequestParam(value = "description", required = false) final String desc,
			@RequestParam(value = "showInDashboard", required = false) final boolean flag) {
		final ExpenseType expType = new ExpenseType();
		expType.setId(id);
		if ("edit".equals(action)) {
			expType.setType(type);
			expType.setDescription(desc);
			expType.setShowInDashboard(flag);
			this.userExpenseService.updateExpenseType(expType,
					this.userContextService.getCurrentUser());
		} else if ("del".equals(action)) {
			this.userExpenseService.deleteExpenseType(expType.getId());
		}
		return "success";
	}

	@RequestMapping(value = "/userAppSettings", method = RequestMethod.GET)
	public ModelAndView showAppSettingsForm() {
		final ModelAndView mv = new ModelAndView("userAppSettingsForm");
		final Map<String, String> config = this.configurationService
				.getUserConfigurations(this.userContextService.getCurrentUser());
		mv.addObject("config", config);
		mv.addObject(ETConstants.CURRENT_VIEW, "settings/userAppSettings");
		return mv;
	}

	@RequestMapping(value = "/updateuserAppSettings", method = RequestMethod.POST)
	public @ResponseBody
	Message updateUserAppSettings(
			@RequestParam("configKey") final String configKey,
			@RequestParam("configValue") final String configValue,
			final HttpSession session) {
		final Message message = new Message();
		try {
			final User user = this.userContextService.getCurrentUser();
			this.configurationService
					.updateConfig(configKey, configValue, user);
			final Map<String, String> conf = this.configurationService
					.getUserConfigurations(user);
			session.removeAttribute(ETConstants.USER_CONFIG_KEY);
			this.log.debug("Updating session with user configurations:: {}",
					conf);
			session.setAttribute(ETConstants.USER_CONFIG_KEY, conf);
			message.setStatus(ETConstants.SUCCESS);
		} catch (final Exception e) {
			message.setStatus(ETConstants.FAIL);
			message.setDetails(e.getMessage());
		}
		return message;
	}

	@RequestMapping(value = "/reportTest", method = RequestMethod.GET)
	public ModelAndView reportTestForm() {
		final ModelAndView mv = new ModelAndView("reportTestForm");
		final Map<String, String> config = this.configurationService
				.getUserConfigurations(this.userContextService.getCurrentUser());
		mv.addObject("config", config);
		mv.addObject(ETConstants.CURRENT_VIEW, "settings/reportTest");
		return mv;
	}

}
