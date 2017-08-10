package com.castle.repo.jpa.converter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.castle.core.SpringContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Strings;

@Converter
public class ListMapToJsonConverter implements AttributeConverter<Object, String> {

	private ObjectMapper objectMapper;

	@Override
	public String convertToDatabaseColumn(Object attribute) {
		if (attribute == null) {
			return null;
		}
		try {
			return getObjectMapper().writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object convertToEntityAttribute(String dbData) {
		if (Strings.isNullOrEmpty(dbData)) {
			return null;
		}
		try {
			return getObjectMapper().readValue(dbData, TypeFactory.defaultInstance().constructCollectionType(List.class,
					TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class)));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = SpringContext.getBean(ObjectMapper.class);
		}
		return objectMapper;
	}

}