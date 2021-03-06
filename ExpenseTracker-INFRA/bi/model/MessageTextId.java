package com.pk.et.infra.model;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class MessageTextId implements Serializable {

	/**
	 * For the serialization.
	 */
	private static final long serialVersionUID = 1L;

	private String key1;
	private String key2;
	@Enumerated(EnumType.STRING)
	private Locale locale;

	public MessageTextId() {
	}

	public MessageTextId(final String key1, final String key2,
			final Locale locale) {
		this.key1 = key1;
		this.key2 = key2;
		this.locale = locale;
	}

	public String getKey1() {
		return this.key1;
	}

	public void setKey1(final String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return this.key2;
	}

	public void setKey2(final String key2) {
		this.key2 = key2;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public static MessageTextId create() {
		return new MessageTextId();

	}

	public static MessageTextId create(final String key1, final String key2,
			final Locale locale) {
		final MessageTextId id = new MessageTextId();
		id.setKey1(key1);
		id.setKey2(key2);
		id.setLocale(locale);
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.key1, this.key2, this.locale);
	}

	@Override
	public boolean equals(final Object obj) {

		if (!(obj instanceof MessageTextId)) {
			return false;
		}
		final MessageTextId other = (MessageTextId) obj;
		return Objects.equal(this.key1, other.key1)
				&& Objects.equal(this.key2, other.key2)
				&& Objects.equal(this.locale, other.locale);

	}

	@Override
	public String toString() {
		return "MessageTextId [key1=" + this.key1 + ", key2=" + this.key2
				+ ", locale=" + this.locale + "]";
	}
}
