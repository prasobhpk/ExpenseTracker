package com.pk.et.bi.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Row of a report matrix of data
 */
public class ReportDataMatrixRow {

	Map<String, Object> valueByTemplateColumnName;

	private String itemId;

	public ReportDataMatrixRow() {
		this.valueByTemplateColumnName = new HashMap<String, Object>();
	}

	public Object getValue(final String templateColumnName) {
		return this.valueByTemplateColumnName.get(templateColumnName);
	}

	public void addValue(final String templateColumnName, final Object value) {
		this.valueByTemplateColumnName.put(templateColumnName, value);
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(final String itemId) {
		this.itemId = itemId;
	}

}
