package com.pk.et.infra.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pk.et.infra.util.ETConstants;

/***
 * 
 * @author PrasobhP
 * 
 */

@Entity
@Table(name = "EXTENSIONS")
public class Extension extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;

	private Long id;

	private String tenant;

	private String entity = "";

	private String attributeName = "";

	private String javaType = "java.lang.String";

	private String cluster;

	public Extension() {
	}

	public Extension(final String entity, final String attributeName) {
		setEntity(entity);
		setAttributeName(attributeName);
	}

	public Extension(final String entity, final String attributeName,
			final String javaType) {
		setEntity(entity);
		setAttributeName(attributeName);
		setJavaType(javaType);
		setTenant("tenant1");
	}

	public Extension(final String entity, final String attributeName,
			final String javaType, final String cluster, final String tenant) {
		setEntity(entity);
		setAttributeName(attributeName);
		setJavaType(javaType);
		setCluster(cluster);
		setTenant(tenant);
	}

	@Override
	@Id
	@GeneratedValue(generator = "EXTENSION_GEN", strategy = GenerationType.TABLE)
	@Column(name = "EXTENSION_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Column(name = "TENANT")
	public String getTenant() {
		return this.tenant;
	}

	public void setTenant(final String tenant) {
		this.tenant = tenant;
	}

	@Column(name = "ENTITY_NAME")
	public String getEntity() {
		return this.entity;
	}

	public void setEntity(final String entity) {
		this.entity = entity;
	}

	@Column(name = "EXTENSION_NAME")
	public String getAttributeName() {
		return this.attributeName;
	}

	public void setAttributeName(final String attributeName) {
		this.attributeName = attributeName;
	}

	@Column(name = "EXTENSION_TYPE")
	public String getJavaType() {
		return this.javaType;
	}

	public void setJavaType(final String javaType) {
		this.javaType = javaType;
	}

	@Column(name = "CLUSTER_NAME", nullable = false, length = 1000)
	public String getCluster() {
		return this.cluster;
	}

	public void setCluster(final String cluster) {
		this.cluster = cluster;
	}

	@Override
	public int hashCode() {
		return (this.entity + this.attributeName).hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Extension))
			return false;
		final Extension obj1 = (Extension) obj;
		return obj1 != null && this.entity.equals(obj1.entity)
				&& this.attributeName.equals(obj1.attributeName);
	}
}
