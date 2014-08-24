package com.pk.et.feed.mtm.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pk.et.infra.service.IConfigurationItemService;

public class NseMtmDownloader {
	private static final Logger LOG = LoggerFactory
			.getLogger(NseMtmDownloaderTask.class);
	private static final String[] months = new String[] { "JAN", "FEB", "MAR",
			"APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };
	@Autowired(required = true)
	@Qualifier("configurationItemService")
	IConfigurationItemService configurationItemService;
}
