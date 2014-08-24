package com.pk.et.infra.util;

public class ETTechnicalException extends RuntimeException {

	/**
	 * For the serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Different constructors.
	 */
	public ETTechnicalException() {
		super();
	}

	public ETTechnicalException(final String message) {
		super(message);
	}

	public ETTechnicalException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public ETTechnicalException(final Throwable cause) {
		super(cause);
	}

}