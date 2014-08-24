package com.pk.et.feed.mtm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pk.et.infra.jms.Job;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "dev")
@ContextConfiguration({ "classpath:config/spring/ExpenseTracker-INFRA-jms.xml",
		"classpath:config/spring/ExpenseTracker-MTMFeeder-jms.xml" })
public class ChannelAdapterDemoTest {
	@Autowired
	@Qualifier("feederMtmJobChannel")
	private MessageChannel feederMtmJobChannel;

	@Test
	public void testChannelAdapterDemo() throws InterruptedException {
		this.feederMtmJobChannel.send(MessageBuilder
				.withPayload(Job.FEEDER_MTM).build());
	}
}
