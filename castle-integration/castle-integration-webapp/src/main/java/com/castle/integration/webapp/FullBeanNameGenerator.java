package com.castle.integration.webapp;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class FullBeanNameGenerator extends AnnotationBeanNameGenerator {

	@Override
	protected String buildDefaultBeanName(BeanDefinition definition) {
		String beanClassName = definition.getBeanClassName();
		if (StringUtils.endsWith(beanClassName, "Controller")) {
			return beanClassName;
		}
		return super.buildDefaultBeanName(definition);
	}
}
