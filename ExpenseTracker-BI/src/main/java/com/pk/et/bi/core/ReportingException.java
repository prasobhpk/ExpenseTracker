package com.pk.et.bi.core;

@SuppressWarnings("serial")
public class ReportingException extends RuntimeException {

	public ReportingException() {
	}

	public ReportingException(final String message) {
		super(message);
	}

	public ReportingException(final Throwable cause) {
		super(cause);
	}

	public ReportingException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
