package com.pk.et.infra.model.bi;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.pk.et.infra.bi.ReportableDataEntity;
import com.pk.et.infra.model.ProductType;

/**
 * This class defines a template column data mapping id.
 */
public class TemplateColumnDataMappingId implements Serializable {

	/**
	 * For the serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The template column.
	 */
	private String templateColumn;

	/**
	 * The product type.
	 */
	@Enumerated(EnumType.STRING)
	private ProductType productType;

	/**
	 * The entity name.
	 */
	@Enumerated(EnumType.STRING)
	private ReportableDataEntity entity;

	/**
	 * The field name.
	 */
	private String field;

	/**
	 * The default constructor. Used for the serialization.
	 */
	public TemplateColumnDataMappingId() {
	}

	/**
	 * Constructor.
	 * 
	 * @param templateColumn
	 *            the template column to use.
	 * @param productType
	 *            the product type to use.
	 * @param entity
	 *            the entity to use.
	 * @param field
	 *            the field name to use.
	 */
	public TemplateColumnDataMappingId(final String templateColumn,
			final ProductType productType, final ReportableDataEntity entity,
			final String field) {
		this.templateColumn = templateColumn;
		this.productType = productType;
		this.entity = entity;
		this.field = field;
	}

	public String getTemplateColumn() {
		return this.templateColumn;
	}

	public ProductType getProductType() {
		return this.productType;
	}

	public ReportableDataEntity getEntity() {
		return this.entity;
	}

	public String getField() {
		return this.field;
	}

	@Override
	public boolean equals(final Object other) {

		if (!(other instanceof TemplateColumnDataMappingId)) {
			return false;
		}
		final TemplateColumnDataMappingId castOther = (TemplateColumnDataMappingId) other;

		return Objects.equal(this.getTemplateColumn(),
				castOther.getTemplateColumn())
				&& Objects.equal(this.getProductType(),
						castOther.getProductType())
				&& Objects.equal(this.getEntity(), castOther.getEntity())
				&& Objects.equal(this.getField(), castOther.getField());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.getTemplateColumn(),
				this.getProductType(), this.getEntity(), this.getField());
	}

}
