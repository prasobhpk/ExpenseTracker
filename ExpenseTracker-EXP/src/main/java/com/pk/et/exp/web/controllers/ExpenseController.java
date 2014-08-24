package com.pk.et.exp.web.controllers;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pk.et.exp.model.Expense;
import com.pk.et.exp.model.ExpenseType;
import com.pk.et.exp.service.IUserExpenseService;
import com.pk.et.exp.util.POIExcelReader;
import com.pk.et.exp.web.command.ExpensesCommand;
import com.pk.et.exp.web.views.ExcelExpenseView;
import com.pk.et.infra.service.IUserContextService;
import com.pk.et.infra.util.DateUtil;
import com.pk.et.infra.util.ETConstants;
import com.pk.et.infra.util.Message;

@Controller
@RequestMapping("/exp")
// @SessionAttributes("expense")
public class ExpenseController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired(required = true)
	@Qualifier("userContextService")
	private IUserContextService userContextService;

	@Autowired(required = true)
	@Qualifier("userExpenseService")
	private IUserExpenseService userExpenseService;

	// @Autowired(required = true)
	// @Qualifier("feedService")
	// private IFeedService feedService;

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	@Qualifier("uploadJob")
	private Job job;

	@Autowired
	@Qualifier("itemReader")
	private FlatFileItemReader<Expense> itemReader;

	@ModelAttribute("expense")
	public Expense populateExpense(
			@RequestParam(required = false, value = "expId") final Long id) {
		if (id != null) {
			// load from db..
			return null;
		}
		return new Expense();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView expenseForm(final HttpServletRequest request) {
		final ModelAndView mv = new ModelAndView("expenseForm");
		final List<ExpenseType> list = this.userExpenseService
				.getExpenseTypes(this.userContextService.getCurrentUser());
		mv.addObject("expTypes", list);
		mv.addObject("expCommand", new ExpensesCommand());
		mv.addObject(ETConstants.CURRENT_VIEW, "exp");
		return mv;
	}

	@RequestMapping(value = "/expenseDashBoard", method = RequestMethod.GET)
	public ModelAndView expenseDashBoard(final HttpServletRequest request) {
		final ModelAndView mv = new ModelAndView("expenseHome");
		mv.addObject(ETConstants.CURRENT_VIEW, "exp/expenseDashBoard");
		return mv;
	}

	// @RequestMapping(method = RequestMethod.POST)
	// public ModelAndView addExpense(
	// @ModelAttribute("expense") @Valid Expense expense,
	// BindingResult result,@RequestParam("typeId") long typeId) {
	// ModelAndView mv = new ModelAndView();
	// // if has validation errors then return to the form
	// if (result.hasErrors()) {
	// mv.setViewName("expenseForm");
	// } else {
	// // else save the expense
	// userService.addExpense(expense,
	// userContextService.getCurrentUser(),typeId);
	// mv.setView(new RedirectView("expenses"));
	// }
	// return mv;
	// }
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView addExpense(final HttpSession session,
			@ModelAttribute("expCommand") final ExpensesCommand command,
			final BindingResult result) {
		final ModelAndView mv = new ModelAndView();
		final Message message = new Message();
		// if has validation errors then return to the form
		if (result.hasErrors()) {
			mv.setViewName("expenseForm");
			final List<ExpenseType> list = this.userExpenseService
					.getExpenseTypes(this.userContextService.getCurrentUser());
			mv.addObject("expTypes", list);
		} else {
			// else save the expense
			this.userExpenseService.addExpenses(command,
					this.userContextService.getCurrentUser());
			message.setMsg("Expenses have been saved successfully....");
			mv.setView(new RedirectView("exp"));
		}
		session.setAttribute("actionMSG", message);
		return mv;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ModelAndView updateExpense(
			@ModelAttribute("expense") @Valid final Expense expense,
			final BindingResult result, final HttpServletRequest request) {
		final ModelAndView mv = new ModelAndView();
		// if has validation errors then return to the form
		if (result.hasErrors()) {
			mv.setViewName("expenseForm");
		} else {
			// else update the expense
			this.userExpenseService.updateExpense(expense);
			mv.setView(new RedirectView("expenses/expenseDashBoard"));
		}
		return mv;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ModelAndView deleteExpense(@RequestParam("expId") final long id) {
		final ModelAndView mv = new ModelAndView();
		this.userExpenseService.deleteExpense(id);
		mv.setView(new RedirectView("expenses/expenseDashBoard"));
		return mv;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editExpense(
			@RequestParam("oper") final String action,
			@RequestParam("id") final long expId,
			@RequestParam(value = "expense", required = false) final Integer amount,
			@RequestParam(value = "description", required = false) final String desc,
			@RequestParam(value = "active", required = false) final boolean active) {
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
		return "success";
	}

	// File upload
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String fileUploadForm() {
		return "uploadForm";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody
	Message processUpload(@RequestParam("file") final MultipartFile file,
			final HttpServletRequest request, final Model model) {
		final POIExcelReader excelReader = new POIExcelReader();
		final Message message = new Message();
		List<Expense> expenses = null;
		try {
			expenses = excelReader.readExpnsesFromExcel(file.getInputStream());
			this.userExpenseService.addExpenses(expenses);
			message.setStatus("success");
			message.setMsg("Expenses have been uploaded successfully!");
		} catch (final Exception e) {
			message.setStatus("error");
			message.setMsg(e.getMessage());
			this.logger.error(e.getMessage());
		}
		return message;

	}

	/*
	 * @RequestMapping(value = "/uploadCSV", method = RequestMethod.POST) public
	 * 
	 * @ResponseBody Message processUploadCSV(@RequestParam("file")
	 * MultipartFile file,
	 * 
	 * @RequestParam("description") String description, HttpServletRequest
	 * request, Model model) { User user = userContextService.getCurrentUser();
	 * JobExecution execution = null; JobParametersBuilder paramsBuilder = new
	 * JobParametersBuilder(); String TS = new
	 * SimpleDateFormat("dd/MM/yyyy-HH:mm:ss").format(
	 * Calendar.getInstance().getTime()).toString();
	 * paramsBuilder.addString("TS", TS); Message m = new Message(); try { Long
	 * ts = System.currentTimeMillis(); String fullFileName =
	 * file.getOriginalFilename(); Feed feed = new Feed();
	 * feed.setFileName(file.getName()); feed.setDescription(description);
	 * feed.setExtension(fullFileName.substring(fullFileName.indexOf(".")));
	 * feed.setStoredName(ts + feed.getExtension()); feed.setTime(new Date());
	 * feed.setContentType(file.getContentType()); // Save the file String
	 * ctxPath = request.getSession().getServletContext() .getRealPath("/");
	 * File rootFolder = new File(ctxPath + ETConstants.UPLOAD_ROOT + "/" +
	 * userContextService.getCurrentUser().getUsername()); if
	 * (rootFolder.exists() && rootFolder.isDirectory()) { File tmp = new
	 * File(rootFolder, feed.getStoredName()); file.transferTo(tmp);
	 * feed.setPath(ETConstants.UPLOAD_ROOT + "/" + user.getUsername() + "/" +
	 * feed.getStoredName()); feedService.saveFeed(feed); } Resource r = new
	 * InputStreamResource(file.getInputStream()); itemReader.setResource(r);
	 * execution = jobLauncher.run(job, paramsBuilder.toJobParameters()); if
	 * (execution.getStatus() == BatchStatus.COMPLETED) {
	 * m.setMsg("JOB STATUS :" + execution.getStatus().toString() +
	 * "\nExpenses have been uploaded successfully !"); } else {
	 * m.setStatus("error"); m.setMsg("JOB STATUS :" +
	 * execution.getStatus().toString()); } } catch (Exception e) {
	 * m.setStatus("error"); m.setMsg(e.getMessage().replace('{',
	 * '(').replace('}', ')')); } return m;
	 * 
	 * }
	 */

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView getExportForm(final HttpServletRequest request,
			final HttpServletResponse response) {
		final ModelAndView mv = new ModelAndView("export");
		final String yearRange = this.userExpenseService
				.getYearRange(this.userContextService.getCurrentUser());
		this.logger.debug("Year Range >>>>>>" + yearRange);
		mv.addObject("yearRange", yearRange);
		mv.addObject("dateFormat", ETConstants.DATE_FORMAT);
		mv.addObject(ETConstants.CURRENT_VIEW, "exp/export");
		return mv;
	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public ModelAndView getExcelExpenses(final HttpServletRequest request,
			final HttpServletResponse response,
			@RequestParam("from") final String from,
			@RequestParam("to") final String to) {

		final Date fromDate = DateUtil.getDate(from, ETConstants.DATE_FORMAT);
		final Date toDate = DateUtil.getDate(to, ETConstants.DATE_FORMAT);
		List<Expense> expenses = null;
		try {
			expenses = this.userExpenseService.getExpenses(fromDate, toDate,
					this.userContextService.getCurrentUser());
		} catch (final Exception e) {
			expenses = Collections.emptyList();
			e.printStackTrace();
		}

		return new ModelAndView(new ExcelExpenseView(), "expenseData", expenses);

	}

}
