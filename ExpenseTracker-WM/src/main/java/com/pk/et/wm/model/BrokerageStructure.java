package com.pk.et.wm.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.util.ETConstants;

/**
 * @author PrasobhP Brokerage Structure of a particular financial institution
 */
@Cache(type = CacheType.SOFT, alwaysRefresh = true, refreshOnlyIfNewer = true)
@Entity()
@Table(name = "BROKERAGE_STRUCTURES", uniqueConstraints = @UniqueConstraint(columnNames = { "INSTITUTION" }))
public class BrokerageStructure extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Long id;
	private String institution;
	private BigDecimal brokerage;
	private BigDecimal minBrokerage;
	private BigDecimal serivceTax;
	private BigDecimal transactionTax;
	private BigDecimal otherCharges;

	@Override
	@Id
	@GeneratedValue(generator = "BROKERAGE_GEN", strategy = GenerationType.TABLE)
	@Column(name = "EXPENSE_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@NotNull
	@Column(name = "INSTITUTION")
	public String getInstitution() {
		return this.institution;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	@Column(name = "BROKERAGE")
	public BigDecimal getBrokerage() {
		return this.brokerage;
	}

	public void setBrokerage(final BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	@Column(name = "MIN_BROKERAGE")
	public BigDecimal getMinBrokerage() {
		return this.minBrokerage;
	}

	public void setMinBrokerage(final BigDecimal minBrokerage) {
		this.minBrokerage = minBrokerage;
	}

	@Column(name = "SERVICE_TAX")
	public BigDecimal getSerivceTax() {
		return this.serivceTax;
	}

	public void setSerivceTax(final BigDecimal serivceTax) {
		this.serivceTax = serivceTax;
	}

	@Column(name = "TRANSACTION_TAX")
	public BigDecimal getTransactionTax() {
		return this.transactionTax;
	}

	public void setTransactionTax(final BigDecimal transactionTax) {
		this.transactionTax = transactionTax;
	}

	@Column(name = "OTHER_CHARGES")
	public BigDecimal getOtherCharges() {
		return this.otherCharges;
	}

	public void setOtherCharges(final BigDecimal otherCharges) {
		this.otherCharges = otherCharges;
	}

}
