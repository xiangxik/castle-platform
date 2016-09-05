package com.whenling.castle.integration.webapp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.WebApplicationInitializer;

import com.whenling.castle.core.StaticConfigSupplier;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class StaticConfigurationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		Resource configResource = new ClassPathResource("config.properties");
		if (configResource.exists()) {
			StaticConfigSupplier.append("config.properties");
		}
	}

}
