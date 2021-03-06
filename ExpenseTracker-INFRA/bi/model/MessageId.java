package com.pk.et.infra.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MessageId implements Serializable {

	/**
	 * For the serialization.
	 */
	private static final long serialVersionUID = 1L;

	public final static String DEFAULT_KEY2_VALUE = "default";

	public final static String CUSTOM_KEY2_VALUE = "custom";

	public final static String REPORT_NAME_KEY2_VALUE = "ReportName";

	public static List<MessageId> createMessageIdsWithDefaultKey2(
			final String... keys1) {
		final List<MessageId> ids = new ArrayList<MessageId>();
		for (final String key1 : keys1) {
			final MessageId id = new MessageId(key1, DEFAULT_KEY2_VALUE);
			ids.add(id);
		}
		return ids;
	}

	public static List<MessageId> createMessageIdsWithGivenKey2(
			final String key2, final List<String> keys1) {
		final List<MessageId> ids = new ArrayList<MessageId>();
		for (final String key1 : keys1) {
			final MessageId id = new MessageId(key1, key2);
			ids.add(id);
		}
		return ids;
	}

	private String key1;
	private String key2;

	public MessageId() {
	}

	public MessageId(final String key1, final String key2) {
		this.key1 = key1;
		this.key2 = key2;
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

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof MessageId)) {
			return false;
		}
		final MessageId castOther = (MessageId) other;
		return Objects.equal(this.key1, castOther.getKey1())
				&& Objects.equal(this.key2, castOther.getKey2());

	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.key1, this.key2);

	}

	public static MessageId create(final String key1, final String key2) {
		return new MessageId(key1, key2);
	}

	@Override
	public String toString() {
		return "MessageId [key1=" + this.key1 + ", key2=" + this.key2 + "]";
	}
}
