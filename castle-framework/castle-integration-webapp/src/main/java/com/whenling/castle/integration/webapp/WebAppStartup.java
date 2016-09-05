package com.whenling.castle.integration.webapp;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.whenling.castle.core.CastleConstants;

@Order(Ordered.LOWEST_PRECEDENCE)
public class WebAppStartup extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfigBean.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { ServletConfigBean.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] { new CharacterEncodingFilter(CastleConstants.characterEncoding, true) };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		super.customizeRegistration(registration);

		registration.setMultipartConfig(new MultipartConfigElement("/tmp", 1024 * 1024 * 512, 1024 * 1024 * 1024, 1024 * 512));
	}
}
