package com.castle.repo.jpa.converter;

import javax.persistence.AttributeConverter;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;

public class ArrayConverter implements AttributeConverter<Object, String> {

	private static final String separator = "@#@";

	private static final Splitter splitter = Splitter.on(separator);
	private static final Joiner joiner = Joiner.on(separator);

	private AttributeConverter<Object, String> elementConverter;

	public ArrayConverter(AttributeConverter<Object, String> elementConverter) {
		this.elementConverter = elementConverter;
	}

	@Override
	public String convertToDatabaseColumn(Object attribute) {
		if (attribute == null) {
			return null;
		}

		String[] values = null;
		if (attribute.getClass().isArray()) {
			values = new String[((Object[]) attribute).length];
			for (int i = 0; i < ((Object[]) attribute).length; i++) {
				values[i] = elementConverter.convertToDatabaseColumn(((Object[]) attribute)[i]);
			}
		}
		return values == null ? null : joiner.join(values);
	}

	@Override
	public Object convertToEntityAttribute(String dbData) {
		if (Strings.isNullOrEmpty(dbData)) {
			return null;
		}

		String[] stringValues = Iterables.toArray(splitter.split(dbData), String.class);
		return stringValues;
	}

}
