package com.pk.et.web.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pk.et.exp.service.IUserExpenseService;
import com.pk.et.infra.model.Configuration;
import com.pk.et.infra.model.Roles;
import com.pk.et.infra.model.User;
import com.pk.et.infra.model.UserAuthority;
import com.pk.et.infra.service.IConfigurationService;
import com.pk.et.infra.service.IUserContextService;
import com.pk.et.infra.util.ETConstants;
import com.pk.et.infra.util.Message;

@Controller
public class LoginController {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	@Qualifier("userContextService")
	private IUserContextService userContextService;

	@Autowired(required = true)
	@Qualifier("configurationService")
	private IConfigurationService configurationService;

	@Autowired(required = true)
	@Qualifier("userExpenseService")
	private IUserExpenseService userExpenseService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(final HttpSession session) {
		final ModelAndView mv = new ModelAndView();
		String redirectUrl = "";
		final User user = this.userContextService.getCurrentUser();
		final UserAuthority adminAuth = new UserAuthority();
		adminAuth.setRole(Roles.ROLE_ADMIN);
		final UserAuthority userAuth = new UserAuthority();
		userAuth.setRole(Roles.ROLE_USER);
		if ((user.getAuthorities().contains(adminAuth) && user.getAuthorities()
				.contains(userAuth))
				|| user.getAuthorities().contains(userAuth)) {
			@SuppressWarnings("unchecked")
			final Map<String, String> conf = (Map<String, String>) session
					.getAttribute(ETConstants.USER_CONFIG_KEY);
			if (conf.containsKey("HOME_VIEW")
					&& (conf.get("HOME_VIEW") != null)) {
				redirectUrl = conf.get("HOME_VIEW");
			} else {
				redirectUrl = "exp/expenseDashBoard";
			}
			// set the forecast in session
			// session.setAttribute(USER_FORECAST_KEY, this.userExpenseService
			// .getAllForcastsByUserForMonth(user, LocalDate.now(),
			// orecastType));
		} else if (user.getAuthorities().contains(adminAuth)) {
			redirectUrl = "admin/proxysettings";
		}
		mv.setView(new RedirectView(redirectUrl));
		return mv;
	}

	@RequestMapping(value = "/keepAlive", method = RequestMethod.GET)
	public @ResponseBody
	Message keepAlive(final HttpServletRequest req) {
		final Message message = new Message();
		message.setStatus("OK");
		return message;
	}

	@RequestMapping(value = "/setHome", method = RequestMethod.GET)
	public @ResponseBody
	Message setHomePage(final HttpSession session,
			@RequestParam("viewName") final String viewName) {
		final User user = this.userContextService.getCurrentUser();
		final Configuration config = this.configurationService.updateConfig(
				"HOME_VIEW", viewName, user);
		@SuppressWarnings("unchecked")
		final Map<String, String> conf = (Map<String, String>) session
				.getAttribute(ETConstants.USER_CONFIG_KEY);
		if (conf != null) {
			conf.put("HOME_VIEW", config.getConfigItemValue());
		}
		final Message message = new Message();
		message.setStatus(ETConstants.SUCCESS);
		return message;
	}

}
