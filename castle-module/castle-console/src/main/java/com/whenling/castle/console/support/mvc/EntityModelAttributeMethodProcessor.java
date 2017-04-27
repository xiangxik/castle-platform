package com.whenling.castle.console.support.mvc;

import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.data.repository.support.Repositories;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;
import org.springframework.web.servlet.HandlerMapping;

import com.google.common.base.Strings;
import com.whenling.castle.console.support.setting.Setting;
import com.whenling.castle.console.support.setting.SettingUtils;
import com.whenling.castle.repo.jpa.BaseEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public class EntityModelAttributeMethodProcessor extends ModelAttributeMethodProcessor {

	private Repositories repositories;
	private ConversionService conversionService;

	public EntityModelAttributeMethodProcessor(ConversionService conversionService, boolean annotationNotRequired,
			ApplicationContext applicationContext) {
		super(annotationNotRequired);
		this.conversionService = conversionService;
		this.repositories = new Repositories(applicationContext);
	}

	@Override
	protected final Object createAttribute(String attributeName, MethodParameter methodParam,
			WebDataBinderFactory binderFactory, NativeWebRequest request) throws Exception {

		String value = getRequestValueForAttribute(attributeName, request);
		if (value != null) {
			Object attribute = createAttributeFromRequestValue(value, attributeName, methodParam, binderFactory,
					request);
			if (attribute != null) {
				return attribute;
			}
		} else {
			Class<?> parameterType = methodParam.getParameterType();
			if (ClassUtils.isAssignable(BaseEntity.class, parameterType)) {
				String id = request.getParameter("id");
				if (!Strings.isNullOrEmpty(id)) {
					return conversionService.convert(id, methodParam.getParameterType());
				} else {
					Object repository = repositories.getRepositoryFor(parameterType);
					if (repository != null && repository instanceof BaseJpaRepository) {
						return ((BaseJpaRepository<?, ?>) repository).newEntity();
					}
					return parameterType.newInstance();
				}
			} else if (ClassUtils.isAssignable(Setting.class, parameterType)) {
				return SettingUtils.get();
			}
		}

		return super.createAttribute(attributeName, methodParam, binderFactory, request);
	}

	protected String getRequestValueForAttribute(String attributeName, NativeWebRequest request) {
		Map<String, String> variables = getUriTemplateVariables(request);
		if (StringUtils.hasText(variables.get(attributeName))) {
			return variables.get(attributeName);
		} else if (StringUtils.hasText(request.getParameter(attributeName))) {
			return request.getParameter(attributeName);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	protected final Map<String, String> getUriTemplateVariables(NativeWebRequest request) {
		Map<String, String> variables = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
		return (variables != null ? variables : Collections.<String, String>emptyMap());
	}

	protected Object createAttributeFromRequestValue(String sourceValue, String attributeName,
			MethodParameter methodParam, WebDataBinderFactory binderFactory, NativeWebRequest request)
			throws Exception {

		DataBinder binder = binderFactory.createBinder(request, null, attributeName);
		ConversionService conversionService = binder.getConversionService();
		if (conversionService != null) {
			TypeDescriptor source = TypeDescriptor.valueOf(String.class);
			TypeDescriptor target = new TypeDescriptor(methodParam);
			if (conversionService.canConvert(source, target)) {
				return binder.convertIfNecessary(sourceValue, methodParam.getParameterType(), methodParam);
			}
		}
		return null;
	}

	@Override
	protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
		ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
		ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
		servletBinder.bind(servletRequest);
	}

}
