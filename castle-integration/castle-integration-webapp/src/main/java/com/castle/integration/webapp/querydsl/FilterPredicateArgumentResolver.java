package com.castle.integration.webapp.querydsl;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;

public class FilterPredicateArgumentResolver implements HandlerMethodArgumentResolver {

	private final QuerydslBindingsFactory bindingsFactory;
	private final QuerydslPredicateBuilder predicateBuilder;

	private final ConversionService conversionService;
	private final ObjectMapper objectMapper;

	public FilterPredicateArgumentResolver(QuerydslBindingsFactory factory, ConversionService conversionService,
			ObjectMapper objectMapper) {
		this.bindingsFactory = factory;
		this.conversionService = conversionService == null ? new DefaultConversionService() : conversionService;
		this.predicateBuilder = new QuerydslPredicateBuilder(this.conversionService, factory.getEntityPathResolver());

		this.objectMapper = objectMapper;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {

		if (Predicate.class.equals(parameter.getParameterType())) {
			return true;
		}

		if (parameter.hasParameterAnnotation(QuerydslPredicate.class)) {
			throw new IllegalArgumentException(
					String.format("Parameter at position %s must be of type Predicate but was %s.",
							parameter.getParameterIndex(), parameter.getParameterType()));
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();

		String filterString = webRequest.getParameter("filter");
		if (!Strings.isNullOrEmpty(filterString)) {
			filterString = URLDecoder.decode(filterString, "UTF-8");

			List<Map<String, Object>> filters = objectMapper.readValue(filterString,
					TypeFactory.defaultInstance().constructCollectionType(List.class,
							TypeFactory.defaultInstance().constructMapType(Map.class, String.class, Object.class)));
			for (Map<String, Object> filter : filters) {
				String property = (String) filter.get("property");
				Object value = filter.get("value");

				Object convertedValue = conversionService.convert(value, TypeDescriptor.forObject(value),
						TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(String.class)));
				parameters.put(property, (List<String>) convertedValue);
			}
		} else {
			for (Entry<String, String[]> entry : webRequest.getParameterMap().entrySet()) {
				parameters.put(entry.getKey(), Arrays.asList(entry.getValue()));
			}
		}

		QuerydslPredicate annotation = parameter.getParameterAnnotation(QuerydslPredicate.class);
		TypeInformation<?> domainType = extractTypeInfo(parameter).getActualType();

		Class<? extends QuerydslBinderCustomizer<?>> customizer = (Class<? extends QuerydslBinderCustomizer<?>>) (annotation == null
				? null
				: annotation.bindings());
		QuerydslBindings bindings = bindingsFactory.createBindingsFor(customizer, domainType);

		return predicateBuilder.getPredicate(domainType, parameters, bindings);
	}

	static TypeInformation<?> extractTypeInfo(MethodParameter parameter) {

		QuerydslPredicate annotation = parameter.getParameterAnnotation(QuerydslPredicate.class);

		if (annotation != null && !Object.class.equals(annotation.root())) {
			return ClassTypeInformation.from(annotation.root());
		}

		return detectDomainType(ClassTypeInformation.fromReturnTypeOf(parameter.getMethod()));
	}

	private static TypeInformation<?> detectDomainType(TypeInformation<?> source) {

		if (source.getTypeArguments().isEmpty()) {
			return source;
		}

		TypeInformation<?> actualType = source.getActualType();

		if (source != actualType) {
			return detectDomainType(actualType);
		}

		if (source instanceof Iterable) {
			return source;
		}

		return detectDomainType(source.getComponentType());
	}

}
