package com.pk.et.wm.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.pk.et.infra.util.ETConstants;

@Entity
@Table(name = "EQUITIES")
@DiscriminatorValue("EQ")
public class Equity extends Instrument {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Exchanges exchange;
	private boolean mtmCandidate;
	private String nseCode;

	public Equity() {

	}

	public Equity(final String name, final String uniqCode, final String code,
			final String group) {
		super(name, uniqCode, code, group);
	}

	@Column(name = "STOCK_EXCHANGE")
	@Enumerated(EnumType.STRING)
	public Exchanges getExchange() {
		return this.exchange;
	}

	public void setExchange(final Exchanges exchange) {
		this.exchange = exchange;
	}

	@Column(name = "MTM_CANDIDATE")
	public boolean isMtmCandidate() {
		return this.mtmCandidate;
	}

	public void setMtmCandidate(final boolean mtmCandidate) {
		this.mtmCandidate = mtmCandidate;
	}

	@Column(name = "NSE_CODE")
	public String getNseCode() {
		return this.nseCode;
	}

	public void setNseCode(final String nseCode) {
		this.nseCode = nseCode;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
