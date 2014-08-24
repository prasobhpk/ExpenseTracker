package com.pk.et.infra.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;

import com.pk.et.infra.util.ETConstants;

@Cache(type = CacheType.SOFT, alwaysRefresh = true, refreshOnlyIfNewer = true)
@Entity
@Table(name = "CONFIGURATIONS", uniqueConstraints = @UniqueConstraint(columnNames = {
		"USER_ID", "CONFIG_ITEM_ID" }))
public class Configuration extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Long id;
	private User user;
	private ConfigurationItem configItem;
	private String configItemValue;

	@Override
	@Id
	@GeneratedValue(generator = "CONFIG_GEN", strategy = GenerationType.TABLE)
	@Column(name = "CONFIG_ID")
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "CONFIG_ITEM_ID")
	public ConfigurationItem getConfigItem() {
		return this.configItem;
	}

	public void setConfigItem(final ConfigurationItem configItem) {
		this.configItem = configItem;
	}

	@Column(name = "CONFIG_VALUE", nullable = false)
	public String getConfigItemValue() {
		return this.configItemValue;
	}

	public void setConfigItemValue(final String configItemValue) {
		this.configItemValue = configItemValue;
	}

	@Override
	public String toString() {
		return this.configItem + "==>" + this.configItemValue;
	}
}
