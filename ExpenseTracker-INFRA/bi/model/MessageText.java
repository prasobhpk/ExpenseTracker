package com.pk.et.infra.model;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGE_TEXT")
@NamedQueries({
		@NamedQuery(name = MessageText.MESSAGE_TEXT_FIND_ALL_BY_LOCALE_AND_MESSAGE_IDS, query = "select txt from MessageText txt join fetch txt.message m where txt.locale = :locale and txt.message.id in(:idList) order by txt.id.key1"),
		@NamedQuery(name = MessageText.MESSAGE_TEXT_FIND_ALL_BY_LOCALE_AND_CONTEXT, query = "select txt from MessageText txt join fetch txt.message m where txt.locale = :locale and txt.message.id.key2 = :key2") })
public class MessageText extends BaseEntity<MessageTextId> {

	public static final String MESSAGE_TEXT_FIND_ALL_BY_LOCALE_AND_MESSAGE_IDS = "MESSAGE_TEXT_FIND_ALL_BY_LOCALE_AND_MESSAGE_IDS";
	public static final String MESSAGE_TEXT_FIND_ALL_BY_LOCALE_AND_CONTEXT = "MESSAGE_TEXT_FIND_ALL_BY_LOCALE_AND_CONTEXT";

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "key1", column = @Column(name = "KEY1", nullable = false)),
			@AttributeOverride(name = "key2", column = @Column(name = "KEY2", nullable = false)),
			@AttributeOverride(name = "locale", column = @Column(name = "LOCALE", nullable = false, length = 1020)) })
	private MessageTextId id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.REMOVE })
	@JoinColumns({
			@JoinColumn(name = "KEY1", referencedColumnName = "KEY1", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "KEY2", referencedColumnName = "KEY2", nullable = false, insertable = false, updatable = false) })
	private Message message;

	@Enumerated(EnumType.STRING)
	@Column(name = "LOCALE", nullable = false, insertable = false, updatable = false)
	private Locale locale;

	@Column(name = "VALUE", length = 4000)
	private String value;

	@Column(name = "LARGE_VALUE")
	@Lob
	private String largeValue;

	public MessageText() {
	}

	@Override
	public MessageTextId getId() {
		return this.id;
	}

	@Override
	public void setId(final MessageTextId id) {
		this.id = id;
	}

	public Message getMessage() {
		return this.message;
	}

	public void setMessage(final Message message) {
		this.message = message;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public void setLocale(final Locale locale) {
		this.locale = locale;
	}

	public String getValue() {
		return this.largeValue != null ? this.largeValue : this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	public String getLargeValue() {
		return this.largeValue;
	}

	public void setLargeValue(final String largeValue) {
		this.largeValue = largeValue;
	}

	/**
	 * Create MessageText.
	 * 
	 * @return A new MessageText.
	 */
	public static MessageText create() {
		return new MessageText();
	}

	public static MessageText create(final Message message,
			final Locale locale, final String value) {
		final MessageText messageText = new MessageText();
		final MessageTextId id = new MessageTextId(message.getId().getKey1(),
				message.getId().getKey2(), locale);
		messageText.setId(id);
		messageText.setLocale(locale);
		messageText.setMessage(message);
		messageText.setValue(value);
		return messageText;
	}

	@Override
	public String toString() {
		return "MessageText [id=" + this.id + ", message=" + this.message
				+ ", locale=" + this.locale + ", value=" + this.value
				+ ", largeValue=" + this.largeValue + "]";
	}

	/**
	 * Find message texts for a given locale and a message ids list.
	 * 
	 * @param locale
	 *            the locale to consider.
	 * @param messageIds
	 *            the message ids list.
	 * 
	 * @return the found message texts.
	 */
	@SuppressWarnings("unchecked")
	public static List<MessageText> findMessageTextsByLocaleAndMessageIds(
			final EntityManager entityManager, final Locale locale,
			final List<MessageId> messageIds) {
		final Query query = entityManager.createNamedQuery(
				MESSAGE_TEXT_FIND_ALL_BY_LOCALE_AND_MESSAGE_IDS).setParameter(
				"locale", locale);
		final List<MessageText> messageTexts = query(entityManager, messageIds,
				query);
		return messageTexts;
	}

	/**
	 * Find message texts for a given locale and a context.
	 * 
	 * @param locale
	 *            the locale to consider.
	 * @param key2
	 *            the context to consider
	 * 
	 * 
	 * @return the found message texts.
	 */
	@SuppressWarnings("unchecked")
	public static List<MessageText> findMessageTextsByLocaleAndContext(
			final EntityManager entityManager, final Locale locale,
			final String key2) {
		final List<MessageText> messageTexts = entityManager
				.createNamedQuery(
						MessageText.MESSAGE_TEXT_FIND_ALL_BY_LOCALE_AND_CONTEXT)
				.setParameter("key2", key2).setParameter("locale", locale)
				.getResultList();
		return messageTexts;
	}

}
