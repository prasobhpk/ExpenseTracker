package com.pk.et.infra.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

import com.pk.et.infra.util.ETConstants;

@Entity
@Table(name = "AUTHORITIES")
public class UserAuthority extends BaseEntity<Long> implements GrantedAuthority {

	private static final long serialVersionUID = ETConstants.etVersion;

	private Long id;
	private Roles role;

	@Override
	@Id
	@GeneratedValue(generator = "AUTH_GEN", strategy = GenerationType.TABLE)
	@Column(name = "AUTHORITY_ID")
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	@Column(name = "AUTHORITY", length = 50, unique = true)
	@Enumerated(EnumType.STRING)
	public Roles getRole() {
		return this.role;
	}

	public void setRole(final Roles role) {
		this.role = role;
	}

	@Transient
	public String getAuthority() {
		return this.role.name();
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserAuthority)) {
			return false;
		}

		final UserAuthority auth = (UserAuthority) obj;

		return (this.role.equals(auth.role));
	}

	@Override
	public int hashCode() {
		return this.role.hashCode();
	}

	@Override
	public String toString() {
		return this.role.toString();
	}
}
