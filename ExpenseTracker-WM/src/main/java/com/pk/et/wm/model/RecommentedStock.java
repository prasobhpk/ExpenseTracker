package com.pk.et.wm.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.model.User;
import com.pk.et.infra.util.ETConstants;

@Entity(name = "STOCK_PREFERENCE")
public class RecommentedStock extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Long id;
	private User user;
	private Equity stock;
	private BigDecimal buyPrice;
	private BigDecimal targetPrice;
	private BigDecimal longtermBuyPrice;
	private String description;

	@Override
	@Id
	@GeneratedValue(generator = "FAV_STOCK_GEN", strategy = GenerationType.TABLE)
	@Column(name = "FAV_STOCK_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STOCK_ID")
	public Equity getStock() {
		return this.stock;
	}

	public void setStock(final Equity stock) {
		this.stock = stock;
	}

	@Column(name = "BUY_PRICE")
	public BigDecimal getBuyPrice() {
		return this.buyPrice;
	}

	public void setBuyPrice(final BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	@Column(name = "TARGET_PRICE")
	public BigDecimal getTargetPrice() {
		return this.targetPrice;
	}

	public void setTargetPrice(final BigDecimal targetPrice) {
		this.targetPrice = targetPrice;
	}

	@Column(name = "LNG_TARGET_PRICE")
	public BigDecimal getLongtermBuyPrice() {
		return this.longtermBuyPrice;
	}

	public void setLongtermBuyPrice(final BigDecimal longtermBuyPrice) {
		this.longtermBuyPrice = longtermBuyPrice;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
