package com.pk.et.infra.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

/**
 * A Time Provider returning the system time
 */
@Component
public class SystemTimeProvider implements TimeProvider {

	public long getCurrentTime() {
		return System.currentTimeMillis();
	}

	public Date getCurrentDate() {
		return new Date(getCurrentTime());
	}

	public DateTime getCurrentDateTime() {
		return DateUtil.convertDateToDateTime(getCurrentDate());
	}
}
