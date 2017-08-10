package com.castle.repo.mongo.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MongoBeanSerializer<T> extends JsonSerializer<T> {

	private BeanProperty beanProperty;
	private Class<T> entityClass;

	public MongoBeanSerializer(BeanProperty beanProperty, Class<T> entityClass) {
		this.beanProperty = beanProperty;
		this.entityClass = entityClass;
	}

	@Override
	public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		if (value == null) {
			serializers.defaultSerializeNull(gen);
		} else {
			serializers.findValueSerializer(entityClass, beanProperty).serialize(value, gen, serializers);
		}
	}

}
