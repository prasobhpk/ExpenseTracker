package com.pk.et.infra.model.bi;

import java.io.Serializable;

public class TemplateColumnParametrizationId implements Serializable {

	/**
	 * For the serialization.
	 */
	private static final long serialVersionUID = 1L;

	private String reportItem;

	private String templateParametrization;

	private String reportColumn;

	public TemplateColumnParametrizationId() {
	}

	public TemplateColumnParametrizationId(final String grid,
			final String templateParametrization, final String reportColumn) {
		this.reportItem = grid;
		this.templateParametrization = templateParametrization;
		this.reportColumn = reportColumn;
	}

	public String getReportItem() {
		return this.reportItem;
	}

	public void setReportItem(final String reportItem) {
		this.reportItem = reportItem;
	}

	public String getTemplateParametrization() {
		return this.templateParametrization;
	}

	public void setTemplateParametrization(final String templateParametrization) {
		this.templateParametrization = templateParametrization;
	}

	public String getReportColumn() {
		return this.reportColumn;
	}

	public void setReportColumn(final String reportColumn) {
		this.reportColumn = reportColumn;
	}

	@Override
	public String toString() {
		return "TemplateColumnParametrizationId [grid=" + this.reportItem
				+ ", templateParametrization=" + this.templateParametrization
				+ ", reportColumn=" + this.reportColumn + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (this.reportItem == null ? 0 : this.reportItem.hashCode());
		result = prime
				* result
				+ (this.reportColumn == null ? 0 : this.reportColumn.hashCode());
		result = prime
				* result
				+ (this.templateParametrization == null ? 0
						: this.templateParametrization.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TemplateColumnParametrizationId other = (TemplateColumnParametrizationId) obj;
		if (this.reportItem == null) {
			if (other.reportItem != null) {
				return false;
			}
		} else if (!this.reportItem.equals(other.reportItem)) {
			return false;
		}
		if (this.reportColumn == null) {
			if (other.reportColumn != null) {
				return false;
			}
		} else if (!this.reportColumn.equals(other.reportColumn)) {
			return false;
		}
		if (this.templateParametrization == null) {
			if (other.templateParametrization != null) {
				return false;
			}
		} else if (!this.templateParametrization
				.equals(other.templateParametrization)) {
			return false;
		}
		return true;
	}
}
