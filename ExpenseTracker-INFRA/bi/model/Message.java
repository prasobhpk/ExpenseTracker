package com.pk.et.infra.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

@Entity
@Table(name = "MESSAGE")
public class Message extends BaseEntity<MessageId> {

	/**
	 * A request for find message ids by contexts.
	 */
	public static final String FIND_MESSAGE_IDS_BY_CONTEXTS = "FIND_MESSAGE_IDS_BY_CONTEXTS";

	public static final String FIND_MESSAGES_BY_IDS = "FIND_MESSAGES_BY_IDS";

	private MessageId id;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "TEMPLATE_MESSAGES", joinColumns = {
			@JoinColumn(name = "KEY1", nullable = false, updatable = false),
			@JoinColumn(name = "KEY2", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "TEMPLATE", updatable = false) })
	private List<Template> templates;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "message", cascade = {
			CascadeType.PERSIST, CascadeType.REMOVE })
	private List<MessageText> messageTexts;

	public Message() {
	}

	public Message(final MessageId id) {
		this.id = id;
	}

	@Override
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "key1", column = @Column(name = "KEY1", nullable = false)),
			@AttributeOverride(name = "key2", column = @Column(name = "KEY2", nullable = false)) })
	public MessageId getId() {
		return this.id;
	}

	@Override
	public void setId(final MessageId id) {
		this.id = id;
	}

	public List<Template> getTemplates() {
		return this.templates;
	}

	public void setTemplates(final List<Template> customTemplates) {
		this.templates = customTemplates;
	}

	/**
	 * Create Message.
	 * 
	 * @return A new Message.
	 */
	public static Message create() {
		final Message message = new Message();
		message.templates = new ArrayList<Template>();
		return message;
	}

	public static Message create(final MessageId id) {
		final Message message = new Message();
		message.id = id;
		message.templates = new ArrayList<Template>();
		return message;
	}

	public static Message create(final String key1) {
		return Message.create(key1, MessageId.DEFAULT_KEY2_VALUE);
	}

	public static Message create(final String key1, final String key2) {
		final MessageId messageId = MessageId.create(key1, key2);
		return Message.create(messageId);
	}

	@Override
	public String toString() {
		return "Message [id=" + this.id + ", customTemplates=" + this.templates
				+ "]";
	}

	public void addTemplate(final Template template) {
		if (!this.templates.contains(template)) {
			this.templates.add(template);
		}
	}

	public void removeTemplate(final Template template) {
		this.templates.remove(template);
	}

	public void setMessageTexts(final List<MessageText> messageTexts) {
		this.messageTexts = messageTexts;
	}

	public List<MessageText> getMessageTexts() {
		return this.messageTexts;
	}

	public MessageText getTextByLocale(final Locale locale) {
		for (final MessageText messageText : this.messageTexts) {
			if (messageText.getLocale().equals(locale)) {
				return messageText;
			}
		}
		return null;
	}

}
