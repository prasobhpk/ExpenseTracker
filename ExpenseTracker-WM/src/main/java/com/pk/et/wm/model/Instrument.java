package com.pk.et.wm.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.util.ETConstants;

@Cache(type = CacheType.SOFT, alwaysRefresh = true, refreshOnlyIfNewer = true)
@Entity
@Table(name = "INSTRUMENTS", uniqueConstraints = @UniqueConstraint(columnNames = { "INSTRUMENT_SYMBOL" }))
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "INSTRUMENT_TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class Instrument extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	protected Long id;
	protected String name;
	protected String symbol;
	protected String group;
	protected String code;
	protected String isin;

	public Instrument() {

	}

	public Instrument(final String name, final String uniqCode,
			final String code, final String group) {
		this.name = name;
		this.symbol = uniqCode;
		this.code = code;
		this.group = group;
	}

	@Override
	@Id
	@GeneratedValue(generator = "INSTRUMENT_GEN", strategy = GenerationType.TABLE)
	@Column(name = "INSTRUMENT_ID")
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	@NotNull
	@Column(name = "INSTRUMENT_NAME", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	@Column(name = "INSTRUMENT_SYMBOL", nullable = false)
	public String getSymbol() {
		return this.symbol;
	}

	public void setSymbol(final String symbol) {
		this.symbol = symbol;
	}

	@Column(name = "INSTRUMENT_GROUP")
	public String getGroup() {
		return this.group;
	}

	public void setGroup(final String group) {
		this.group = group;
	}

	@Column(name = "INSTRUMENT_CODE")
	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	@Column(name = "ISIN")
	public String getIsin() {
		return this.isin;
	}

	public void setIsin(final String isin) {
		this.isin = isin;
	}

	@Override
	public int hashCode() {
		return (this.symbol != null ? this.symbol.hashCode() : 0);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof Instrument))
			return false;

		final Instrument com = (Instrument) obj;

		return (this.symbol.equals(com.symbol));
	}

	@Override
	public String toString() {
		return this.symbol + "|";
	}

}
