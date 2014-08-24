package com.pk.et.exp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;
import org.hibernate.validator.constraints.NotEmpty;

import com.pk.et.infra.model.BaseEntity;
import com.pk.et.infra.util.ETConstants;

@Cache(type = CacheType.SOFT, alwaysRefresh = true, refreshOnlyIfNewer = true)
@Entity
@Table(name = "EXPENSE_TYPES", uniqueConstraints = @UniqueConstraint(columnNames = {
		"USER_EXP_FK", "EXP_TYPE" }))
public class ExpenseType extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Long id;
	private UserExpense userExpense;
	private String type;
	private String description;
	private boolean showInDashboard;

	public ExpenseType() {
		this.description = "Expense";
		this.showInDashboard = true;
	}

	public ExpenseType(final String type) {
		this.type = type;
		this.description = type;
	}

	@Override
	@Id
	@GeneratedValue(generator = "EXP_TYPE_GEN", strategy = GenerationType.TABLE)
	@Column(name = "TYPE_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "USER_EXP_FK")
	public UserExpense getUserExpense() {
		return this.userExpense;
	}

	public void setUserExpense(final UserExpense userExpense) {
		this.userExpense = userExpense;
	}

	@NotEmpty
	@Column(name = "EXP_TYPE", nullable = false)
	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	// @Lob
	@Column(name = "DESCRIPTION", length = 2500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@Column(name = "SHOW_IN_DB")
	public boolean isShowInDashboard() {
		return this.showInDashboard;
	}

	public void setShowInDashboard(final boolean showInDashboard) {
		this.showInDashboard = showInDashboard;
	}

}
