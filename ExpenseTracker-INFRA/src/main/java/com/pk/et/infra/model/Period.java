package com.pk.et.infra.model;

public enum Period {
	NA("Not Applicable"), DAILY("Every Day"), WEEKLY("Every Week"), MONTHLY(
			"Every Month"), QUARTERLY("Every Quarter"), HALF_YEARLY(
			"Every 6 months"), YEARLY("Every Year");

	private Period(final String descrition) {
		this.descrition = descrition;
	}

	private String descrition;

	public String getDescrition() {
		return this.descrition;
	}

	public void setDescrition(final String descrition) {
		this.descrition = descrition;
	}

}
