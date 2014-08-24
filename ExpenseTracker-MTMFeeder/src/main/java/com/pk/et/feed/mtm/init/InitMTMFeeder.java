package com.pk.et.feed.mtm.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.pk.et.infra.model.ConfigType;
import com.pk.et.infra.model.ConfigurationItem;
import com.pk.et.infra.model.ValueType;
import com.pk.et.infra.service.IConfigurationItemService;
import com.pk.et.infra.util.ETConstants;

@Component
public class InitMTMFeeder {

	@Autowired(required = true)
	@Qualifier("configurationItemService")
	IConfigurationItemService configurationItemService;

	@PostConstruct
	public void init() {
		if (this.configurationItemService
				.findByKey(ETConstants.LAST_FEED_DATE_KEY) == null) {
			final ConfigurationItem item = new ConfigurationItem(
					ETConstants.LAST_FEED_DATE_KEY, ValueType.SINGLE_VALUED,
					false, ETConstants.FEED_START_DATE, ConfigType.USER);
			this.configurationItemService.save(item);
		}
	}
}
