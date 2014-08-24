package com.pk.et.exp.model;

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

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.model.Period;
import com.pk.et.infra.util.ETConstants;

@Entity
@Table(name = "FORECAST_EXPENSE")
public class Forecast extends BaseEntity<Long> {

	private static final long serialVersionUID = ETConstants.etVersion;

	private Long id;
	private UserExpense userExpense;
	private String title;
	private String description;
	private BigDecimal forecastAmount;
	private ForecastType forecastType;
	private Date date;
	private boolean periodic;
	private Period period;

	public Forecast() {
		super();
		this.date = new Date();
		this.periodic = false;
		this.period = Period.NA;
	}

	@Override
	@Id
	@GeneratedValue(generator = "FORECAST_GEN", strategy = GenerationType.TABLE)
	@Column(name = "FORECAST_EXPENSE_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "USER_EXP_FK")
	// @XmlInverseReference(mappedBy="expenses")
	// @XmlTransient
	public UserExpense getUserExpense() {
		return this.userExpense;
	}

	public void setUserExpense(final UserExpense userExpense) {
		this.userExpense = userExpense;
	}

	@Column(name = "TITLE")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Column(name = "FORECAST_AMOUNT")
	public BigDecimal getForecastAmount() {
		return this.forecastAmount;
	}

	public void setForecastAmount(final BigDecimal forecastAmount) {
		this.forecastAmount = forecastAmount;
	}

	@Column(name = "FORECAST_TYPE")
	@Enumerated(EnumType.STRING)
	public ForecastType getForecastType() {
		return this.forecastType;
	}

	public void setForecastType(final ForecastType forecastType) {
		this.forecastType = forecastType;
	}

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "FORECAST_DATE")
	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return this.date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	@Column(name = "IS_PERIODIC")
	public boolean isPeriodic() {
		return this.periodic;
	}

	public void setPeriodic(final boolean periodic) {
		this.periodic = periodic;
	}

	@Column(name = "PERIOD")
	@Enumerated(EnumType.STRING)
	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(final Period period) {
		this.period = period;
	}

	@Override
	public String toString() {
		return "ForecastExpense [userExpense=" + this.userExpense + ", title="
				+ this.title + ", forecastAmount=" + this.forecastAmount
				+ ", date=" + this.date + ", period=" + this.period + "]";
	}

}
