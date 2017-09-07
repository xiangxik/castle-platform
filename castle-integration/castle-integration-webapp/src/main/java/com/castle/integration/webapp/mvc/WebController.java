package com.castle.integration.webapp.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class WebController {

	@Autowired
	private ConversionService conversionService;

	protected HttpServletRequest getRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			return ((ServletRequestAttributes) requestAttributes).getRequest();
		}
		return null;
	}

	protected HttpServletResponse getResponse() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			return ((ServletRequestAttributes) requestAttributes).getResponse();
		}
		return null;
	}

	protected String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	protected String[] getParameterValues(String name) {
		return getRequest().getParameterValues(name);
	}

	protected <T> T getParameter(String name, Class<T> targetType) {
		return conversionService.convert(getParameter(name), targetType);
	}

	protected <T> T getParameter(String name, Class<T> targetType, T defaultValue) {
		T convertedValue = getParameter(name, targetType);
		return convertedValue == null ? defaultValue : convertedValue;
	}

	protected <T> List<T> getParameterValues(String name, Class<T> targetType) {
		String[] values = getParameterValues(name);
		if (values == null)
			return null;

		List<T> list = new ArrayList<>();
		for (String value : values) {
			list.add(conversionService.convert(value, targetType));
		}
		return list;
	}

}
