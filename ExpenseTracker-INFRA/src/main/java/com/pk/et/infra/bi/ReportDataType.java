package com.pk.et.infra.bi;

public enum ReportDataType {
	STRING("string", false), //
	DECIMAL("decimal", false),
	/**
	 * The value is a string, but may contain a decimal, stored as a string and
	 * should be rendered as such. We can't use STRING here because in Excel we
	 * would have errors, so we will translate on the fly the Java object, and
	 * in case of a decimal apply a neutral format.
	 */
	STRING_CAN_BE_DECIMAL("javaObject", false), //
	INTEGER("integer", false), //
	LONG("long", false), //
	DATE_TIME("date-time", false), //
	DATE("date", false), //
	PERCENTAGE("percentage", false), //
	PERCENT_OR_VALUE("percent_or_value", false), //
	BOOLEAN("boolean", false), //
	DECIMAL_PRICE_DECOMPOSITION("decimal_price_decomposition", false), //
	DECIMAL_PRICE("decimal_price", false), //
	DYNAMIC_PRECISION_DECIMAL("dynamic_precision_decimal", false),
	LIST_STRING("list_string", true), //
	LIST_DECIMAL("list_decimal", true), //
	LIST_DATE("list_date", true);

	private String value;
	private boolean isAList;

	private ReportDataType(final String value, final boolean isAList) {
		this.value = value;
		this.isAList = isAList;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public boolean isAList() {
		return this.isAList;
	}

	public void setAList(final boolean isAList) {
		this.isAList = isAList;
	}
}
