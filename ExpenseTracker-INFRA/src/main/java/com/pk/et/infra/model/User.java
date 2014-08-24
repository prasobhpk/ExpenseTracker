package com.pk.et.infra.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;
import org.eclipse.persistence.annotations.JoinFetch;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pk.et.infra.util.ETConstants;

@Cache(type = CacheType.SOFT, alwaysRefresh = true, refreshOnlyIfNewer = true)
@Entity
@Table(name = "USERS")
public class User extends BaseEntity<Long> implements UserDetails {

	private static final long serialVersionUID = ETConstants.etVersion;

	private Long id;

	private String username;

	private String password;

	private Name name;

	private Date createdDate;

	// spring security

	private boolean accountNonExpired;

	private boolean enabled;
	private boolean credentialsNonExpired;
	private boolean accountNonLocked;
	private String salt;

	private List<UserAuthority> roles = new ArrayList<UserAuthority>();

	public User() {
		this.createdDate = new Date();
		this.accountNonExpired = true;
		this.enabled = true;
		this.credentialsNonExpired = true;
		this.accountNonLocked = true;
	}

	@Override
	@Id
	@GeneratedValue(generator = "USER_GEN", strategy = GenerationType.TABLE)
	@Column(name = "USER_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@NotEmpty
	@Length(min = 4, max = 9)
	@Column(name = "USER_NAME", nullable = false, length = 50, unique = true)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String userName) {
		this.username = userName;
	}

	@NotEmpty
	@Length(min = 4, max = 100)
	@Column(name = "PASSWORD", nullable = false, length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Valid
	@Embedded
	public Name getName() {
		return this.name;
	}

	public void setName(final Name name) {
		this.name = name;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VALID_FROM")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}

	@Transient
	public Collection<GrantedAuthority> getAuthorities() {
		final Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (final UserAuthority a : getRoles()) {
			authorities.add(a);
		}
		return authorities;
	}

	@ManyToMany
	@JoinFetch
	@JoinTable(name = "USER_AUTHORITIES", joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "AUTHORITY_ID") })
	public List<UserAuthority> getRoles() {
		return this.roles;
	}

	public void setRoles(final List<UserAuthority> roles) {
		this.roles = roles;
	}

	@Column(name = "ACC_NON_EXPIRED")
	public boolean isAccountNonExpired() {

		return this.accountNonExpired;
	}

	public void setAccountNonExpired(final boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Column(name = "ACC_NON_LOCKED")
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	public void setAccountNonLocked(final boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Column(name = "PASSWORD_NON_EXPIRED")
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	public void setCredentialsNonExpired(final boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Column(name = "ACTIVE")
	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "SALT")
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(final String salt) {
		this.salt = salt;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof User))
			return false;

		final User user = (User) obj;

		return (this.id == user.id);
	}

	@Override
	public int hashCode() {
		return (this.username != null ? this.username.hashCode() : 0);
	}

	@Override
	public String toString() {
		return "User [id=" + this.id + ", userName=" + this.username + "]";
	}
}
