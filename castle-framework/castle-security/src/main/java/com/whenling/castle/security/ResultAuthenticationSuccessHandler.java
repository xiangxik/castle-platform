package com.whenling.castle.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whenling.castle.repo.domain.Result;

public class ResultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private ObjectMapper objectMapper;

	public ResultAuthenticationSuccessHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		objectMapper.writeValue(response.getWriter(), Result.success());
	}

}
