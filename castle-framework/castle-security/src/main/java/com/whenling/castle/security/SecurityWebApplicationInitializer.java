package com.whenling.castle.security;

import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.RequestContextFilter;

import com.whenling.castle.core.CastleConstants;
import com.whenling.castle.core.StaticConfigSupplier;
import com.whenling.castle.security.captcha.CaptchaConfigBean;

@Order(1)
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		super.beforeSpringSecurityFilterChain(servletContext);

		String[] urlPatterns = StaticConfigSupplier.getConfiguration().getStringArray("captcha.urlPatterns");
		if (urlPatterns == null || urlPatterns.length == 0) {
			urlPatterns = new String[] { "/login", "/forgotPassword" };
		}

		DelegatingFilterProxy captchaFilter = new DelegatingFilterProxy(CaptchaConfigBean.CAPTCHA_FILTER_NAME);
		Dynamic captchaRegistration = servletContext.addFilter(CaptchaConfigBean.CAPTCHA_FILTER_NAME, captchaFilter);
		captchaRegistration.setAsyncSupported(isAsyncSecuritySupported());
		captchaRegistration.addMappingForUrlPatterns(getSecurityDispatcherTypes(), false, urlPatterns);

		Dynamic holderRegistration = servletContext.addFilter("RequestContextFilter", new RequestContextFilter());
		holderRegistration.setAsyncSupported(isAsyncSecuritySupported());
		holderRegistration.addMappingForUrlPatterns(getSecurityDispatcherTypes(), false, "/*");

		Dynamic encodingRegistration = servletContext.addFilter("CharacterEncodingFilter", new CharacterEncodingFilter(CastleConstants.characterEncoding, true));
		encodingRegistration.setAsyncSupported(isAsyncSecuritySupported());
		encodingRegistration.addMappingForUrlPatterns(getSecurityDispatcherTypes(), false, "/*");
	}

	@Override
	protected void afterSpringSecurityFilterChain(ServletContext servletContext) {
		super.afterSpringSecurityFilterChain(servletContext);

		// DelegatingFilterProxy captchaFilter = new
		// DelegatingFilterProxy(CaptchaConfigBean.CAPTCHA_FILTER_NAME);
		// Dynamic captchaRegistration =
		// servletContext.addFilter(CaptchaConfigBean.CAPTCHA_FILTER_NAME,
		// captchaFilter);
		// captchaRegistration.setAsyncSupported(isAsyncSecuritySupported());
		// captchaRegistration.addMappingForUrlPatterns(getSecurityDispatcherTypes(),
		// false, "/login", "/forgotPassword");
		//
		// Dynamic holderRegistration =
		// servletContext.addFilter("RequestContextFilter", new
		// RequestContextFilter());
		// holderRegistration.setAsyncSupported(isAsyncSecuritySupported());
		// holderRegistration.addMappingForUrlPatterns(getSecurityDispatcherTypes(),
		// false, "/*");
		//
		// Dynamic encodingRegistration =
		// servletContext.addFilter("CharacterEncodingFilter", new
		// CharacterEncodingFilter(CastleConstants.characterEncoding, true));
		// encodingRegistration.setAsyncSupported(isAsyncSecuritySupported());
		// encodingRegistration.addMappingForUrlPatterns(getSecurityDispatcherTypes(),
		// false, "/*");

	}

}
