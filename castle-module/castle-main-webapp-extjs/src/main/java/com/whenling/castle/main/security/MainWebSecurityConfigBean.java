package com.whenling.castle.main.security;

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

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MainWebSecurityConfigBean extends WebSecurityConfigurerAdapter {

	@Autowired
	private ResultAuthenticationSuccessHandler successHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.getSharedObject(AuthenticationManagerBuilder.class)
				.authenticationEventPublisher(defaultAuthenticationEventPublisher());
		http.headers().frameOptions().sameOrigin().and().csrf().disable().formLogin().successHandler(successHandler)
				.permitAll().and().authorizeRequests()
				.antMatchers("/assets/**", "/extjs/**", "/", "/index", "/app_info",
						"/captcha**")
				.permitAll().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(new Http403ForbiddenEntryPoint()).and().logout().permitAll();
	}

	@Bean
	public DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher() {
		return new DefaultAuthenticationEventPublisher();
	}

}
