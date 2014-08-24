package com.pk.et.exp.web.controllers;

import static com.pk.et.infra.util.ETConstants.ACTION_MSG_KEY;
import static com.pk.et.infra.util.ETConstants.ERROR;
import static com.pk.et.infra.util.ETConstants.Exception_MSG_KEY;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pk.et.exp.model.Forecast;
import com.pk.et.exp.model.ForecastType;
import com.pk.et.exp.service.IForecastService;
import com.pk.et.exp.service.IUserExpenseService;
import com.pk.et.infra.model.Period;
import com.pk.et.infra.model.User;
import com.pk.et.infra.service.IUserContextService;
import com.pk.et.infra.util.ETConstants;
import com.pk.et.infra.util.Message;

@Controller
@RequestMapping("/exp/forecast")
public class ForecastContoller {

	@Autowired(required = true)
	@Qualifier("userContextService")
	private IUserContextService userContextService;

	@Autowired(required = true)
	@Qualifier("userExpenseService")
	private IUserExpenseService userExpenseService;

	@Autowired(required = true)
	@Qualifier("forecastService")
	private IForecastService forecastService;

	private static final String REDIRECT_VIEW = "forecast";

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showForecastView() {
		final ModelAndView mv = new ModelAndView("forecastExpense");
		mv.addObject("forecast", new Forecast());
		mv.addObject("periods", Period.values());
		mv.addObject("forecastTypes", ForecastType.values());
		mv.addObject(ETConstants.CURRENT_VIEW, "exp/forecast");
		return mv;
	}

	@RequestMapping(value = "/{forecastId}", method = RequestMethod.GET)
	public ModelAndView showForecastViewForUpdate(
			@PathVariable("forecastId") final Long forecastId) {
		final User user = this.userContextService.getCurrentUser();
		final ModelAndView mv = new ModelAndView("forecastExpense");
		mv.addObject("forecast",
				this.userExpenseService.findForecast(user, forecastId));
		mv.addObject("periods", Period.values());
		mv.addObject("forecastTypes", ForecastType.values());
		mv.addObject(ETConstants.CURRENT_VIEW, "exp/forecast");
		return mv;
	}

	@RequestMapping(value = "/{forecastId}", method = RequestMethod.POST)
	public ModelAndView updateForecast(final HttpSession session,
			@ModelAttribute("forecast") final Forecast forecast,
			final BindingResult result) {
		final ModelAndView mv = new ModelAndView();
		final Message message = new Message();
		final User user = this.userContextService.getCurrentUser();
		try {
			this.forecastService.updateForecast(forecast, user);
			mv.setView(new RedirectView("status"));
			message.setMsg("Forecast has been updated successfully....");
			session.setAttribute(ACTION_MSG_KEY, message);
		} catch (final Exception e) {
			message.setMsg("Could not update Forecast...");
			message.setDetails(e.getMessage());
			message.setStatus(ERROR);
			session.setAttribute(Exception_MSG_KEY, message);
			mv.setView(new RedirectView("" + forecast.getId()));
		}
		return mv;

	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addForecast(final HttpSession session,
			@ModelAttribute("forecast") final Forecast forecast,
			final BindingResult result) {
		final ModelAndView mv = new ModelAndView();
		final Message message = new Message();
		final User user = this.userContextService.getCurrentUser();
		if (this.userExpenseService.addForecastExpenses(forecast, user)) {
			// refresh the forecasts in session
			// session.setAttribute(USER_FORECAST_KEY, this.userExpenseService
			// .getAllForcastsByUserForMonth(user, LocalDate.now(),
			// ForecastType.getf));
			message.setMsg("Forecast has been saved successfully....");
		} else {
			message.setMsg("Could not save Forecast...");
		}

		mv.setView(new RedirectView(REDIRECT_VIEW));
		session.setAttribute("actionMSG", message);
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/status")
	public ModelAndView getMonthlyForecasts(
			@RequestParam(value = "month", required = false) @DateTimeFormat(iso = ISO.DATE) final Date month) {
		final ModelAndView mv = new ModelAndView("financialStatus");
		final User user = this.userContextService.getCurrentUser();
		final LocalDate date = (month == null ? LocalDate.now() : LocalDate
				.fromDateFields(month));
		final List<Forecast> incomeForecasts = this.userExpenseService
				.getAllForcastsByUserForMonth(user, date, ForecastType.INCOME);
		final List<Forecast> expenseForecasts = this.userExpenseService
				.getAllForcastsByUserForMonth(user, date, ForecastType.EXPENSE);
		mv.addObject("incomeForecasts", incomeForecasts);
		mv.addObject("expenseForecasts", expenseForecasts);
		mv.addObject(ETConstants.CURRENT_VIEW, "exp/forecast/status");
		return mv;
	}
}
