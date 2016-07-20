package com.whenling.castle.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whenling.castle.repo.domain.Result;

public class ResultAuthenticationFailureHanlder implements AuthenticationFailureHandler {

	private ObjectMapper objectMapper;

	public ResultAuthenticationFailureHanlder(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		objectMapper.writeValue(response.getWriter(), Result.failure().message(exception.getMessage()));
	}

}
