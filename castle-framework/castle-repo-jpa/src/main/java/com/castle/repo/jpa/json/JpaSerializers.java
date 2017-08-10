package com.castle.repo.jpa.json;

import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.util.ClassUtils;

import com.castle.repo.jpa.BaseEntity;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;

/**
 * jpa序列化器
 * 
 * @作者 孔祥溪
 * @创建时间 2015年10月5日 下午10:24:41
 */
public class JpaSerializers extends com.fasterxml.jackson.databind.ser.Serializers.Base {

	private EntityManager entityManager;
	private Set<Class<?>> excluded = new HashSet<>();

	public JpaSerializers(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public JpaSerializers exclude(Class<?> clazz) {
		assert clazz != null;
		excluded.add(clazz);

		return this;
	}

	@Override
	public JsonSerializer<?> findSerializer(SerializationConfig config, JavaType type, BeanDescription beanDesc) {
		if (ClassUtils.isAssignable(JavassistLazyInitializer.class, type.getRawClass())) {
			return new JavassistLazyInitializerSerializer(null);
		} else if (!excluded.contains(type.getRawClass())
				&& ClassUtils.isAssignable(BaseEntity.class, type.getRawClass())) {
			Class<?> entityClass = type.getRawClass();
			if (Proxy.isProxyClass(entityClass) || ClassUtils.isCglibProxyClass(entityClass)) {
				entityClass = entityClass.getSuperclass();
				JpaEntityInformation<?, ? extends Serializable> entityInformation = JpaEntityInformationSupport
						.getEntityInformation(entityClass, entityManager);
				if (entityInformation != null) {
					return new JpaEntitySerializer<>(entityInformation, null);
				}
			}
		}
		return super.findSerializer(config, type, beanDesc);
	}

}
