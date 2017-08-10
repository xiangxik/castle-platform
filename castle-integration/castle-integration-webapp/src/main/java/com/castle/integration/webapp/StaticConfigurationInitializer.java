package com.castle.integration.webapp;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.WebApplicationInitializer;

import com.castle.config.StaticConfigSupplier;
import com.castle.core.CastleConstants;

@Order(CastleConstants.order_config)
public class StaticConfigurationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		Resource configResource = new ClassPathResource("config.properties");
		if (configResource.exists()) {
			StaticConfigSupplier.append("config.properties");
		}
	}

}
