package com.whenling.castle.security;

import javax.servlet.ServletContext;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.RequestContextFilter;

import com.whenling.castle.core.CastleConstants;
import com.whenling.castle.core.StaticConfigSupplier;
import com.whenling.castle.security.captcha.CaptchaConfigBean;

@Order(-10)
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
		super.beforeSpringSecurityFilterChain(servletContext);

		Boolean captchaEnabled = StaticConfigSupplier.getConfiguration().getBoolean("captcha.enabled", true);
		if (captchaEnabled) {
			String[] urlPatterns = StaticConfigSupplier.getConfiguration().getStringArray("captcha.urlPatterns");
			if (urlPatterns == null || urlPatterns.length == 0) {
				urlPatterns = new String[] { "/login", "/forgotPassword" };
			}

			DelegatingFilterProxy captchaFilter = new DelegatingFilterProxy(CaptchaConfigBean.CAPTCHA_FILTER_NAME);
			insertFilters(servletContext, captchaFilter);
		}

		insertFilters(servletContext, new RequestContextFilter(), new CharacterEncodingFilter(CastleConstants.characterEncoding, true));
	}

	@Override
	protected void afterSpringSecurityFilterChain(ServletContext servletContext) {
		super.afterSpringSecurityFilterChain(servletContext);

	}

}
