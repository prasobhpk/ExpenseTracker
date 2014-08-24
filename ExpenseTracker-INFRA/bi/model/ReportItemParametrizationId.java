package com.pk.et.infra.model.bi;

import com.google.common.base.Objects;

public class ReportItemParametrizationId {
	private static final long serialVersionUID = 1L;

	private String reportItem;

	private String templateParametrization;

	public ReportItemParametrizationId() {
	}

	public ReportItemParametrizationId(final String reportItem,
			final String templateParametrization) {
		this.reportItem = reportItem;
		this.templateParametrization = templateParametrization;
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

	@Override
	public boolean equals(final Object other) {

		if (!(other instanceof ReportItemParametrizationId)) {
			return false;
		}
		final ReportItemParametrizationId castOther = (ReportItemParametrizationId) other;
		return Objects.equal(this.getReportItem(), castOther.getReportItem())
				&& Objects.equal(this.getTemplateParametrization(),
						castOther.getTemplateParametrization());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.getReportItem(),
				this.getTemplateParametrization());

	}

	@Override
	public String toString() {
		return "TemplateGridParametrizationId [reportItem=" + this.reportItem
				+ ", templateParametrization=" + this.templateParametrization
				+ "]";
	}
}
