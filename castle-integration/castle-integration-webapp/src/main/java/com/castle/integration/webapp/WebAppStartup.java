package com.castle.integration.webapp;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.apache.commons.configuration2.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.castle.config.StaticConfigSupplier;
import com.castle.core.CastleConstants;

@Order(CastleConstants.order_webapp)
public class WebAppStartup extends AbstractAnnotationConfigDispatcherServletInitializer {

	private Class<?>[] rootConfigClasses;
	private Class<?>[] servletConfigClasses;

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		final Configuration config = StaticConfigSupplier.getConfiguration();
		String[] rootConfigBeanClassNames = config.getStringArray("integration.root_config_bean_classes");
		String[] servletConfigBeanClassNames = config.getStringArray("integration.servlet_config_bean_classes");

		if (rootConfigBeanClassNames == null) {
			rootConfigClasses = new Class<?>[] { RootConfigBean.class };
		} else {
			rootConfigClasses = new Class<?>[rootConfigBeanClassNames.length + 1];
			rootConfigClasses[0] = RootConfigBean.class;
			for (int i = 0; i < rootConfigBeanClassNames.length; i++) {
				try {
					rootConfigClasses[i + 1] = Class.forName(rootConfigBeanClassNames[i]);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}
			}
		}

		if (servletConfigBeanClassNames == null) {
			servletConfigClasses = new Class<?>[] { ServletConfigBean.class };
		} else {
			servletConfigClasses = new Class<?>[servletConfigBeanClassNames.length + 1];
			servletConfigClasses[0] = ServletConfigBean.class;
			for (int i = 0; i < servletConfigBeanClassNames.length; i++) {
				try {
					servletConfigClasses[i + 1] = Class.forName(servletConfigBeanClassNames[i]);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}
			}
		}
		super.onStartup(servletContext);
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return rootConfigClasses;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return servletConfigClasses;
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

		registration.setMultipartConfig(
				new MultipartConfigElement("/tmp", 1024 * 1024 * 512, 1024 * 1024 * 1024, 1024 * 512));
	}
}
