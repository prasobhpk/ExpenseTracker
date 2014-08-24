package com.pk.et.infra.util;

import java.util.Date;

import org.joda.time.DateTime;

/**
 * Time Provider tells the time. It could be the time of the system in
 * production environment or a fake time for demonstration/test purpose
 */
public interface TimeProvider {

	/**
	 * Returns the current time in milliseconds.
	 */
	long getCurrentTime();

	/**
	 * Return the current date
	 */
	Date getCurrentDate();

	DateTime getCurrentDateTime();
}