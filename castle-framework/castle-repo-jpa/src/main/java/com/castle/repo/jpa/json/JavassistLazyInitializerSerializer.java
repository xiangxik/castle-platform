package com.castle.repo.jpa.json;

import java.io.IOException;

import org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * jpa延迟加载对象的序列化器
 * 
 * @作者 孔祥溪
 * @创建时间 2015年10月5日 下午10:23:22
 */
public class JavassistLazyInitializerSerializer extends JsonSerializer<JavassistLazyInitializer> {

	private BeanProperty beanProperty;

	public JavassistLazyInitializerSerializer(BeanProperty beanProperty) {
		this.beanProperty = beanProperty;
	}

	@Override
	public void serialize(JavassistLazyInitializer value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		serializers.findValueSerializer(value.getPersistentClass(), beanProperty).serialize(value.getImplementation(),
				gen, serializers);
	}

}
