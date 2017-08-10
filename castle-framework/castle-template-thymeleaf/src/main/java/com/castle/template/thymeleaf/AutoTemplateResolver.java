package com.castle.template.thymeleaf;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;

public class AutoTemplateResolver implements ITemplateResolver {

	private String name = this.getClass().getName();

	private ServletContextTemplateResolver servletContextTemplateResolver;
	private ClassLoaderTemplateResolver classLoaderTemplateResolver;

	public AutoTemplateResolver(ServletContextTemplateResolver servletContextTemplateResolver,
			ClassLoaderTemplateResolver classLoaderTemplateResolver) {
		super();
		this.servletContextTemplateResolver = servletContextTemplateResolver;
		this.classLoaderTemplateResolver = classLoaderTemplateResolver;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Integer getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	public TemplateResolution resolveTemplate(IEngineConfiguration configuration, String ownerTemplate, String template,
			Map<String, Object> templateResolutionAttributes) {
		if (StringUtils.startsWith(template, "classpath:")) {
			return classLoaderTemplateResolver.resolveTemplate(configuration, ownerTemplate,
					StringUtils.removeStart(template, "classpath:"), templateResolutionAttributes);
		}
		return servletContextTemplateResolver.resolveTemplate(configuration, ownerTemplate, template,
				templateResolutionAttributes);
	}

}
