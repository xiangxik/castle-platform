package com.castle.repo.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringArrayConverter implements AttributeConverter<Object, String> {

	private static final AttributeConverter<Object, String> target = new ArrayConverter(new StringConverter());

	@Override
	public String convertToDatabaseColumn(Object attribute) {
		return target.convertToDatabaseColumn(attribute);
	}

	@Override
	public Object convertToEntityAttribute(String dbData) {
		return target.convertToEntityAttribute(dbData);
	}

	private static class StringConverter implements AttributeConverter<Object, String> {

		@Override
		public String convertToDatabaseColumn(Object attribute) {
			if (attribute == null)
				return null;
			return attribute.toString();
		}

		@Override
		public Object convertToEntityAttribute(String dbData) {
			return dbData;
		}

	}

}
