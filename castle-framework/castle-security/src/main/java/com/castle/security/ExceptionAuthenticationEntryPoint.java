package com.castle.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class ExceptionAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private Http403ForbiddenEntryPoint http403ForbiddenEntryPoint;

	private LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint;

	public ExceptionAuthenticationEntryPoint(Http403ForbiddenEntryPoint http403ForbiddenEntryPoint,
			LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint) {
		this.http403ForbiddenEntryPoint = http403ForbiddenEntryPoint;
		this.loginUrlAuthenticationEntryPoint = loginUrlAuthenticationEntryPoint;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
		if (isAjax) {
			http403ForbiddenEntryPoint.commence(request, response, authException);
		} else {
			loginUrlAuthenticationEntryPoint.commence(request, response, authException);
		}
	}

}
