package com.whenling.castle.usercenter.webapp.security;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.whenling.castle.security.ResultAuthenticationSuccessHandler;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserCenterWebSecurityConfigBean extends WebSecurityConfigurerAdapter {

	@Autowired
	private ObjectFactory<ObjectMapper> objectMapper;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.getSharedObject(AuthenticationManagerBuilder.class)// .authenticationProvider(null)
				.authenticationEventPublisher(defaultAuthenticationEventPublisher());
		http.headers().frameOptions().sameOrigin().and().csrf().disable().formLogin().successHandler(resultAuthenticationSuccessHandler()).permitAll().and().authorizeRequests()
				.antMatchers("/assets/**", "/extjs/**", "/register", "/captcha**").permitAll().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(new Http403ForbiddenEntryPoint()).and().logout().permitAll();
	}

	@Bean
	public ResultAuthenticationSuccessHandler resultAuthenticationSuccessHandler() {
		return new ResultAuthenticationSuccessHandler(objectMapper.getObject());
	}

	@Bean
	public DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher() {
		return new DefaultAuthenticationEventPublisher();
	}

}
