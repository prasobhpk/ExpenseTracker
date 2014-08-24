package com.pk.et.infra.util;

public class Notification {

	private String message;

	private boolean failure;

	public Notification(final String message, final boolean failure) {
		this.message = message;
		this.failure = failure;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public boolean isFailure() {
		return this.failure;
	}

	public void setFailure(final boolean failure) {
		this.failure = failure;
	}

	@Override
	public String toString() {
		return "Notification [message=" + this.message + ", failure="
				+ this.failure + "]";
	}
}
