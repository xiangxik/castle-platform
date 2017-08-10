package com.castle.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ServletSupport
public class WebConfigBean extends WebMvcConfigurerAdapter {

	@Bean
	public MultipartResolver multipartResolver() {
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
		resolver.setResolveLazily(true);
		return resolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/", "classpath:/META-INF/assets/").setCachePeriod(60 * 60 * 24 * 30);
		registry.addResourceHandler("/bower_components/**").addResourceLocations("classpath:/META-INF/bower_components/")
				.setCachePeriod(60 * 60 * 24 * 30);
		registry.addResourceHandler("/upload/**").addResourceLocations("/upload/");
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

}
