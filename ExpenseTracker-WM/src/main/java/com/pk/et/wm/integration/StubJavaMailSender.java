package com.pk.et.wm.integration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class StubJavaMailSender implements JavaMailSender {

	private final MimeMessage uniqueMessage;

	private final List<MimeMessage> sentMimeMessages = new ArrayList<MimeMessage>();

	private final List<SimpleMailMessage> sentSimpleMailMessages = new ArrayList<SimpleMailMessage>();

	public StubJavaMailSender(final MimeMessage uniqueMessage) {
		this.uniqueMessage = uniqueMessage;
	}

	public List<MimeMessage> getSentMimeMessages() {
		return this.sentMimeMessages;
	}

	public List<SimpleMailMessage> getSentSimpleMailMessages() {
		return this.sentSimpleMailMessages;
	}

	public MimeMessage createMimeMessage() {
		return this.uniqueMessage;
	}

	public MimeMessage createMimeMessage(final InputStream contentStream)
			throws MailException {
		return this.uniqueMessage;
	}

	public void send(final MimeMessage mimeMessage) throws MailException {
		this.sentMimeMessages.add(mimeMessage);
	}

	public void send(final MimeMessage[] mimeMessages) throws MailException {
		this.sentMimeMessages.addAll(Arrays.asList(mimeMessages));
	}

	public void send(final MimeMessagePreparator mimeMessagePreparator)
			throws MailException {
		throw new UnsupportedOperationException(
				"MimeMessagePreparator not supported");
	}

	public void send(final MimeMessagePreparator[] mimeMessagePreparators)
			throws MailException {
		throw new UnsupportedOperationException(
				"MimeMessagePreparator not supported");
	}

	public void send(final SimpleMailMessage simpleMessage)
			throws MailException {
		this.sentSimpleMailMessages.add(simpleMessage);
	}

	public void send(final SimpleMailMessage[] simpleMessages)
			throws MailException {
		this.sentSimpleMailMessages.addAll(Arrays.asList(simpleMessages));
	}

	public void reset() {
		this.sentMimeMessages.clear();
		this.sentSimpleMailMessages.clear();
	}
}
