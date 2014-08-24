package com.pk.et.infra.web.listeners;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class LogConfigListener implements ServletContextListener {

	private static final String LOGBACK_FILE_SUFFIX = "-logback.xml";
	private static final String LOG_CONFIGURATION_DIR = "log.configurationDir";

	private final static Logger LOGGER = LoggerFactory
			.getLogger(LogConfigListener.class);

	public void contextInitialized(final ServletContextEvent sce) {
		final String contextName = extractContextName(sce);
		final String logConfigurationDirectory = System
				.getProperty(LOG_CONFIGURATION_DIR);
		final String configurationFilePath = constructConfFilePath(
				logConfigurationDirectory, contextName);
		initLogger(configurationFilePath);
	}

	private String extractContextName(final ServletContextEvent sce) {
		String contextPath = sce.getServletContext().getContextPath();
		if (contextPath.isEmpty()) {
			contextPath = sce.getServletContext().getInitParameter(
					"contextPath");
		}

		String contextName;
		if (contextPath == null) {
			// IntelliJ in run & debug mode
			contextName = sce.getServletContext().getServletContextName();
		} else {
			contextName = extractContextName(contextPath);
		}

		return contextName;
	}

	private String constructConfFilePath(
			final String logConfigurationDirectory, final String contextName) {
		return logConfigurationDirectory + File.separator + contextName
				+ LOGBACK_FILE_SUFFIX;
	}

	private String extractContextName(final String contextPath) {
		if (contextPath.startsWith("/")) {
			return contextPath.substring(1, contextPath.length());
		}
		return contextPath;
	}

	private void initLogger(final String configurationFilePath) {
		final LoggerContext lc = (LoggerContext) LoggerFactory
				.getILoggerFactory();
		try {
			final JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(lc);
			// the context was probably already configured by default
			// configuration rules
			lc.reset();
			configurator.doConfigure(configurationFilePath);
		} catch (final JoranException je) {
			LOGGER.error("error during logger initialisation", je);
		}
		StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
		LOGGER.info("Entering application.");
	}

	public void contextDestroyed(final ServletContextEvent sce) {
	}

}
