package com.pk.et.infra.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;

import com.pk.et.infra.util.ETConstants;

@Cache(type = CacheType.SOFT, alwaysRefresh = true, refreshOnlyIfNewer = true)
@Entity
@Table(name = "WM_PARAMS")
public class WMParams extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Long id;

	protected Long ccyRateDec;
	protected Long priceDec;
	protected Long amountDec;
	protected Long unitDec;
	protected Long percentageDec;

	public WMParams() {

	}

	public WMParams(final Long ccyRateDec, final Long priceDec,
			final Long amountDec, final Long unitDec, final Long percentageDec) {
		super();
		this.ccyRateDec = ccyRateDec;
		this.priceDec = priceDec;
		this.amountDec = amountDec;
		this.unitDec = unitDec;
		this.percentageDec = percentageDec;
	}

	@Override
	@Id
	@GeneratedValue(generator = "WM_PARAMS_GEN", strategy = GenerationType.TABLE)
	@Column(name = "PARAM_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Column(name = "RATE_DECIMALS")
	public Long getCcyRateDec() {
		return this.ccyRateDec;
	}

	public void setCcyRateDec(final Long ccyRateDec) {
		this.ccyRateDec = ccyRateDec;
	}

	@Column(name = "PRICE_DECIMALS")
	public Long getPriceDec() {
		return this.priceDec;
	}

	public void setPriceDec(final Long priceDec) {
		this.priceDec = priceDec;
	}

	@Column(name = "AMOUNT_DECIMALS")
	public Long getAmountDec() {
		return this.amountDec;
	}

	public void setAmountDec(final Long amountDec) {
		this.amountDec = amountDec;
	}

	@Column(name = "UNIT_DECIMALS")
	public Long getUnitDec() {
		return this.unitDec;
	}

	public void setUnitDec(final Long unitDec) {
		this.unitDec = unitDec;
	}

	@Column(name = "PERCENT_DECIMALS")
	public Long getPercentageDec() {
		return this.percentageDec;
	}

	public void setPercentageDec(final Long percentageDec) {
		this.percentageDec = percentageDec;
	}

	@Override
	public String toString() {
		return "WMParams [ccyRateDec=" + this.ccyRateDec + ", priceDec="
				+ this.priceDec + ", amountDec=" + this.amountDec
				+ ", unitDec=" + this.unitDec + ", percentageDec="
				+ this.percentageDec + "]";
	}

}
