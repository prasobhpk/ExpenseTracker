package com.pk.et.wm.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.util.ETConstants;

@Entity
@Table(name = "HOLDINGS", uniqueConstraints = @UniqueConstraint(columnNames = {
		"INSTRUMENT_ID", "WEALTH_CONTEXT_ID", "PORTFOLIO_ID" }))
public class Holding extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Long id;
	private UserWealthContext wealthContext;
	private Portfolio portfolio;
	private Equity instrument;
	private BigDecimal price;
	private BigDecimal quantity;
	// price*qty
	private BigDecimal amount;
	// Total brokerage spent on holding
	private BigDecimal brokerage;
	private BigDecimal totalAmount;
	private BrokerageStructure brokerageStructure;
	private BigDecimal profit;

	public Holding() {
		this.price = BigDecimal.ZERO;
		this.quantity = BigDecimal.ZERO;
		this.amount = BigDecimal.ZERO;
		this.brokerage = BigDecimal.ZERO;
		this.totalAmount = BigDecimal.ZERO;
		this.profit = BigDecimal.ZERO;
	}

	@Override
	@Id
	@GeneratedValue(generator = "HOLDING_GEN", strategy = GenerationType.TABLE)
	@Column(name = "HOLDING_ID")
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "WEALTH_CONTEXT_ID", nullable = false)
	public UserWealthContext getWealthContext() {
		return this.wealthContext;
	}

	public void setWealthContext(final UserWealthContext wealthContext) {
		this.wealthContext = wealthContext;
	}

	@ManyToOne
	@JoinColumn(name = "INSTRUMENT_ID", nullable = false)
	public Equity getInstrument() {
		return this.instrument;
	}

	public void setInstrument(final Equity instrument) {
		this.instrument = instrument;
	}

	@ManyToOne
	@JoinColumn(name = "PORTFOLIO_ID")
	public Portfolio getPortfolio() {
		return this.portfolio;
	}

	public void setPortfolio(final Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	@Column(name = "PRICE", nullable = false)
	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(final BigDecimal price) {
		this.price = price;
	}

	@NotNull
	@Column(name = "QUANTITY", nullable = false)
	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final BigDecimal quantity) {
		this.quantity = quantity;
	}

	@Column(name = "AMOUNT", nullable = false)
	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	@NotNull
	@Column(name = "BROKERAGE")
	public BigDecimal getBrokerage() {
		return this.brokerage;
	}

	public void setBrokerage(final BigDecimal brokerage) {
		this.brokerage = brokerage;
	}

	@NotNull
	@Column(name = "TOTAL_AMOUNT", nullable = false)
	public BigDecimal getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(final BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@ManyToOne
	@JoinColumn(name = "BROKER_ID")
	public BrokerageStructure getBrokerageStructure() {
		return this.brokerageStructure;
	}

	public void setBrokerageStructure(
			final BrokerageStructure brokerageStructure) {
		this.brokerageStructure = brokerageStructure;
	}

	@Column(name = "PROFIT")
	public BigDecimal getProfit() {
		return this.profit;
	}

	public void setProfit(final BigDecimal profit) {
		this.profit = profit;
	}

	@Transient
	public BigDecimal getFee() {
		BigDecimal totalFee = BigDecimal.ZERO;

		if (this.brokerageStructure != null) {

			totalFee = this.brokerageStructure.getMinBrokerage();

			if (this.amount.multiply(

			this.brokerageStructure.getBrokerage().divide(new BigDecimal(100)))

			.compareTo(this.brokerageStructure.getMinBrokerage()) > 0) {

				totalFee = this.amount.multiply(this.brokerageStructure
						.getBrokerage().divide(

						new BigDecimal(100)));

			}

			if (this.brokerageStructure.getSerivceTax() != null) {

				totalFee = totalFee.add(totalFee
						.multiply(this.brokerageStructure

						.getSerivceTax().divide(new BigDecimal(100))));

			}

			if (this.brokerageStructure.getTransactionTax() != null) {

				totalFee = totalFee.add(this.amount
						.multiply(this.brokerageStructure

						.getTransactionTax().divide(new BigDecimal(100))));

			}

			if (this.brokerageStructure.getOtherCharges() != null) {

				totalFee = totalFee.add(this.brokerageStructure
						.getOtherCharges());

			}

		}

		return totalFee;

	}

}
