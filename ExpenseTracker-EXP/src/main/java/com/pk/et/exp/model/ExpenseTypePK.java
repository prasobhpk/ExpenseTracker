package com.pk.et.exp.model;

import java.io.Serializable;

import javax.persistence.Column;

import org.hibernate.validator.constraints.NotEmpty;

//@Embeddable
public class ExpenseTypePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5551582770818256623L;
	private Long userId;
	private String type;

	public Long getUserId() {
		return userId;
	}

	@Column(name = "USER_FK")
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@NotEmpty
	@Column(name = "EXP_TYPE", nullable = false)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof ExpenseTypePK) {
			ExpenseTypePK that = (ExpenseTypePK) o;
			return this.userId.equals(that.userId)
					&& this.type.equals(that.type);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return userId.hashCode() + type.hashCode();
	}

}
