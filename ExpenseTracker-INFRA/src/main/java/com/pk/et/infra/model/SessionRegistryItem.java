package com.pk.et.infra.model;

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

import com.pk.et.infra.util.ETConstants;

@Entity
@Table(name = "SESSION_REGISTRY")
public class SessionRegistryItem extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Long id;
	private Date lastRequest;
	private User principal;
	private String sessionId;
	private boolean expired;

	public SessionRegistryItem() {
		this.lastRequest = new Date();
	}

	@Override
	@Id
	@GeneratedValue(generator = "SESSION_REGISTRY_GEN", strategy = GenerationType.TABLE)
	@Column(name = "ITEM_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_REQUEST_TIME")
	public Date getLastRequest() {
		return this.lastRequest;
	}

	public void setLastRequest(final Date lastRequest) {
		this.lastRequest = lastRequest;
	}

	@ManyToOne()
	@JoinColumn(name = "PRINCIPAL")
	public User getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(final User principal) {
		this.principal = principal;
	}

	@Column(name = "SESSION_ID", unique = true)
	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(final String sessionId) {
		this.sessionId = sessionId;
	}

	@Column(name = "EXPIRED")
	public boolean isExpired() {
		return this.expired;
	}

	public void setExpired(final boolean expired) {
		this.expired = expired;
	}

}
