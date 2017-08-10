package com.castle.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.castle.repo.domain.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import ch.mfrey.jackson.antpathfilter.AntPathPropertyFilter;

public class ResultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private ObjectMapper objectMapper;

	public ResultAuthenticationSuccessHandler(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		CustomUserDetails<?, ?> customUser = (CustomUserDetails<?, ?>) authentication.getPrincipal();
		ObjectWriter objectWriter = objectMapper.writer(
				new SimpleFilterProvider().addFilter("antPathFilter", new AntPathPropertyFilter(new String[] { "*", "*.*", "*.*.id", "*.*.name" })));
		objectWriter.writeValue(response.getWriter(), Result.success().addProperties("currentUser", customUser.getCustomUser()));
	}

}
