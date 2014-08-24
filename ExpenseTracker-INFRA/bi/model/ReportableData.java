package com.pk.et.infra.model.bi;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.pk.et.infra.bi.ReportDataType;
import com.pk.et.infra.bi.ReportableDataEntity;
import com.pk.et.infra.model.BaseEntity;

/**
 * This class defines a reportable data.
 */
@Entity
@Table(name = "REPORTABLE_DATA")
public class ReportableData extends BaseEntity<ReportableDataId> {

	private ReportableDataId id;

	/**
	 * The report data type.
	 */

	private ReportDataType type;

	private ReportableDataEntity entity;

	private String field;

	/**
	 * Default constructor.
	 */
	public ReportableData() {
	}

	/**
	 * Create a {@link ReportableData}
	 * 
	 * @param entity
	 *            the entity name to use.
	 * @param field
	 *            the field name to use.
	 * @return the {@link ReportableData} built.
	 */
	public static ReportableData create(final ReportableDataEntity entity,
			final String field) {
		final ReportableData reportableData = new ReportableData();
		reportableData.id = new ReportableDataId(entity, field);
		reportableData.entity = entity;
		reportableData.field = field;
		return reportableData;
	}

	/**
	 * Retrieve the id ({@link ReportableDataId}).
	 */
	/**
	 * The id.
	 */
	@EmbeddedId
	@Override
	public ReportableDataId getId() {
		return this.id;
	}

	@Enumerated(EnumType.STRING)
	public ReportDataType getType() {
		return this.type;
	}

	public void setType(final ReportDataType type) {
		this.type = type;
	}

	/**
	 * Define the toString method.
	 */
	@Override
	public String toString() {
		return "ReportableData [id=" + this.id + "]";
	}

	@Column(insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	public ReportableDataEntity getEntity() {
		return this.entity;
	}

	@Column(insertable = false, updatable = false)
	public String getField() {
		return this.field;
	}
}
