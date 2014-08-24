package com.pk.et.infra.util;

public final class ETConstants {
	public static final long etVersion = 1L;

	public static final String SESSION_USER_KEY = "s_user";
	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";
	public static final String MESSAGE_KEY = "msg";
	public static final String ROLE_ADMIN = "A";
	public static final String ROLE_USER = "U";
	public static final String DATE_FORMAT = "dd-MM-yyyy";
	public static final String EXP_KEY = "exp";
	public static final String ACTION_MSG_KEY = "actionMSG";
	public static final String Exception_MSG_KEY = "exceptionMSG";

	// Class Path
	public static final String CLASS_PATH = ETConstants.class.getResource("/")
			.getPath().replaceAll("%20", " ");

	// Config file
	public static final String CONFIG = "config.properties";

	public static final String UPLOAD_ROOT = "/WEB-INF/USER_SPACE";

	public static final String YES = "YES";
	public static final String NO = "NO";

	public static final String USER_CONFIG_KEY = "USER_CONF";

	public static final String CURRENT_VIEW = "currentView";

	public static final String LOGOUT_URL = "et_logout";

	// Constants that controls pop-up styles
	public static final String ERROR = "error";
	public static final String WARN = "warn";

	// Proxy config keys
	public static final String PROXY_URL_KEY = "PROXY_URL";
	public static final String PROXY_PORT_KEY = "PROXY_PORT";
	public static final String PROXY_USED_KEY = "PROXY_ACTIVE";
	public static final String PROXY_USER = "PROXY_USER";
	public static final String PROXY_PASSWORD = "PROXY_PASSWORD";

	public static final String USER_FORECAST_KEY = "user_forecast";

	// Feeder config keys
	public static final String LAST_FEED_DATE_KEY = "LAST_FEED_DATE";
	public static final String FEED_START_DATE = "01-01-2013";

}
