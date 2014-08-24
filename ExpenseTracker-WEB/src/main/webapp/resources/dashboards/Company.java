package com.exp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XmlRootElement(name = "company")
@XStreamAlias("company")
@JsonIgnoreProperties(value = { "favUsers" })
@JsonAutoDetect
@Entity
@Table(name = "COMPANY", uniqueConstraints = @UniqueConstraint(columnNames = { "COMPANY_UNIQUE_CODE" }))
public class Company {
	@XStreamAlias("id")
	private Long id;
	@XStreamOmitField
	private Long version;
	@XStreamAlias("code")
	private String code;
	@XStreamAlias("uniqCode")
	private String uniqCode;
	@XStreamAlias("name")
	private String name;
	@XStreamAlias("group")
	private String group;
	@XStreamOmitField
	private Set<User> favUsers = new HashSet<User>();

	public Company() {

	}

	public Company(String name, String uniqCode, String code, String group) {
		this.name = name;
		this.uniqCode = uniqCode;
		this.code = code;
		this.group = group;
	}

	@Id
	@GeneratedValue(generator = "id_gen", strategy = GenerationType.TABLE)
	@Column(name = "COMPANY_ID")
	public Long getId() {
		return id;
	}

	@XmlElement
	public void setId(Long id) {
		this.id = id;
	}

	@Version
	@Column(name = "VER_NO")
	public Long getVersion() {
		return version;
	}

	@XmlTransient
	public void setVersion(Long version) {
		this.version = version;
	}

	@Column(name = "COMPANY_CODE")
	public String getCode() {
		return code;
	}

	@XmlElement
	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "COMPANY_UNIQUE_CODE")
	public String getUniqCode() {
		return uniqCode;
	}

	@XmlElement
	public void setUniqCode(String uniqCode) {
		this.uniqCode = uniqCode;
	}

	@Column(name = "COMPANY_NAME")
	public String getName() {
		return name;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "COMPANY_GROUP")
	public String getGroup() {
		return group;
	}

	@XmlElement
	public void setGroup(String group) {
		this.group = group;
	}

	@ManyToMany(mappedBy = "favStocks")
	public Set<User> getFavUsers() {
		return favUsers;
	}

	@XmlTransient
	public void setFavUsers(Set<User> favUsers) {
		this.favUsers = favUsers;
	}

	@Override
	public int hashCode() {
		return (uniqCode != null ? uniqCode.hashCode() : 0);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof Company))
			return false;

		final Company com = (Company) obj;

		return (this.uniqCode == com.uniqCode);
	}

	@Override
	public String toString() {
		return this.uniqCode + "|";
	}

}
