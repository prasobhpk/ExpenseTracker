package com.pk.et.infra.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.pk.et.infra.util.ETConstants;

//import org.eclipse.persistence.annotations.Customizer;

@Entity
@Table(name = "EXTENSIONVALUE")
// @Customizer(ExtensionValueDescriptorCustomizer.class)
public class ExtensionValue extends BaseEntity<Long> {
	private static final long serialVersionUID = ETConstants.etVersion;
	private Long id;
	private Extension extension;
	private String value;

	@Override
	@Id
	@GeneratedValue(generator = "EXTN_VAL_GEN", strategy = GenerationType.TABLE)
	@Column(name = "EXTN_VAL_ID")
	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY)
	// @MapsId
	@JoinColumn(name = "EXTN_ID")
	public Extension getExtension() {
		return this.extension;
	}

	public void setExtension(final Extension extension) {
		this.extension = extension;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

}
