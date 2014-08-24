package com.pk.et.infra.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class ETMapEntryType {
	private String key;
	private Object value;

	public ETMapEntryType() {

	}

	public ETMapEntryType(String key,Object value) {
		this.key=key;
		this.value=value;
	}

	@XmlAttribute
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@XmlValue
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
