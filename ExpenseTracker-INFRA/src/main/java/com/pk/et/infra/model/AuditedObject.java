package com.pk.et.infra.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pk.et.infra.util.ETConstants;

@MappedSuperclass
public abstract class AuditedObject extends BaseEntity {
	private static final long serialVersionUID = ETConstants.etVersion;

	protected String auditUser;

	protected Calendar auditTimestamp;

	@Column(name = "AUDIT_USER")
	public String getAuditUser() {
		return this.auditUser;
	}

	public void setAuditUser(final String auditUser) {
		this.auditUser = auditUser;
	}

	@Column(name = "AUDIT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getAuditTimestamp() {
		return this.auditTimestamp;
	}

	public void setAuditTimestamp(final Calendar auditTimestamp) {
		this.auditTimestamp = auditTimestamp;
	}

	@PrePersist
	@PreUpdate
	public void updateAuditInfo() {
		// setAuditUser(AppContext.getContext()!=null?AppContext.getContext().getUserName():null);
		setAuditTimestamp(Calendar.getInstance());
	}

	@Override
	public abstract Object getId();
}
