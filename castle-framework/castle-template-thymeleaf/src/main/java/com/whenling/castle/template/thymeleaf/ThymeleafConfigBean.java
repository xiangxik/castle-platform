package com.whenling.castle.template.thymeleaf;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.whenling.castle.core.CastleConstants;
import com.whenling.castle.core.ConfigWrapper;
import com.whenling.castle.core.StaticConfigSupplier;
import com.whenling.castle.web.WebSpringContext;

@Configuration
public class ThymeleafConfigBean {

	@Autowired
	private ServletContext servletContext;

	@Value("${template.thymeleaf.cacheable?:false}")
	private Boolean cacheable;

	@Bean
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setOrder(1);
		resolver.setCharacterEncoding(CastleConstants.characterEncoding);
		resolver.addStaticVariable("base", WebSpringContext.getContextPath());
		resolver.addStaticVariable("staticConfig", new ConfigWrapper(StaticConfigSupplier.getConfiguration()));
		// resolver.setViewNames(new String[] { "*.html", "*.xhtml" });
//		resolver.addStaticVariable("auth", AuthVariable.getInstance());
		return resolver;
	}

	// SpringResourceTemplateResolver:classpath
	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
		templateResolver.setPrefix("/WEB-INF/templates/");
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
//		templateEngine.addDialect(new SpringDataDialect());
//		templateEngine.addDialect(new SpringSecurityDialect());
		return templateEngine;
	}
}
