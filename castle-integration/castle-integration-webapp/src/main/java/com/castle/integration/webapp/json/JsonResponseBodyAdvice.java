package com.castle.integration.webapp.json;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import com.castle.repo.domain.Tree;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.common.base.Strings;

import ch.mfrey.jackson.antpathfilter.AntPathPropertyFilter;

@ControllerAdvice
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class JsonResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

	@Override
	protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request,
			ServerHttpResponse response) {
		Object value = bodyContainer.getValue();
		if (value != null) {
			HttpServletRequest httpRequest = ((ServletServerHttpRequest) request).getServletRequest();

			// antpathfilter
			FilterProvider filterProvider = null;

			String pathFilter = returnType.hasMethodAnnotation(PathFilter.class) ? returnType.getMethodAnnotation(PathFilter.class).value()
					: httpRequest.getParameter("path_filter");
			if (!Strings.isNullOrEmpty(pathFilter)) {
				filterProvider = new SimpleFilterProvider().addFilter("antPathFilter", new AntPathPropertyFilter(pathFilter.split(",")));
			} else {
				if (value instanceof Tree) {
					filterProvider = new SimpleFilterProvider().addFilter("antPathFilter",
							new AntPathPropertyFilter(new String[] { "*", "*.*", "*.*.id", "*.*.name", "*.*.title", "*.*.fullName" }));
				} else if (value instanceof Page) {
					filterProvider = new SimpleFilterProvider().addFilter("antPathFilter",
							new AntPathPropertyFilter(new String[] { "*", "*.*", "*.*.id", "*.*.name", "*.*.title", "*.*.fullName" }));
				} else {
					filterProvider = new SimpleFilterProvider().addFilter("antPathFilter",
							new AntPathPropertyFilter(new String[] { "*", "*.id", "*.name", "*.title", "*.fullName" }));
				}
			}

			bodyContainer.setFilters(filterProvider);
		}

		// bodyContainer.setFilters(filters);

	}

}
