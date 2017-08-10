package com.castle.integration.webapp.mvc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Range;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.google.common.base.Strings;

public class RangeMethodArgumentResolver<T extends Comparable<T>> implements HandlerMethodArgumentResolver {

	private ConversionService conversionService;
	
	public RangeMethodArgumentResolver(ConversionService conversionService) {
		this.conversionService = conversionService;
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(RangeParam.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		RangeParam rangeParam = parameter.getParameterAnnotation(RangeParam.class);
		String parameterValue = rangeParam.value();
		String value = webRequest.getParameter(parameterValue);
		if (Strings.isNullOrEmpty(value)) {
			return null;
		}

		String split = rangeParam.split();
		if (!StringUtils.contains(value, split)) {
			return null;
		}

		String[] rangeValues = StringUtils.splitByWholeSeparator(value, split, 2);
		@SuppressWarnings("unchecked")
		Class<T> elementClass = (Class<T>)rangeParam.elementClass();

		return new Range<>(conversionService.convert(rangeValues[0], elementClass), conversionService.convert(rangeValues[1], elementClass));
	}

}
