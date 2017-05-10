package com.whenling.castle.website.security;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whenling.castle.security.ResultAuthenticationFailureHanlder;
import com.whenling.castle.security.ResultAuthenticationSuccessHandler;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MainWebSecurityConfigBean extends WebSecurityConfigurerAdapter {

	@Value("${security.skip_auth_urls?:/assets/**,/captcha**,/upload/**}")
	private String[] skipAuthUrls;

	@Autowired
	private ObjectFactory<ObjectMapper> objectMapper;

	@Autowired
	private ObjectFactory<MessageSourceAccessor> messageSourceAccessor;

	public MainWebSecurityConfigBean() {
		super(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().and().addFilter(new WebAsyncManagerIntegrationFilter()).exceptionHandling().and().headers().and()
				.sessionManagement().and().securityContext().and().requestCache().and().anonymous().and().servletApi()
				.and().logout();

		http.getSharedObject(AuthenticationManagerBuilder.class)// .authenticationProvider(null)
				.authenticationEventPublisher(defaultAuthenticationEventPublisher());

		http.rememberMe().userDetailsService(userDetailsService());
		FormLoginConfigurer<HttpSecurity> configurer = http.headers().frameOptions().sameOrigin().and().csrf().disable()
				.formLogin();
		configurer.successHandler(new ResultAuthenticationSuccessHandler(objectMapper.getObject())).failureHandler(
				new ResultAuthenticationFailureHanlder(objectMapper.getObject(), messageSourceAccessor.getObject()));

		ExceptionHandlingConfigurer<HttpSecurity> exceptionConfigurer = configurer.permitAll().and().authorizeRequests()
				.antMatchers(skipAuthUrls).permitAll().anyRequest().authenticated().and().exceptionHandling();
		exceptionConfigurer.authenticationEntryPoint(new ExceptionAuthenticationEntryPoint(
				new Http403ForbiddenEntryPoint(), new LoginUrlAuthenticationEntryPoint("/login")));
		exceptionConfigurer.and().logout().logoutSuccessUrl("/").permitAll();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new DefaultDetailsService();
	}

	@Bean
	public DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher() {
		return new DefaultAuthenticationEventPublisher();
	}
}
