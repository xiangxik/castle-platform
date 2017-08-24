package com.castle.theme.uikit;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class UikitConfigBean extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/uikit/**").addResourceLocations("classpath:/META-INF/assets/uikit/").setCachePeriod(60 * 60 * 24 * 30);
	}

}
