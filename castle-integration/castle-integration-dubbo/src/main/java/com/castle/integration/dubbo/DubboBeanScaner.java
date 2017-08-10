package com.castle.integration.dubbo;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.utils.ReflectUtils;
import com.alibaba.dubbo.config.annotation.Service;

public class DubboBeanScaner implements BeanFactoryPostProcessor {

	private String annotationPackage;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (annotationPackage == null || annotationPackage.length() == 0) {
			return;
		}
		if (beanFactory instanceof BeanDefinitionRegistry) {
			try {
				// init scanner
				Class<?> scannerClass = ReflectUtils
						.forName("org.springframework.context.annotation.ClassPathBeanDefinitionScanner");
				Object scanner = scannerClass
						.getConstructor(new Class<?>[] { BeanDefinitionRegistry.class, boolean.class })
						.newInstance(new Object[] { (BeanDefinitionRegistry) beanFactory, true });
				// add filter
				Class<?> filterClass = ReflectUtils
						.forName("org.springframework.core.type.filter.AnnotationTypeFilter");
				Object filter = filterClass.getConstructor(Class.class).newInstance(Service.class);
				Method addIncludeFilter = scannerClass.getMethod("addIncludeFilter",
						ReflectUtils.forName("org.springframework.core.type.filter.TypeFilter"));
				addIncludeFilter.invoke(scanner, filter);
				// scan packages
				String[] packages = Constants.COMMA_SPLIT_PATTERN.split(annotationPackage);
				Method scan = scannerClass.getMethod("scan", new Class<?>[] { String[].class });
				scan.invoke(scanner, new Object[] { packages });
			} catch (Throwable e) {
				// spring 2.0
			}
		}
	}

	public String getPackage() {
		return annotationPackage;
	}

	public void setPackage(String annotationPackage) {
		this.annotationPackage = annotationPackage;
	}

}
