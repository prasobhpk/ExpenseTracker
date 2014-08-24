package com.pk.et.wm.jaxb.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeAdapter extends XmlAdapter<String, DateTime> {
	private static final String DATE_FORMAT_PATTERN = "dd MMM,HH:mm:ss";
	private static final DateTimeFormatter fmt = DateTimeFormat
			.forPattern(DATE_FORMAT_PATTERN);

	@Override
	public String marshal(final DateTime date) throws Exception {
		return fmt.print(date);
	}

	@Override
	public DateTime unmarshal(final String dateString) throws Exception {
		return fmt.parseDateTime(dateString)
				.withYear(LocalDate.now().getYear());
	}

}
