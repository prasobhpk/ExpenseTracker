package com.pk.et.infra.model.bi;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.pk.et.infra.bi.ReportableDataEntity;

/**
 * This class defines a reportable data id.
 */
public class ReportableDataId implements Serializable {

	/**
	 * For the serialization.
	 */
	private static final long serialVersionUID = 1L;

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
	public ReportableDataId() {
	}

	/**
	 * A constructor.
	 * 
	 * @param entity
	 *            the entity name to use.
	 * @param field
	 *            the field name to use.
	 */
	public ReportableDataId(final ReportableDataEntity entity,
			final String field) {
		this.entity = entity;
		this.field = field;
	}

	public ReportableDataEntity getEntity() {
		return this.entity;
	}

	public String getField() {
		return this.field;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.entity == null) ? 0 : this.entity.hashCode());
		result = prime * result
				+ ((this.field == null) ? 0 : this.field.hashCode());
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
		final ReportableDataId other = (ReportableDataId) obj;
		if (this.entity != other.entity) {
			return false;
		}
		if (this.field == null) {
			if (other.field != null) {
				return false;
			}
		} else if (!this.field.equals(other.field)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "ReportableDataId [entity=" + this.entity + ", field="
				+ this.field + "]";
	}

}
