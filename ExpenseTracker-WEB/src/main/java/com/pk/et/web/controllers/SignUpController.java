package com.pk.et.web.controllers;

import java.io.File;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pk.et.infra.model.Roles;
import com.pk.et.infra.model.User;
import com.pk.et.infra.service.IAuthorityService;
import com.pk.et.infra.service.IConfigurationService;
import com.pk.et.infra.util.ETConstants;
import com.pk.et.service.ISignUpService;

@Controller
@RequestMapping(value = "/regForm")
public class SignUpController {

	@Autowired
	private ISignUpService signUpService;

	@Autowired(required = true)
	@Qualifier("securityManager")
	private com.pk.et.infra.security.SecurityManager securityManager;

	@Autowired(required = true)
	@Qualifier("authorityService")
	private IAuthorityService authorityService;
	
	@Autowired(required = true)
	@Qualifier("configurationService")
	private IConfigurationService configurationService;

	@ModelAttribute("regUser")
	public User populateExpense(
			@RequestParam(required = false, value = "userId") Long id) {
		if (id != null) {
			// load from db..
			return null;
		}
		return new User();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView regForm() {
		ModelAndView mv = new ModelAndView("regForm");
		// mv.addObject("user", new User());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView createUser(HttpServletRequest request,
			@ModelAttribute("regUser") @Valid User user, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		System.out.println(user);

		// if has validation errors then return to the form
		if (result.hasErrors()) {
			System.out.println(result.getFieldErrors());
			System.out.println(">>>HAS VALIDATION ERRORS<<<<<<<");
			mv.setViewName("regForm");
		} else {
			// else create the user
			System.out.println(">>>NO VALIDATION ERRORS<<<<<<<");
			user.setSalt(securityManager.getRandomSalt());
			user.getRoles().add(authorityService.getAuthority(Roles.ROLE_USER));
			// user.setPassword(securityManager.asyEncrypt(user.getPassword()));
			user.setPassword(securityManager.encodePassword(user.getPassword(),
					null));
			try {
				// Create User context
				if (signUpService.signUp(user)) {
					//Set the default home page
					
					configurationService.updateConfig("HOME_VIEW", "exp/expenseDashBoard", user);
					
					// Save the file
					String ctxPath = request.getSession().getServletContext()
							.getRealPath("/");
					File rootFolder = new File(ctxPath
							+ ETConstants.UPLOAD_ROOT);
					if (rootFolder.exists() && rootFolder.isDirectory()) {
						File userFolder = new File(rootFolder,
								user.getUsername());
						userFolder.mkdir();
					} else {
						rootFolder.mkdir();
						File userFolder = new File(rootFolder,
								user.getUsername());
						userFolder.mkdir();
					}
					mv.setView(new RedirectView("login"));
				}
			} catch (PersistenceException e) {
				mv.setViewName("regForm");
				System.out.println("User already exists....");
			} catch (Exception e) {
				mv.setViewName("regForm");
				System.out.println(e.getClass() + "::" + e.getMessage());
			}
		}
		return mv;
	}

}
