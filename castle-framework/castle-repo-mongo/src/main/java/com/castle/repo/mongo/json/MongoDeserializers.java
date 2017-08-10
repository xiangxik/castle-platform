package com.castle.repo.mongo.json;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.data.mongodb.core.MongoOperations;

import com.castle.repo.mongo.BaseDoc;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;

public class MongoDeserializers extends com.fasterxml.jackson.databind.deser.Deserializers.Base {

	private MongoOperations mongoOperations;
	private Set<Class<?>> excluded = new HashSet<>();

	public MongoDeserializers(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	public MongoDeserializers exclude(Class<?> clazz) {
		assert clazz != null;
		excluded.add(clazz);

		return this;

	}

	@Override
	public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc)
			throws JsonMappingException {
		if (!excluded.contains(type.getRawClass()) && ClassUtils.isAssignable(type.getRawClass(), BaseDoc.class)) {
			return new MongoBeanDeserializer(mongoOperations, type);
		}
		return super.findBeanDeserializer(type, config, beanDesc);
	}
}
