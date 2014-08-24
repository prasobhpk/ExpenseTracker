package com.pk.et.infra.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.annotations.Index;

import com.pk.et.infra.util.ETConstants;

@Cache(type = CacheType.SOFT, alwaysRefresh = true, refreshOnlyIfNewer = true)
@Entity
@Table(name = "CONFIGURATION_ITEMS", uniqueConstraints = @UniqueConstraint(columnNames = "CONFIG_ITEM_KEY"))
public class ConfigurationItem extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Long id;
	private String configKey;
	private ValueType valueType;
	private boolean isKeyValuePair;
	private String defaultValue;
	private ConfigType configType;

	public ConfigurationItem() {

	}

	public ConfigurationItem(final String configKey, final ValueType valueType,
			final boolean isKeyValuePair, final String defaultValue,
			final ConfigType configType) {
		super();
		this.configKey = configKey;
		this.valueType = valueType;
		this.isKeyValuePair = isKeyValuePair;
		this.defaultValue = defaultValue;
		this.configType = configType;
	}

	@Override
	@Id
	@GeneratedValue(generator = "CONFIG_ITEM_GEN", strategy = GenerationType.TABLE)
	@Column(name = "CONFIG_ITEM_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Index
	@Column(name = "CONFIG_ITEM_KEY")
	public String getConfigKey() {
		return this.configKey;
	}

	public void setConfigKey(final String configKey) {
		this.configKey = configKey;
	}

	@Column(name = "CONFIG_ITEM_TYPE")
	@Enumerated(EnumType.ORDINAL)
	public ValueType getValueType() {
		return this.valueType;
	}

	public void setValueType(final ValueType valueType) {
		this.valueType = valueType;
	}

	@Column(name = "IS_KEY_VALUE_PAIR")
	public boolean isKeyValuePair() {
		return this.isKeyValuePair;
	}

	public void setKeyValuePair(final boolean isKeyValuePair) {
		this.isKeyValuePair = isKeyValuePair;
	}

	@Column(name = "DEFAULT_VALUE")
	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(final String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Column(name = "CONFIG_TYPE")
	@Enumerated(EnumType.ORDINAL)
	public ConfigType getConfigType() {
		return this.configType;
	}

	public void setConfigType(final ConfigType configType) {
		this.configType = configType;
	}

	@Override
	public String toString() {
		return this.configKey;
	}
}
