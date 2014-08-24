package com.pk.et.exp.model;

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
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.util.ETConstants;

@Entity
@Table(name = "EXPENSES")
public class Expense extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Long id;
	private int expense;
	private UserExpense userExpense;
	private ExpenseType expenseType;
	private String description;
	private Date expDate;
	private Long typeId;
	private boolean active;

	public Expense() {
		this.expDate = new Date();
		this.description = "Expense";
		this.active = true;
	}

	// this constructor is used in hql
	public Expense(final Date date, final int expense) {
		this();
		this.expDate = date;
		this.expense = expense;
	}

	@Override
	@Id
	@GeneratedValue(generator = "EXP_GEN", strategy = GenerationType.TABLE)
	@Column(name = "EXPENSE_ID")
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	// @Min(0)
	@Column(name = "EXPENSE", nullable = false)
	public int getExpense() {
		return this.expense;
	}

	public void setExpense(final int expense) {
		this.expense = expense;
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

	@ManyToOne
	@JoinColumn(name = "TYPE_ID")
	// @XmlTransient
	public ExpenseType getExpenseType() {
		return this.expenseType;
	}

	public void setExpenseType(final ExpenseType expenseType) {
		this.expenseType = expenseType;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@DateTimeFormat(iso = ISO.DATE)
	@Temporal(TemporalType.DATE)
	@Past
	@Column(name = "EXPEXNSE_DATE", nullable = false)
	public Date getExpDate() {
		return this.expDate;
	}

	public void setExpDate(final Date expDate) {
		this.expDate = expDate;
	}

	@Transient
	public Long getTypeId() {
		return this.typeId;
	}

	public void setTypeId(final Long typeId) {
		this.typeId = typeId;
	}

	@Column(name = "ACTIVE")
	public boolean isActive() {
		return this.active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Expense [id=" + this.id + ", expense=" + this.expense
				+ ", description=" + this.description + ", expDate="
				+ this.expDate + "]";
	}

}
