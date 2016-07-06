package com.whenling.castle.template.groovy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.groovy.GroovyMarkupConfigurer;
import org.springframework.web.servlet.view.groovy.GroovyMarkupViewResolver;

@Configuration
public class GroovyConfigBean {

	@Bean
	public GroovyMarkupViewResolver groovyMarkupViewResolver() {
		GroovyMarkupViewResolver resolver = new GroovyMarkupViewResolver();
		resolver.setContentType("text/html;charset=UTF-8");
		resolver.setSuffix(".tpl");
		resolver.setCache(false);
		return resolver;
	}

	@Bean
	public GroovyMarkupConfigurer groovyMarkupConfigurer() {
		GroovyMarkupConfigurer configurer = new GroovyMarkupConfigurer();
		configurer.setAutoIndent(true);
		configurer.setAutoNewLine(true);
		configurer.setBaseTemplateClass(CastleBaseTemplate.class);
		configurer.setCacheTemplates(false);
		configurer.setDeclarationEncoding("UTF-8");
		return configurer;
	}
}
