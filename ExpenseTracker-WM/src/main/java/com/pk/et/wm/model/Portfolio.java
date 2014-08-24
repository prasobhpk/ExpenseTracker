package com.pk.et.wm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.util.ETConstants;

@Entity
@Table(name = "PORTFOLIO")
public class Portfolio extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Long id;
	private UserWealthContext wealthContext;
	private String portfolioName;
	private List<Transaction> transactions = new ArrayList<Transaction>();

	@Override
	@Id
	@GeneratedValue(generator = "FOLIO_GEN", strategy = GenerationType.TABLE)
	@Column(name = "PORTFOLIO_ID")
	public Long getId() {
		return this.id;
	}

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

	@NotEmpty
	@Column(name = "PORTFOLIO_NAME", nullable = false)
	public String getPortfolioName() {
		return this.portfolioName;
	}

	public void setPortfolioName(final String portfolioName) {
		this.portfolioName = portfolioName;
	}

	@OneToMany(mappedBy = "portfolio")
	public List<Transaction> getTransactions() {
		return this.transactions;
	}

	public void setTransactions(final List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void addTransaction(final Transaction transaction) {
		this.transactions.add(transaction);
		transaction.setPortfolio(this);
	}

	@Override
	public String toString() {
		return "Portfolio [id=" + this.id + " portfolioName="
				+ this.portfolioName + "]";
	}

}
