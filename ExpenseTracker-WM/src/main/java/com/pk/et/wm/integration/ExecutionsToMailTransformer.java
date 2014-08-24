package com.pk.et.wm.integration;

import org.springframework.batch.core.JobExecution;
import org.springframework.messaging.Message;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.mail.MailHeaders;
import org.springframework.integration.support.MessageBuilder;

public class ExecutionsToMailTransformer {

	@Transformer
	public Message<String> transformExecutionsToMail(
			final JobExecution jobExecution) {
		final String result = "Execution has "
				+ jobExecution.getStatus().toString();
		return MessageBuilder.withPayload(result)
				.setHeader(MailHeaders.TO, "siia.test@yahoo.ca")
				.setHeader(MailHeaders.FROM, "siia.test@yahoo.ca").build();
	}
}
