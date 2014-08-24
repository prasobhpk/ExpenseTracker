package com.pk.et.infra.model.bi;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.pk.et.infra.bi.ReportDataType;
import com.pk.et.infra.bi.TemplateColumnAlignment;
import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.model.Message;
import com.pk.et.infra.model.MessageId;
import com.pk.et.infra.model.ProductType;

@Entity
@Table(name = "TEMPLATE_COLUMN")
@NamedQueries({ //
		@NamedQuery(name = TemplateColumn.FIND_TEMPLATE_COLUMNS_FROM_REPORTABLE_FIELDS, query = "select tc from TemplateColumn tc 	join tc.columnDataMapping cdm where cdm.productType in (:productTypes) and cdm.id.entity = :entity and cdm.id.field in (:fields)"), //
		@NamedQuery(name = TemplateColumn.FIND_BY_IDS, query = "from TemplateColumn where id in :ids") //
})
public class TemplateColumn extends BaseEntity<String> {

	public static final String FIND_TEMPLATE_COLUMNS_FROM_REPORTABLE_FIELDS = "FIND_TEMPLATE_COLUMNS_FROM_REPORTABLE_FIELDS";
	public static final String FIND_BY_IDS = "FIND_TEMPLATE_COLUMNS_BY_IDS";

	@Id
	@Column(name = "NAME", updatable = false, insertable = false)
	private String id;

	@Column(name = "NAME", unique = true, nullable = false, updatable = false, insertable = false)
	private String name;

	@OneToOne
	@JoinColumns({
			@JoinColumn(name = "NAME", referencedColumnName = "KEY1", unique = true, insertable = false, updatable = false),
			@JoinColumn(name = "MESSAGE_KEY2", referencedColumnName = "KEY2", insertable = false, updatable = false) })
	private Message labelMessage;

	@OneToMany(mappedBy = "column")
	private Set<TemplateColumnDataMapping> columnDataMapping;

	@Column(name = "MESSAGE_KEY2")
	private String messageKey2;

	@Column(name = "WIDTH")
	private Integer width;

	@Column(name = "FORMAT")
	private String format;

	/**
	 * Allows overriding the default alignment of a column.
	 */
	@Column(name = "ALIGNMENT")
	@Enumerated(EnumType.STRING)
	private TemplateColumnAlignment alignment;

	public static TemplateColumn createCustomTemplateColumn(final String name) {
		final TemplateColumn column = new TemplateColumn(name);
		column.messageKey2 = MessageId.CUSTOM_KEY2_VALUE;
		return column;
	}

	/**
	 * Default constructor should only be used by the exporter. Use
	 * {@link #TemplateColumn(String)} to construct a {@link TemplateColumn}
	 * with a given name used as its primary key.
	 */
	public TemplateColumn() {
	}

	/**
	 * Create a {@link TemplateColumn} entity and set is name attribute. Will
	 * also set its id as the {@link #name} is used as the primary key.
	 * 
	 * @param name
	 *            Name of the template.
	 */
	public TemplateColumn(final String name) {
		this.id = name;
		this.name = name;
		this.columnDataMapping = new HashSet<TemplateColumnDataMapping>();
	}

	public String getName() {
		return this.name;
	}

	public Message getLabelMessage() {
		return this.labelMessage;
	}

	public void setLabelMessage(final Message labelMessage) {
		this.labelMessage = labelMessage;
	}

	public void setMessageKey2(final String messageKey2) {
		this.messageKey2 = messageKey2;
	}

	@Override
	public String getId() {
		return this.id;
	}

	/**
	 * Setting the id will also set the {@link #name} attribute used as the
	 * primary key.
	 */
	public void setId(final String id) {
		this.id = id;
		this.name = id;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(final Integer width) {
		this.width = width;
	}

	/**
	 * @return the format. Can be a number format or a date format.
	 */
	public String getFormat() {
		return this.format;
	}

	/**
	 * Set the format.
	 * 
	 * @param format
	 *            the format to set.
	 */
	public void setFormat(final String format) {
		this.format = format;
	}

	/**
	 * @return the alignment
	 */
	public TemplateColumnAlignment getAlignment() {
		return this.alignment;
	}

	/**
	 * @param alignment
	 *            the alignment to set
	 */
	public void setAlignment(final TemplateColumnAlignment alignment) {
		this.alignment = alignment;
	}

	/**
	 * Create method.
	 * 
	 * @return A new column.
	 */
	public static TemplateColumn create() {
		final TemplateColumn templateColumn = new TemplateColumn();
		templateColumn.columnDataMapping = new HashSet<TemplateColumnDataMapping>();
		return templateColumn;
	}

	/**
	 * Creates a column parameterization that is aligned with this column.
	 */
	public TemplateColumnParametrization createParametrization() {
		final TemplateColumnParametrization columnParametrization = TemplateColumnParametrization
				.create();
		columnParametrization.setColumn(this);
		return columnParametrization;
	}

	/**
	 * @return the columnDataMapping
	 */
	public Set<TemplateColumnDataMapping> getColumnDataMapping() {
		return Collections.unmodifiableSet(this.columnDataMapping);
	}

	/**
	 * Add a column data mapping to the list.
	 * 
	 * @param columnDataMapping
	 *            the {@link TemplateColumnDataMapping} to add.
	 */
	public void addColumnDataMapping(
			final TemplateColumnDataMapping columnDataMapping) {
		if (!this.columnDataMapping.contains(columnDataMapping)) {
			this.columnDataMapping.add(columnDataMapping);
		}
	}

	/**
	 * Retrieve a {@link TemplateColumnDataMapping} for a given product type.
	 * 
	 * @param templateProductType
	 *            the template product type to consider.
	 * @return the {@link TemplateColumnDataMapping} found or null if there is
	 *         no {@link TemplateColumnDataMapping} bind with the given product
	 *         type.
	 */
	public TemplateColumnDataMapping retrieveATemplateColumnDataMappingForAGivenProductType(
			final ProductType templateProductType) {
		for (final TemplateColumnDataMapping templateColumnDataMapping : getColumnDataMapping()) {
			final ProductType templateColumnDataMappingProductType = templateColumnDataMapping
					.getProductType();
			if (templateColumnDataMappingProductType
					.equals(templateProductType)) {
				return templateColumnDataMapping;
			}
		}
		return null;
	}

	/**
	 * Retrieve a {@link ReportDataType} for a given product type.
	 * 
	 * @param productType
	 *            the product type to consider.
	 * @return the {@link ReportDataType} found or null if there is no
	 *         {@link TemplateColumnDataMapping} bind to the given product type.
	 */
	public ReportDataType retrieveReportableDataTypeForAGivenProductType(
			final ProductType productType) {
		final TemplateColumnDataMapping templateColumnDataMapping = retrieveATemplateColumnDataMappingForAGivenProductType(productType);
		if (templateColumnDataMapping != null) {
			return templateColumnDataMapping.getReportableData().getType();
		}
		return null;
	}

	/**
	 * Compute the column width for the current template column. If the template
	 * column has a column width, we return this value otherwise we return a
	 * default value (this value came from a property file).
	 * 
	 * @param defaultColumnWidth
	 *            the default column width.
	 * @return the column width.
	 */
	public Integer computeColumnWidth(final Integer defaultColumnWidth) {
		final Integer columnWidth = getWidth();
		if (columnWidth != null) {
			return columnWidth;
		} else {
			return defaultColumnWidth;
		}
	}

	/**
	 * Compute the date format for the current template column. If the template
	 * column has a date format, we return this value otherwise we return a
	 * default value (this value came from a property file).
	 * 
	 * @param defaultDateFormat
	 *            the default date format.
	 * @return the date format.
	 */
	public String computeDateFormat(final String defaultDateFormat) {
		final String dateFormat = getFormat();
		if (dateFormat != null) {
			return dateFormat;
		} else {
			return defaultDateFormat;
		}
	}

	/**
	 * Compute the number format for the current template column. If the
	 * template column has a number format, we return this value otherwise we
	 * return a default value (this value came from a property file).
	 * 
	 * @param defaultNumberFormat
	 *            the default number format.
	 * @return the number format.
	 */
	public String computeNumberFormat(final String defaultNumberFormat) {
		final String numberFormat = getFormat();
		if (numberFormat != null) {
			return numberFormat;
		} else {
			return defaultNumberFormat;
		}
	}

}
