package com.castle.web;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@ServletSupport
public class WebSpringContext implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	private static String contextPath;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		WebSpringContext.applicationContext = applicationContext;

		WebSpringContext.contextPath = applicationContext.getBean(ServletContext.class).getContextPath();
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static String getContextPath() {
		return contextPath;
	}

	public static void autowireBean(Object existingBean) {
		getApplicationContext().getAutowireCapableBeanFactory().autowireBean(existingBean);
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> type) {
		return applicationContext.getBean(type);
	}

	public static <T> T getBean(String name, Class<T> type) {
		return applicationContext.getBean(name, type);
	}

	public static <T extends Annotation, F> List<F> findAnnotatedBeans(Class<T> annotationType, Class<F> elementType) {
		List<F> beans = new ArrayList<>();
		for (String name : BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, Object.class)) {
			if (applicationContext.findAnnotationOnBean(name, annotationType) != null) {
				beans.add(applicationContext.getBean(name, elementType));
			}
		}

		return beans;
	}

	public static <T> List<T> findBeansByType(Class<T> beanType) {
		List<T> beans = new ArrayList<>();
		beans.addAll(applicationContext.getBeansOfType(beanType).values());

		return beans;
	}
}