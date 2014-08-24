package com.pk.et.wm.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.util.ETConstants;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Long id;
	private Equity instrument;
	private Portfolio portfolio;
	private BigDecimal price;
	private BigDecimal quantity;
	// price*qty
	private BigDecimal amount;
	private BigDecimal brokerage;
	private BigDecimal otherCharges;
	// price*qty+brokerage+other...
	private BigDecimal totalAmount;
	private boolean traded;
	private Date tradeDate;
	private TransactionType type;
	private String exchange;
	private BrokerageStructure brokerageStructure;
	private BigDecimal profit;

	public Transaction() {
		this.profit = BigDecimal.ZERO;
	}

	public Transaction(final Equity instrument/* ,BigDecimal price */,
			final BigDecimal quantity, final BigDecimal amount,
			final BigDecimal brokerage, final BigDecimal total,
			final BrokerageStructure structure) {
		this.instrument = instrument;
		// this.price=price;
		this.quantity = quantity;
		this.amount = amount;
		this.brokerage = brokerage;
		this.totalAmount = total;
		this.brokerageStructure = structure;
	}

	@Override
	@Id
	@GeneratedValue(generator = "TXN_GEN", strategy = GenerationType.TABLE)
	@Column(name = "TRAN_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@ManyToOne()
	@JoinColumn(name = "INSTRUMENT_ID", nullable = false)
	public Equity getInstrument() {
		return this.instrument;
	}

	public void setInstrument(final Equity instrument) {
		this.instrument = instrument;
	}

	@ManyToOne()
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

	@Column(name = "OTHER_CHRGS")
	public BigDecimal getOtherCharges() {
		return this.otherCharges;
	}

	public void setOtherCharges(final BigDecimal otherCharges) {
		this.otherCharges = otherCharges;
	}

	@NotNull
	@Column(name = "TOTAL_AMOUNT", nullable = false)
	public BigDecimal getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(final BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Column(name = "TRADED", nullable = false)
	public boolean isTraded() {
		return this.traded;
	}

	public void setTraded(final boolean traded) {
		this.traded = traded;
	}

	@DateTimeFormat(iso = ISO.DATE)
	@Temporal(TemporalType.DATE)
	@Past
	@Column(name = "TRADE_DATE", nullable = false)
	public Date getTradeDate() {
		return this.tradeDate;
	}

	public void setTradeDate(final Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	@Column(name = "TRANSACTION_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	public TransactionType getType() {
		return this.type;
	}

	public void setType(final TransactionType type) {
		this.type = type;
	}

	@Column(name = "EXCHANGE", nullable = false)
	public String getExchange() {
		return this.exchange;
	}

	public void setExchange(final String exchange) {
		this.exchange = exchange;
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

	@Transient
	public BigDecimal getProfit() {
		return this.profit;
	}

	public void setProfit(final BigDecimal profit) {
		this.profit = profit;
	}
}
