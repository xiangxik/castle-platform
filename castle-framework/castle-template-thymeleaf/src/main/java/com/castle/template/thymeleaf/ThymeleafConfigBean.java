package com.castle.template.thymeleaf;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.castle.config.ConfigWrapper;
import com.castle.config.StaticConfigSupplier;
import com.castle.core.CastleConstants;
import com.castle.template.thymeleaf.dialect.CastleStandardDialect;
import com.castle.web.WebSpringContext;

@Configuration
public class ThymeleafConfigBean {

	@Autowired
	private ServletContext servletContext;

	@Value("${template.thymeleaf.cacheable?:true}")
	private Boolean cacheable;

	// servletcontext/classpath
	@Value("${template.thymeleaf.loader?:auto}")
	private String loader;

	@Value("${template.thymeleaf.prefix?:/WEB-INF/templates/}")
	private String prefix;

	@Value("${template.thymeleaf.class_prefix?:/META-INF/templates/}")
	private String classPrefix;
	
	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setOrder(1);
		resolver.setCharacterEncoding(CastleConstants.characterEncoding);
		resolver.addStaticVariable("base", WebSpringContext.getContextPath());
		resolver.addStaticVariable("staticConfig", new ConfigWrapper(StaticConfigSupplier.getConfiguration()));
		resolver.setExcludedViewNames(new String[] { "/views/*", "/jsp/*", "*.jsp", "*.ftl" });

		// resolver.addStaticVariable("auth", AuthVariable.getInstance());
		return resolver;
	}

	// SpringResourceTemplateResolver:classpath
	@Bean
	public ITemplateResolver templateResolver() {
		ITemplateResolver templateResolver = null;
		switch (loader) {
		case "servletcontext":
			templateResolver = servletContextTemplateResolver();
			break;
		case "classpath":
			templateResolver = classLoaderTemplateResolver();
			break;
		case "spring":
			templateResolver = new SpringResourceTemplateResolver();
			break;

		default:
			templateResolver = new AutoTemplateResolver(servletContextTemplateResolver(),
					classLoaderTemplateResolver());
			break;
		}
		return templateResolver;

	}

	protected ServletContextTemplateResolver servletContextTemplateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setPrefix(prefix);
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCacheable(cacheable);
		templateResolver.setCharacterEncoding(CastleConstants.characterEncoding);
		return templateResolver;
	}

	protected ClassLoaderTemplateResolver classLoaderTemplateResolver() {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix(classPrefix);
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML");
		templateResolver.setCacheable(cacheable);
		templateResolver.setCharacterEncoding(CastleConstants.characterEncoding);
		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		 templateEngine.addDialect(new CastleStandardDialect(messageSourceAccessor));
		// templateEngine.addDialect(new SpringSecurityDialect());
		return templateEngine;
	}
}
