package com.pk.et.infra.model.bi;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.model.ProductType;

/**
 * This class defines a template column data mapping.
 */
@Entity
@Table(name = "TEMPLATE_COLUMN_DATA_MAPPING")
public class TemplateColumnDataMapping extends
		BaseEntity<TemplateColumnDataMappingId> {

	/**
	 * The id.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "templateColumn", column = @Column(name = "TEMPLATE_COLUMN", nullable = false)),
			@AttributeOverride(name = "productType", column = @Column(name = "PRODUCT_TYPE", nullable = false)),
			@AttributeOverride(name = "entity", column = @Column(name = "ENTITY", nullable = false)),
			@AttributeOverride(name = "field", column = @Column(name = "FIELD", nullable = false)) })
	private TemplateColumnDataMappingId id;

	/**
	 * The column.
	 */
	@ManyToOne
	@JoinColumn(name = "TEMPLATE_COLUMN", nullable = false, insertable = false, updatable = false)
	private TemplateColumn column;

	/**
	 * The product type, or "website" for a website column
	 */
	@Column(name = "PRODUCT_TYPE", nullable = false, insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private ProductType productType;

	/**
	 * The reportable data.
	 */
	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "ENTITY", referencedColumnName = "ENTITY", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "FIELD", referencedColumnName = "FIELD", nullable = false, insertable = false, updatable = false) })
	private ReportableData reportableData;

	/**
	 * Default constructor.
	 */
	public TemplateColumnDataMapping() {
	}

	/**
	 * Create a {@link TemplateColumnDataMapping}
	 * 
	 * @param column
	 *            the column to use.
	 * @param productType
	 *            the product type to use.
	 * @param reportableData
	 *            the reportable data to use.
	 * @return the {@link TemplateColumnDataMapping} built.
	 */
	public static TemplateColumnDataMapping create(final TemplateColumn column,
			final ProductType productType, final ReportableData reportableData) {
		final TemplateColumnDataMapping templateColumnDataMapping = new TemplateColumnDataMapping();
		templateColumnDataMapping.id = new TemplateColumnDataMappingId(
				column.getId(), productType,
				reportableData.getId().getEntity(), reportableData.getId()
						.getField());
		templateColumnDataMapping.column = column;
		templateColumnDataMapping.productType = productType;
		templateColumnDataMapping.reportableData = reportableData;
		return templateColumnDataMapping;
	}

	@Override
	public TemplateColumnDataMappingId getId() {
		return this.id;
	}

	public TemplateColumn getColumn() {
		return this.column;
	}

	public void setColumn(final TemplateColumn column) {
		this.column = column;
	}

	public ReportableData getReportableData() {
		return this.reportableData;
	}

	public void setReportableData(final ReportableData reportableData) {
		this.reportableData = reportableData;
	}

	public ProductType getProductType() {
		return this.productType;
	}

	public void setProductType(final ProductType productType) {
		this.productType = productType;
	}
}
