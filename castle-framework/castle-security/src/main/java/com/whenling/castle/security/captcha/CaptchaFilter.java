package com.whenling.castle.security.captcha;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.octo.captcha.service.CaptchaService;
import com.whenling.castle.repo.domain.Result;

public class CaptchaFilter extends OncePerRequestFilter {

	private CaptchaService captchaService;
	private String codeParam;
	private ObjectMapper objectMapper;

	public CaptchaFilter(CaptchaService captchaService, String codeParam, ObjectMapper objectMapper) {

		Assert.notNull(captchaService);
		Assert.notNull(codeParam);
		Assert.notNull(objectMapper);

		this.captchaService = captchaService;
		this.codeParam = codeParam;
		this.objectMapper = objectMapper;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (request.getMethod().equals("POST")) {
			String code = request.getParameter(codeParam);
			if (!Strings.isNullOrEmpty(code) && captchaService.validateResponseForID(request.getSession(true).getId(), code.toUpperCase())) {
				filterChain.doFilter(request, response);
			} else {
				objectMapper.writeValue(response.getOutputStream(), Result.captchaError());
			}
		} else {
			filterChain.doFilter(request, response);

		}
	}

}
