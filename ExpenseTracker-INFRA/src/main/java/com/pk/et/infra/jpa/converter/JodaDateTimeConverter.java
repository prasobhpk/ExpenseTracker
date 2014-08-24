package com.pk.et.infra.jpa.converter;

import java.sql.Timestamp;

import javax.persistence.AttributeConverter;

import org.joda.time.DateTime;

public class JodaDateTimeConverter implements
		AttributeConverter<DateTime, Timestamp> {

	private static final long serialVersionUID = 1L;

	public Timestamp convertToDatabaseColumn(final DateTime attribute) {
		if (attribute == null) {
			return null;
		} else {
			return new Timestamp(attribute.getMillis());
		}
	}

	public DateTime convertToEntityAttribute(final Timestamp dbData) {
		if (dbData == null) {
			return null;
		} else {
			return new DateTime(dbData.getTime());
		}
	}

	// public Object convertDataValueToObjectValue(final Object dataValue,
	// final Session session) {
	// return dataValue == null ? null : new DateTime(dataValue);
	// }
	//
	// public Object convertObjectValueToDataValue(final Object objectValue,
	// final Session session) {
	// return objectValue == null ? null : new Timestamp(
	// ((DateTime) objectValue).getMillis());
	// }
	//
	// public void initialize(final DatabaseMapping mapping, final Session
	// session) {
	// ((AbstractDirectMapping) mapping)
	// .setFieldClassification(java.sql.Timestamp.class);
	// }
	//
	// public boolean isMutable() {
	// return false;
	// }

}