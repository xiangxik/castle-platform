package com.castle.repo.jpa.json;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JpaJsonConfigBean implements InitializingBean {

	@Autowired
	private ObjectMapper objectMapper;

	@Bean
	public JpaModule jpaModule() {
		return new JpaModule();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		objectMapper.registerModule(jpaModule());
	}

}
