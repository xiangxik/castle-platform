package com.whenling.castle.security;

import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.whenling.castle.core.CastleConstants;
import com.whenling.castle.security.captcha.CaptchaConfigBean;

public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	@Override
	protected void afterSpringSecurityFilterChain(ServletContext servletContext) {
		super.afterSpringSecurityFilterChain(servletContext);

		DelegatingFilterProxy captchaFilter = new DelegatingFilterProxy(CaptchaConfigBean.CAPTCHA_FILTER_NAME);
		Dynamic captchaRegistration = servletContext.addFilter(CaptchaConfigBean.CAPTCHA_FILTER_NAME, captchaFilter);
		captchaRegistration.setAsyncSupported(isAsyncSecuritySupported());
		captchaRegistration.addMappingForUrlPatterns(getSecurityDispatcherTypes(), false, "/login");

		Dynamic encodingRegistration = servletContext.addFilter("CharacterEncodingFilter", new CharacterEncodingFilter(CastleConstants.characterEncoding, true));
		encodingRegistration.setAsyncSupported(isAsyncSecuritySupported());
		encodingRegistration.addMappingForUrlPatterns(getSecurityDispatcherTypes(), false, "/*");

	}

}
