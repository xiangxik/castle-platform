package com.castle.integration.webapp.mvc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.ModelAttributeMethodProcessor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.castle.web.ServletSupport;

@ServletSupport
@Component
public class ArgumentResolverResorter implements InitializingBean, ApplicationContextAware {

	@Autowired
	@Qualifier("mvcConversionService")
	private ObjectFactory<ConversionService> conversionService;

	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		List<HandlerMethodArgumentResolver> argumentResolvers = handlerAdapter.getArgumentResolvers();
		List<HandlerMethodArgumentResolver> newArgumentResolvers = new ArrayList<>(argumentResolvers);
		int indexArg = findIndexOfModelAttributeMethodProcessor(argumentResolvers);
		newArgumentResolvers.set(indexArg,
				new EntityModelAttributeMethodProcessor(conversionService.getObject(), false, this.applicationContext));
		handlerAdapter.setArgumentResolvers(newArgumentResolvers);

		List<HandlerMethodArgumentResolver> initBinderArgumentResolvers = handlerAdapter
				.getInitBinderArgumentResolvers();
		List<HandlerMethodArgumentResolver> newInitBinderArgumentResolvers = new ArrayList<>(
				initBinderArgumentResolvers);
		int indexBinder = findIndexOfModelAttributeMethodProcessor(handlerAdapter.getInitBinderArgumentResolvers());
		newInitBinderArgumentResolvers.set(indexBinder,
				new EntityModelAttributeMethodProcessor(conversionService.getObject(), false, this.applicationContext));
		handlerAdapter.setInitBinderArgumentResolvers(newInitBinderArgumentResolvers);
	}

	private int findIndexOfModelAttributeMethodProcessor(List<HandlerMethodArgumentResolver> resolvers) {
		for (int i = 0; i < resolvers.size(); i++) {
			HandlerMethodArgumentResolver resolver = resolvers.get(i);
			if (resolver instanceof ModelAttributeMethodProcessor) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
