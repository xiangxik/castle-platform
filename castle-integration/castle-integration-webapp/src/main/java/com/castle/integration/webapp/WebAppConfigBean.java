package com.castle.integration.webapp;

import java.util.List;

import javax.xml.transform.Source;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.format.DistanceFormatter;
import org.springframework.data.geo.format.PointFormatter;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.ProxyingHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.castle.core.CastleConstants;
import com.castle.integration.webapp.mvc.RangeMethodArgumentResolver;
import com.castle.integration.webapp.mvc.StringToDateConverter;
import com.castle.integration.webapp.querydsl.FilterPredicateArgumentResolver;
import com.castle.repo.domain.PageRequestProxy;
import com.castle.web.ServletSupport;
import com.fasterxml.jackson.databind.ObjectMapper;

@ServletSupport
@Configuration
public class WebAppConfigBean extends WebMvcConfigurerAdapter implements ApplicationContextAware {

	@Autowired
	@Qualifier("mvcConversionService")
	private ObjectFactory<ConversionService> conversionService;

	@Autowired
	private ObjectFactory<ObjectMapper> objectMapper;

	@Value("${mvc.pageParameter?:page}")
	private String pageParameterName;

	@Value("${mvc.sizeParameter?:limit}")
	private String sizeParameterName;

	@Value("${mvc.oneIndexed?:true}")
	private Boolean oneIndexed;

	private ApplicationContext applicationContext;

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);

		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setWriteAcceptCharset(true);
		stringConverter.setDefaultCharset(CastleConstants.charset);

		converters.add(new ByteArrayHttpMessageConverter());
		converters.add(stringConverter);
		converters.add(new ResourceHttpMessageConverter());
		converters.add(new SourceHttpMessageConverter<Source>());
		converters.add(new AllEncompassingFormHttpMessageConverter());
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper.getObject());
		converters.add(jackson2HttpMessageConverter);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(DistanceFormatter.INSTANCE);
		registry.addFormatter(PointFormatter.INSTANCE);
		registry.addConverter(new StringToDateConverter());

		if (!(registry instanceof FormattingConversionService)) {
			return;
		}

		FormattingConversionService conversionService = (FormattingConversionService) registry;

		DomainClassConverter<FormattingConversionService> converter = new DomainClassConverter<FormattingConversionService>(conversionService);
		converter.setApplicationContext(applicationContext);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(sortResolver());
		argumentResolvers.add(pageableResolver());

		ProxyingHandlerMethodArgumentResolver resolver = new ProxyingHandlerMethodArgumentResolver(conversionService.getObject());
		resolver.setBeanFactory(applicationContext);
		resolver.setBeanClassLoader(applicationContext.getClassLoader());

		argumentResolvers.add(resolver);

		argumentResolvers.add(0, querydslPredicateArgumentResolver());

		RangeMethodArgumentResolver<?> rangeArgumentResolver = new RangeMethodArgumentResolver<>(conversionService.getObject());
		argumentResolvers.add(rangeArgumentResolver);
	}

	@Bean
	public PageableHandlerMethodArgumentResolver pageableResolver() {
		PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver = new PageableHandlerMethodArgumentResolver(sortResolver()) {
			@Override
			public Pageable resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
				Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);
				return new PageRequestProxy(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
			}
		};
		pageableHandlerMethodArgumentResolver.setPageParameterName(pageParameterName);
		pageableHandlerMethodArgumentResolver.setOneIndexedParameters(oneIndexed);
		pageableHandlerMethodArgumentResolver.setSizeParameterName(sizeParameterName);
		return pageableHandlerMethodArgumentResolver;
	}

	@Bean
	public SortHandlerMethodArgumentResolver sortResolver() {
		return new SortHandlerMethodArgumentResolver();
	}

	@Bean
	public FilterPredicateArgumentResolver querydslPredicateArgumentResolver() {
		return new FilterPredicateArgumentResolver(querydslBindingsFactory(), conversionService.getObject(), objectMapper.getObject());
	}

	@Lazy
	@Bean
	public QuerydslBindingsFactory querydslBindingsFactory() {
		QuerydslBindingsFactory querydslBindingsFactory = new QuerydslBindingsFactory(SimpleEntityPathResolver.INSTANCE);
		querydslBindingsFactory.setApplicationContext(applicationContext);
		return querydslBindingsFactory;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}
