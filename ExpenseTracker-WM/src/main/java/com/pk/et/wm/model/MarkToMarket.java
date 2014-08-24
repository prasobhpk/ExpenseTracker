package com.pk.et.wm.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.util.ETConstants;

@Entity
@Table(name = "MTMS", uniqueConstraints = @UniqueConstraint(columnNames = {
		"SYMBOL", "SERIES", "TRADE_DATE" }))
public class MarkToMarket extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Long id;
	private String symbol;
	private String series;
	private Equity underlying;
	private BigDecimal previousClose;
	private BigDecimal openPrice;
	private BigDecimal daysHigh;
	private BigDecimal daysLow;
	private BigDecimal lastTradedPrice;
	private BigDecimal closePrice;
	private Date tradeDate;
	private BigDecimal totalTradedQty;
	private BigDecimal totalTradedValue;
	private BigDecimal percentageDiff;
	private BigDecimal fiftyTwoWeekHigh;
	private BigDecimal fiftyTwoWeekLow;
	private BigDecimal changePercent;
	private BigDecimal change;

	@Override
	@Id
	@GeneratedValue(generator = "MTM_GEN", strategy = GenerationType.TABLE)
	@Column(name = "MTM_ID")
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	@Column(name = "SYMBOL")
	public String getSymbol() {
		return this.symbol;
	}

	public void setSymbol(final String symbol) {
		this.symbol = symbol;
	}

	@Column(name = "SERIES")
	public String getSeries() {
		return this.series;
	}

	public void setSeries(final String series) {
		this.series = series;
	}

	@ManyToOne
	@JoinColumn(name = "INSTRUMENT_ID")
	public Equity getUnderlying() {
		return this.underlying;
	}

	public void setUnderlying(final Equity underlying) {
		this.underlying = underlying;
	}

	@Column(name = "PREVIOUS_CLOSE")
	public BigDecimal getPreviousClose() {
		return this.previousClose;
	}

	public void setPreviousClose(final BigDecimal previousClose) {
		this.previousClose = previousClose;
	}

	@Column(name = "OPEN_PRICE")
	public BigDecimal getOpenPrice() {
		return this.openPrice;
	}

	public void setOpenPrice(final BigDecimal openPrice) {
		this.openPrice = openPrice;
	}

	@Column(name = "DAYS_HIGH")
	public BigDecimal getDaysHigh() {
		return this.daysHigh;
	}

	public void setDaysHigh(final BigDecimal daysHigh) {
		this.daysHigh = daysHigh;
	}

	@Column(name = "DAYS_LOW")
	public BigDecimal getDaysLow() {
		return this.daysLow;
	}

	public void setDaysLow(final BigDecimal daysLow) {
		this.daysLow = daysLow;
	}

	@Column(name = "LAST_TRADED_PRICE")
	public BigDecimal getLastTradedPrice() {
		return this.lastTradedPrice;
	}

	public void setLastTradedPrice(final BigDecimal lastTradedPrice) {
		this.lastTradedPrice = lastTradedPrice;
	}

	@Column(name = "CLOSE_PRICE")
	public BigDecimal getClosePrice() {
		return this.closePrice;
	}

	public void setClosePrice(final BigDecimal closePrice) {
		this.closePrice = closePrice;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "TRADE_DATE")
	public Date getTradeDate() {
		return this.tradeDate;
	}

	public void setTradeDate(final Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	@Column(name = "TOTAL_TRADED_QTY")
	public BigDecimal getTotalTradedQty() {
		return this.totalTradedQty;
	}

	public void setTotalTradedQty(final BigDecimal totalTradedQty) {
		this.totalTradedQty = totalTradedQty;
	}

	@Column(name = "TOTAL_TRADED_VALUE")
	public BigDecimal getTotalTradedValue() {
		return this.totalTradedValue;
	}

	public void setTotalTradedValue(final BigDecimal totalTradedValue) {
		this.totalTradedValue = totalTradedValue;
	}

	@Transient
	public BigDecimal getPercentageDiff() {
		return this.percentageDiff;
	}

	public void setPercentageDiff(final BigDecimal percentageDiff) {
		this.percentageDiff = percentageDiff;
	}

	@Transient
	public BigDecimal getFiftyTwoWeekHigh() {
		return this.fiftyTwoWeekHigh;
	}

	public void setFiftyTwoWeekHigh(final BigDecimal fiftyTwoWeekHigh) {
		this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
	}

	@Transient
	public BigDecimal getFiftyTwoWeekLow() {
		return this.fiftyTwoWeekLow;
	}

	public void setFiftyTwoWeekLow(final BigDecimal fiftyTwoWeekLow) {
		this.fiftyTwoWeekLow = fiftyTwoWeekLow;
	}

	@Transient
	public BigDecimal getChangePercent() {
		return this.changePercent;
	}

	public void setChangePercent(final BigDecimal changePercent) {
		this.changePercent = changePercent;
	}

	@Transient
	public BigDecimal getChange() {
		return this.change;
	}

	public void setChange(final BigDecimal change) {
		this.change = change;
	}

	@Override
	public String toString() {
		return "MarkToMarket [id=" + this.id + ", symbol=" + this.symbol
				+ ", series=" + this.series + ", previousClose="
				+ this.previousClose + ", openPrice=" + this.openPrice
				+ ", daysHigh=" + this.daysHigh + ", daysLow=" + this.daysLow
				+ ", lastTradedPrice=" + this.lastTradedPrice + ", closePrice="
				+ this.closePrice + ", tradeDate=" + this.tradeDate
				+ ", totalTradedQty=" + this.totalTradedQty
				+ ", totalTradedValue=" + this.totalTradedValue + "]";
	}

}
