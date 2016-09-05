package com.whenling.castle.main.security;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whenling.castle.security.ResultAuthenticationFailureHanlder;
import com.whenling.castle.security.ResultAuthenticationSuccessHandler;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MainWebSecurityConfigBean extends WebSecurityConfigurerAdapter {

	@Value("${security.skip_auth_urls?:/assets/**,/extjs/**,/,/index,/app_info,/captcha**}")
	private String[] skipAuthUrls;

	@Autowired
	private ObjectFactory<ObjectMapper> objectMapper;

	public MainWebSecurityConfigBean() {
		super(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().and().addFilter(new WebAsyncManagerIntegrationFilter()).exceptionHandling().and().headers().and().sessionManagement().and().securityContext().and().requestCache().and().anonymous()
				.and().servletApi().and().logout();

		http.getSharedObject(AuthenticationManagerBuilder.class)// .authenticationProvider(null)
				.authenticationEventPublisher(defaultAuthenticationEventPublisher());

		http.headers().frameOptions().sameOrigin().and().csrf().disable().formLogin().successHandler(resultAuthenticationSuccessHandler()).failureHandler(resultAuthenticationFailureHandler())
				.permitAll().and().authorizeRequests().antMatchers(skipAuthUrls).permitAll().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(new Http403ForbiddenEntryPoint()).and().logout().logoutSuccessUrl("/").permitAll();
	}

	@Bean
	public ResultAuthenticationSuccessHandler resultAuthenticationSuccessHandler() {
		return new ResultAuthenticationSuccessHandler(objectMapper.getObject());
	}

	@Bean
	public ResultAuthenticationFailureHanlder resultAuthenticationFailureHandler() {
		return new ResultAuthenticationFailureHanlder(objectMapper.getObject());
	}

	@Bean
	public DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher() {
		return new DefaultAuthenticationEventPublisher();
	}
}
