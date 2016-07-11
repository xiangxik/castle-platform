package com.whenling.castle.usercenter.webapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserCenterWebSecurityConfigBean extends WebSecurityConfigurerAdapter {

	public UserCenterWebSecurityConfigBean() {
		super(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().and().addFilter(new WebAsyncManagerIntegrationFilter()).exceptionHandling().and().headers().and().sessionManagement().and().securityContext().and().requestCache().and().anonymous()
				.and().servletApi().and().logout();

		http.getSharedObject(AuthenticationManagerBuilder.class)// .authenticationProvider(null)
				.authenticationEventPublisher(defaultAuthenticationEventPublisher());
		http.headers().frameOptions().sameOrigin().and().csrf().disable().formLogin().defaultSuccessUrl("/").failureUrl("/login").permitAll().and().authorizeRequests()
				.antMatchers("/assets/**", "/extjs/**", "/register", "/captcha**", "/index").permitAll().anyRequest().authenticated().and().exceptionHandling().and().logout().permitAll();
	}

	@Bean
	public DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher() {
		return new DefaultAuthenticationEventPublisher();
	}

}
