package com.pk.et.infra.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pk.et.infra.util.ETConstants;

/**
 * Entity implementation class for Entity: ETBase
 * 
 */
@MappedSuperclass
// @Access(AccessType.PROPERTY)
public abstract class BaseEntity<IDT> implements Serializable, Cloneable {
	/**
	 * The logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(BaseEntity.class);
	private static final long serialVersionUID = ETConstants.etVersion;

	public BaseEntity() {
		super();
	}

	private Long version;

	// @Transient
	protected boolean newEntity = false;

	public abstract IDT getId();

	@Version
	@Column(name = "VER_NO")
	public Long getVersion() {
		return this.version;
	}

	public void setVersion(final Long version) {
		this.version = version;
	}

	/**
	 * Generates an hash code value based on the entity real class and the
	 * entity id.
	 * 
	 * @return the generated hash code.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ (this.getId() == null ? super.hashCode() : this.getId()
						.hashCode());
		return result;
	}

	/**
	 * Verify entity equality. Two entites are considered equal if their classes
	 * and their ids are the same (values of other attributes are not
	 * pertinent).
	 * 
	 * @param obj
	 *            , the object to compare.
	 * 
	 * @return a boolean.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		// if (Hibernate.getClass(this) != Hibernate.getClass(obj)) {
		// return false;
		// }
		final BaseEntity<IDT> other = (BaseEntity<IDT>) obj;
		if (this.getId() == null) {
			return false;
		} else {
			return this.getId().equals(other.getId());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseEntity<IDT> clone() {
		BaseEntity<IDT> copy = null;
		try {
			copy = (BaseEntity<IDT>) super.clone();
			copy.newEntity = true;
			reinitializeJPAEntity(copy);
		} catch (final CloneNotSupportedException cnse) {
			LOG.error("Error while cloning " + this.getClass(), cnse);
		}
		return copy;
	}

	/**
	 * Reinitialize id and version properties.
	 * 
	 * @param entity
	 *            , the entity to reinitialize.
	 */
	private void reinitializeJPAEntity(final BaseEntity<IDT> entity) {
		entity.setId(null);
		entity.setVersion(0L);
	}

	public abstract void setId(IDT id);

	@Override
	public String toString() {
		return getClass().getName() + " [id=" + getId() + "]";
	}
}
