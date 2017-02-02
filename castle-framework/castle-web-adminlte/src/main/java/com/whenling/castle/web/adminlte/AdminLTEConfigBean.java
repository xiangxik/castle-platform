package com.whenling.castle.web.adminlte;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AdminLTEConfigBean extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/extjs/app/**").addResourceLocations("classpath:/META-INF/app/")
				.setCachePeriod(60 * 60 * 24 * 30);
	}
}
