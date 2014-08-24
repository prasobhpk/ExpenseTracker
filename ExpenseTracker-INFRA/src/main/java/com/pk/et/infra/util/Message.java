package com.pk.et.infra.util;


public class Message {
	private String status;
	private String msg;
	private String details;

	public Message() {
		this.status = "success";
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Message [status=" + status + ", msg=" + msg + ", details="
				+ details + "]";
	}

}
