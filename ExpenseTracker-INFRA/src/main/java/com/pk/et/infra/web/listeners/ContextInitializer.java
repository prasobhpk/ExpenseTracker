package com.pk.et.infra.web.listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pk.et.infra.model.WMParams;
import com.pk.et.infra.service.IWMParamsService;

public class ContextInitializer implements ServletContextListener {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired(required = true)
	@Qualifier("paramsService")
	private IWMParamsService paramsService;

	public void contextInitialized(final ServletContextEvent evt) {
		this.log.debug("===========Initializing context........================");
		final ServletContext context = evt.getServletContext();
		/*
		 * if (paramsService.exists()) { WMParams params =
		 * paramsService.getParam(); context.setAttribute("wmParams", params);
		 * log.debug("setting wmparams in context:: {}", params); }
		 */
		final WMParams params = new WMParams(2L, 2L, 2L, 2L, 2L);
		context.setAttribute("wmParams", params);
		this.log.debug("setting wmparams in context:: {}", params);

	}

	public void contextDestroyed(final ServletContextEvent evt) {
		this.log.info("destroying context.......");
	}

}
