package com.pk.et.infra.bi;

import java.io.Serializable;

/**
 * Column Definition
 * 
 */
public class ColumnDefinition implements Serializable {
	/**
	 * For the serialization.
	 */
	private static final long serialVersionUID = 1L;

	protected String name;
	protected ReportDataType dataType;
	protected String label;
	protected Integer width;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public ReportDataType getDataType() {
		return this.dataType;
	}

	public void setDataType(final ReportDataType dataType) {
		this.dataType = dataType;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(final Integer width) {
		this.width = width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ColumnDefinition [name=" + this.name + ", dataType=" + this.dataType + ", label=" + this.label
				+ ", width=" + this.width + "]";
	}
}
