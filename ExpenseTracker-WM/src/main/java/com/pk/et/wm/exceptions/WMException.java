package com.pk.et.wm.exceptions;

public class WMException extends Exception {
	private static final long serialVersionUID = 1L;
	private String details;
	private String status;
	
	public WMException(String message) {
		super(message);
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
