package com.pk.et.infra.model.bi;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.google.common.collect.Lists;
import com.pk.et.infra.bi.TemplateColumnParametrizationComparator;
import com.pk.et.infra.model.BaseEntity;

@Entity
@Table(name = "TEMPLATE_GRID_PARAM")
public class ReportItemParametrization extends
		BaseEntity<ReportItemParametrizationId> {

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "reportItem", column = @Column(name = "REPORT_ITEM", nullable = false, length = 1020)),
			@AttributeOverride(name = "templateParametrization", column = @Column(name = "TEMPLATE_PARAMETRIZATION", nullable = false, length = 1020)) })
	private ReportItemParametrizationId id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "TEMPLATE_PARAMETRIZATION", nullable = false, insertable = false, updatable = false)
	private TemplateParametrization templateParametrization;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPORT_ITEM", nullable = false, insertable = false, updatable = false)
	private ReportItem reportItem;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "reportItem", orphanRemoval = true, cascade = {
			CascadeType.PERSIST, CascadeType.REMOVE })
	@OrderBy("indexPosition")
	private Set<TemplateColumnParametrization> columns;

	@Override
	public ReportItemParametrizationId getId() {
		return this.id;
	}

	@Override
	public void setId(final ReportItemParametrizationId id) {
		this.id = id;
	}

	public TemplateParametrization getTemplateParametrization() {
		return this.templateParametrization;
	}

	public void setTemplateParametrization(
			final TemplateParametrization templateParametrization) {
		this.templateParametrization = templateParametrization;
	}

	public ReportItem getReportItem() {
		return this.reportItem;
	}

	public void setReportItem(final ReportItem reportItem) {
		this.reportItem = reportItem;
	}

	/**
	 * Gets the columns associated to the grid.
	 */
	public List<TemplateColumnParametrization> getColumns() {
		return Collections.unmodifiableList(Lists.newArrayList(this.columns));
	}

	/**
	 * Associates a column to the grid.
	 * 
	 * @param column
	 *            the column to add.
	 */
	public void addColumn(final TemplateColumnParametrization column) {
		if (!this.columns.contains(column)) {
			this.columns.add(column);
		}
	}

	/**
	 * Removes a column from the grid.
	 * 
	 * @param column
	 *            the column to remove.
	 */
	public void removeColumn(final TemplateColumnParametrization column) {
		if (this.columns.contains(column)) {
			this.columns.remove(column);
		}
	}

	/**
	 * Create method.
	 * 
	 * @return A new Report Item.
	 */
	public static ReportItemParametrization create() {
		final ReportItemParametrization reportItemParametrization = new ReportItemParametrization();
		reportItemParametrization.columns = new TreeSet<TemplateColumnParametrization>(
				new TemplateColumnParametrizationComparator());
		return reportItemParametrization;
	}

	/**
	 * Create method.
	 * 
	 * @return A new Template grid.
	 */
	public static ReportItemParametrization create(final ReportItem reportItem,
			final TemplateParametrization templateParametrization) {
		final ReportItemParametrization reportItemParametrization = create();
		reportItemParametrization.setId(new ReportItemParametrizationId(
				reportItem.getId(), templateParametrization.getId()));
		reportItemParametrization.setReportItem(reportItem);
		reportItemParametrization
				.setTemplateParametrization(templateParametrization);
		return reportItemParametrization;
	}

	@Override
	public ReportItemParametrization clone() {
		final ReportItemParametrization clone = (ReportItemParametrization) super
				.clone();
		clone.setId(new ReportItemParametrizationId(this.getReportItem()
				.getId(), null));
		clone.columns = new TreeSet<TemplateColumnParametrization>(
				new TemplateColumnParametrizationComparator());
		for (final TemplateColumnParametrization columnToClone : this.columns) {
			final TemplateColumnParametrization columnCloned = (TemplateColumnParametrization) columnToClone
					.clone();
			clone.addColumn(columnCloned);
			columnCloned.setReportItem(clone);
		}
		return clone;
	}

}
