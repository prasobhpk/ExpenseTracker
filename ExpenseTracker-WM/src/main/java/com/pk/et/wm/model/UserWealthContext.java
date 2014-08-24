package com.pk.et.wm.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.model.User;
import com.pk.et.infra.util.ETConstants;

/**
 * Entity implementation class for Entity: UserWealthContext
 * 
 */
@Entity
@Table(name = "USER_WEALTH_CONTEXT")
public class UserWealthContext extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;

	public UserWealthContext() {
		super();
	}

	public UserWealthContext(final User user) {
		setUser(user);
	}

	private Long id;
	private User user;

	private Set<Equity> favStocks = new HashSet<Equity>();
	private List<Portfolio> portfolios = new ArrayList<Portfolio>();
	private List<Holding> holdings = new ArrayList<Holding>();

	@Override
	@Id
	@GeneratedValue(generator = "WEALTH_CONTEXT_GEN", strategy = GenerationType.TABLE)
	@Column(name = "WEALTH_CONTEXT_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	// @MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "USER_FK", unique = true)
	public User getUser() {
		return this.user;
	}

	public void setUser(final User user) {
		this.user = user;
		// if(user!=null)
		// this.id=user.getId();
	}

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "FAV_STOCKS", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "STOCK_ID"))
	public Set<Equity> getFavStocks() {
		return this.favStocks;
	}

	public void setFavStocks(final Set<Equity> favStocks) {
		this.favStocks = favStocks;
	}

	public void addFavStock(final Equity stock) {
		this.getFavStocks().add(stock);
	}

	@OneToMany(mappedBy = "wealthContext", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Portfolio> getPortfolios() {
		return this.portfolios;
	}

	public void setPortfolios(final List<Portfolio> portfolios) {
		this.portfolios = portfolios;
	}

	public void addPortfolio(final Portfolio portfolio) {
		this.getPortfolios().add(portfolio);
		portfolio.setWealthContext(this);
	}

	@OneToMany(mappedBy = "wealthContext", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Holding> getHoldings() {
		return this.holdings;
	}

	public void setHoldings(final List<Holding> holdings) {
		this.holdings = holdings;
	}

	public void addHolding(final Holding holding) {
		this.getHoldings().add(holding);
		holding.setWealthContext(this);
	}

}
