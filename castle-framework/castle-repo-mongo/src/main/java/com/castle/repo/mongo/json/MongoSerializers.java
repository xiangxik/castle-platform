package com.castle.repo.mongo.json;

import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.ClassUtils;

import com.castle.repo.mongo.BaseDoc;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;

public class MongoSerializers extends com.fasterxml.jackson.databind.ser.Serializers.Base {

	private Set<Class<?>> excluded = new HashSet<>();

	public MongoSerializers exclude(Class<?> clazz) {
		assert clazz != null;
		excluded.add(clazz);

		return this;
	}

	@Override
	public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
		if (!excluded.contains(type.getRawClass()) && ClassUtils.isAssignable(BaseDoc.class, type.getRawClass())) {
			Class<?> entityClass = type.getRawClass();
			if (Proxy.isProxyClass(entityClass) || ClassUtils.isCglibProxyClass(entityClass)) {
				entityClass = entityClass.getSuperclass();
				return new MongoBeanSerializer<>(null, entityClass);
			}
		}
		return super.findSerializer(config, type, beanDesc);
	}
}
