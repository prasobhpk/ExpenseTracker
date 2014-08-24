package com.pk.et.infra.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {
	public int calculateAge(final int year, final int month, final int day) {
		final Calendar dateOfBirth = new GregorianCalendar(year, month, day);

		// Create a calendar object with today's date
		final Calendar today = Calendar.getInstance();

		// Get age based on year
		int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

		// Add the tentative age to the date of birth to get this year's
		// birthday
		dateOfBirth.add(Calendar.YEAR, age);

		// If this year's birthday has not happened yet, subtract one from age
		if (today.before(dateOfBirth)) {
			age--;
		}
		return age;
	}

	public static String getMonthName(final int month) {
		final String[] monthName = { "January", "February", "March", "April",
				"May", "June", "July", "August", "September", "October",
				"November", "December" };

		// Calendar cal = Calendar.getInstance();
		// cal.setTime(date);
		// String month = monthName[cal.get(Calendar.MONTH)];
		final String name = monthName[month];
		return name;

	}

	public static int getDayOfMonth(final Date date) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DATE);
	}

	public static int getYear(final Date date) {
		final Calendar cal = Calendar.getInstance();
		if (date != null) {
			cal.setTime(date);
		}
		return cal.get(Calendar.YEAR);
	}

	public static int compare(final Date one, final Date two) {
		return two.compareTo(one);
	}

	public static int daysBetween(final Date d1, final Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static String getDate(final Date date, final String format) {
		final DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static Date getDate(final String date, final String format) {
		final DateFormat dateFormat = new SimpleDateFormat(format);
		try {
			return dateFormat.parse(date);
		} catch (final ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String getTime(final Date date) {
		final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.format(date);
	}

	private static final int NB_HOURS_IN_A_DAY = 24;

	protected static final String PARIS_ZONE_ID = "Europe/Paris";

	/**
	 * A constant for Paris time zone.
	 */
	public static final DateTimeZone PARIS_TIME_ZONE = DateTimeZone
			.forID(PARIS_ZONE_ID);

	public static final String SDF_DATE_TIME_FORMAT = "dd-MMM-yyyy";

	/**
	 * Format a local date to a string with "dd-MMM-yyyy" format.
	 * 
	 * @param localDate
	 *            the date to format.
	 * @return the formatted date (a string).
	 */
	public static String localDateToStringWithEnglishLocale(
			final LocalDate localDate) {
		return localDateToStringWithPatternAndLocale(localDate,
				SDF_DATE_TIME_FORMAT, Locale.ENGLISH);
	}

	/**
	 * Format a local date to a string with a given format.
	 * 
	 * @param localDate
	 *            the date to format.
	 * @param pattern
	 *            the pattern to consider.
	 * @return the formatted date (a string).
	 */
	public static String localDateToString(final LocalDate localDate,
			final String pattern) {
		if (localDate == null) {
			return null;
		} else {
			return localDate.toString(DateTimeFormat.forPattern(pattern));
		}
	}

	/**
	 * Format a local date to a string with the given date format and locale.
	 * 
	 * @param localDate
	 *            the date to format.
	 * @param dateFormat
	 *            the date format to consider.
	 * @param locale
	 *            the locale to consider.
	 * @return the formatted date (a string).
	 */
	public static String localDateToStringWithPatternAndLocale(
			final LocalDate localDate, final String dateFormat,
			final Locale locale) {
		return localDate.toString(DateTimeFormat.forPattern(dateFormat)
				.withLocale(locale));
	}

	/**
	 * Convert a date to a date time.
	 * 
	 * @param date
	 *            the date to convert.
	 * @return a date time.
	 */
	public static DateTime convertDateToDateTime(final Date date) {
		return date == null ? null : new DateTime(date.getTime());
	}

	/**
	 * Convert a date to a date time.
	 * 
	 * @param date
	 *            the date to convert.
	 * @return a date time.
	 */
	public static DateTime convertDateToDateTime(final Date date,
			final DateTimeZone dateTimeZone) {
		return date == null ? null : new DateTime(date.getTime(), dateTimeZone);
	}

	/**
	 * Convert a date to a date time.
	 * 
	 * @param date
	 *            the date to convert.
	 * @param dateTimeZone
	 *            the time zone
	 * @return a date time.
	 */
	public static DateTime convertDateToDateTimeAtTimeZone(final Date date,
			final DateTimeZone dateTimeZone) {
		return date == null ? null : new DateTime(date.getTime(), dateTimeZone);
	}

	/**
	 * Convert a date time to a date.
	 * 
	 * @param dateTime
	 *            the date time to convert.
	 * @return a date.
	 */
	public static Date convertDateTimeToDate(final DateTime dateTime) {
		return dateTime == null ? null : dateTime.toDate();
	}

	/**
	 * Returns a new formatter that will use the Paris zone
	 * 
	 * @param formatter
	 * @return
	 */
	public static DateTimeFormatter getFormatterWithParisZone(
			final DateTimeFormatter formatter) {
		return formatter.withZone(PARIS_TIME_ZONE);
	}

	/**
	 * Given the formatter, parse a date time using the Paris zone and returns
	 * the local date
	 * 
	 * @param formatter
	 * @param dateString
	 * @return
	 */
	public static LocalDate parseDateTimeUsingParisZoneToLocalDate(
			final DateTimeFormatter formatter, final String dateString) {
		return getFormatterWithParisZone(formatter).parseDateTime(dateString)
				.toLocalDate();
	}

	/**
	 * Convert a local date to a string using Paris time zone.
	 * 
	 * @param formatter
	 *            the formatter to use.
	 * @param date
	 *            the local date to convert.
	 * @return a string.
	 */
	public static String printLocalDateUsingParisZone(
			final DateTimeFormatter formatter, final LocalDate date) {
		return getFormatterWithParisZone(formatter).print(date);
	}

	/**
	 * Convert a date in the Paris time zone to a local date.
	 * 
	 * @param date
	 *            the date to convert.
	 * @return a local date.
	 */
	public static LocalDate convertDateAtParisZoneToLocalDate(final Date date) {
		if (date != null) {
			return new LocalDate(date.getTime(), PARIS_TIME_ZONE);
		} else {
			return null;
		}
	}

	/**
	 * Convert a date time in the Paris time zone to a local date.
	 * 
	 * @param dateTime
	 *            the date time to convert.
	 * @return a local date.
	 */
	public static LocalDate convertDateTimeAtParisZoneToLocalDate(
			final DateTime dateTime) {
		return new LocalDate(dateTime.getMillis(), PARIS_TIME_ZONE);
	}

	public static LocalDate convertDateTimeToLocalDate(final DateTime dateTime,
			final DateTimeZone dateTimeZone) {
		return new LocalDate(dateTime, dateTimeZone);
	}

	public static DateTime convertLocalDateToDateTime(
			final LocalDate localDate, final DateTimeZone dateTimeZone,
			final int hoursOfDay, final int minutesOfDay) {
		if (localDate == null) {
			return null;
		}
		final int nbDayToAdd = hoursOfDay / NB_HOURS_IN_A_DAY;
		final LocalDate localDateUpdated = localDate.plusDays(nbDayToAdd);
		final int nbHoursToAdd = hoursOfDay % NB_HOURS_IN_A_DAY;
		final LocalTime localTime = new LocalTime(nbHoursToAdd, minutesOfDay);
		return localDateUpdated.toDateTime(localTime, dateTimeZone);
	}

	public static DateTime convertLocalDateToDateTimeParis(
			final LocalDate localDate, final int hoursOfDay,
			final int minutesOfDay) {
		return convertLocalDateToDateTime(localDate, PARIS_TIME_ZONE,
				hoursOfDay, minutesOfDay);
	}

	/**
	 * Convert the local date to a java.util.Date at midday (12:00) at UTC
	 * timezone. In doing that, the day will be represented identically in all
	 * time zones with an offset <= +- 12 from UTC time zone.
	 * 
	 * WARNING : You must only used this method to pass a LocalDate outside the
	 * application (e.g : database or reports).
	 * 
	 * @param localDate
	 * @return
	 */
	public static Date convertLocalDateToDateAtUTCMidday(
			final LocalDate localDate) {
		if (localDate == null) {
			return null;
		}
		DateTime date = localDate.toDateTimeAtStartOfDay(DateTimeZone.UTC);
		date = date.plusHours(12);
		return date.toDate();
	}

	/**
	 * Convert a org.joda.time.LocalDate to a java.sql.Timestamp at midnight
	 * Paris time
	 * 
	 * @param localDate
	 * @return
	 */
	public static java.sql.Timestamp convertLocalDateToSQLTimestampAtMidnightParisTime(
			final LocalDate localDate) {
		java.sql.Timestamp value = null;
		if (localDate != null) {
			final DateMidnight date = localDate.toDateMidnight(PARIS_TIME_ZONE);
			value = new java.sql.Timestamp(date.getMillis());
		}
		return value;
	}

	public static java.sql.Timestamp convertDateTimeToSQLTimestamp(
			final DateTime dateTime) {
		java.sql.Timestamp value = null;
		if (dateTime != null) {
			value = new java.sql.Timestamp(dateTime.getMillis());
		}
		return value;
	}

	public static String universalFormatOfTime(final String dateFormat,
			final DateTime dateTime) {
		final DateTimeFormatter formatter = DateTimeFormat
				.forPattern(dateFormat);
		return DateUtil.getFormatterWithParisZone(formatter).print(dateTime);
	}

	public static DateTime todayEarliest(final TimeProvider timeProvider) {
		final DateTime currentDateTime = timeProvider.getCurrentDateTime();
		final DateTime todayEarliest = currentDateTime.withHourOfDay(0)
				.withMinuteOfHour(0).withSecondOfMinute(0)
				.withMillisOfSecond(0);
		return todayEarliest;
	}

	public static DateTime todayLatest(final TimeProvider timeProvider) {
		final DateTime currentDateTime = timeProvider.getCurrentDateTime();
		final DateTime todayLatest = currentDateTime.withHourOfDay(23)
				.withMinuteOfHour(59).withSecondOfMinute(59)
				.withMillisOfSecond(999);
		return todayLatest;
	}

	public static XMLGregorianCalendar nowGregorianCalendar(
			final TimeProvider timeProvider) {
		XMLGregorianCalendar nowXMLCalendar = null;
		try {
			final java.util.TimeZone parisTimeZone = java.util.TimeZone
					.getTimeZone(PARIS_ZONE_ID);
			final Locale frenchLocale = Locale.FRENCH;
			final GregorianCalendar nowCalendar = new GregorianCalendar(
					parisTimeZone, frenchLocale);
			nowCalendar.setTime(timeProvider.getCurrentDate());
			nowXMLCalendar = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(nowCalendar);
		} catch (final DatatypeConfigurationException exc) {
			throw new ETTechnicalException(exc);
		}
		return nowXMLCalendar;
	}

	/**
	 * Convert a xmlGregorianCalendar to a LocalDate.
	 * 
	 * @param calendar
	 *            the calendar to convert
	 * @return the LocalDate converted.
	 */
	public static LocalDate toLocalDate(final XMLGregorianCalendar calendar) {
		if (calendar != null) {
			return new LocalDate(calendar.getYear(), calendar.getMonth(),
					calendar.getDay());
		} else {
			return null;
		}
	}

	public static DateTime toDateTime(final XMLGregorianCalendar calendar) {
		if (calendar != null) {
			final GregorianCalendar gregorianCalendar = calendar
					.toGregorianCalendar();
			gregorianCalendar.get(GregorianCalendar.YEAR);
			return new DateTime(gregorianCalendar.get(GregorianCalendar.YEAR),
					gregorianCalendar.get(GregorianCalendar.MONTH) + 1,
					gregorianCalendar.get(GregorianCalendar.DAY_OF_MONTH),
					gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY),
					gregorianCalendar.get(GregorianCalendar.MINUTE),
					gregorianCalendar.get(GregorianCalendar.SECOND),
					gregorianCalendar.get(GregorianCalendar.MILLISECOND));
		} else {
			return null;
		}
	}

	public static XMLGregorianCalendar localDateToXmlDate(
			final LocalDate localDate) {
		if (localDate == null) {
			return null;
		} else {
			final Date date = convertLocalDateToDateAtUTCMidday(localDate);
			return dateToXmlDate(date);
		}
	}

	/**
	 * Convert a Date to a XmlGregorianCalendar.
	 * 
	 * @param date
	 *            The date to convert.
	 * @return The calendar converted.
	 */
	public static XMLGregorianCalendar dateToXmlDate(final Date date) {
		if (date != null) {
			final Calendar vCal = Calendar.getInstance();
			vCal.setTime(date);
			XMLGregorianCalendar xmlDate = null;
			try {
				final DatatypeFactory df = DatatypeFactory.newInstance();
				xmlDate = df.newXMLGregorianCalendar((GregorianCalendar) vCal);
			} catch (final DatatypeConfigurationException exc) {
				throw new ETTechnicalException(exc);
			}
			return xmlDate;
		} else {
			return null;
		}
	}

	/**
	 * Retrieve the current date.
	 * 
	 * @param timeProvider
	 *            the time provider to get the current date.
	 * @return the current date.
	 */
	public static LocalDate getCurrentDate(final TimeProvider timeProvider) {
		return convertDateAtParisZoneToLocalDate(timeProvider.getCurrentDate());
	}

	public static LocalDate convertSqlDateToLocalDate(
			final java.sql.Date sqlDate) {
		if (sqlDate == null) {
			return null;
		}
		return new LocalDate(sqlDate.getTime());
	}

	/**
	 * Returns the maximum possible day number in a specific month of a specific
	 * year.
	 * 
	 * @param year
	 *            The year of the date
	 * @param monthOfYear
	 *            The month of the date
	 * @return The maximum day number for the month
	 */
	public static int maxDayOfMonthInYear(final int year, final int monthOfYear) {
		return new LocalDate(year, monthOfYear, 14).dayOfMonth()
				.getMaximumValue();
	}

	/**
	 * Compute is a {@link LocalDate} is before another one. Manage null values.
	 * 
	 * @param date1
	 *            the first date to compare.
	 * @param date2
	 *            the second date to compare.
	 * @return true if date1<date2.
	 */
	public static boolean isBefore(final LocalDate date1, final LocalDate date2) {
		if (date1 != null && date2 != null) {
			return date1.isBefore(date2);
		} else if (date1 == null && date2 != null) {
			return true;
		} else if (date1 != null && date2 == null) {
			return false;
		} else {
			return false;
		}
	}

}
