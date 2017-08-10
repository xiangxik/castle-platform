package com.castle.integration.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.castle.web.ServletSupport;

@Configuration
@ComponentScan(basePackages = { "com.castle" }, excludeFilters = {
		@ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION),
		@ComponentScan.Filter(value = EnableWebMvc.class, type = FilterType.ANNOTATION),
		@ComponentScan.Filter(value = ServletSupport.class, type = FilterType.ANNOTATION) })
@EnableAspectJAutoProxy
@EnableAsync
@EnableScheduling
public class RootConfigBean {

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(5);
		threadPoolTaskExecutor.setMaxPoolSize(50);
		threadPoolTaskExecutor.setQueueCapacity(1000);
		threadPoolTaskExecutor.setKeepAliveSeconds(60);
		return threadPoolTaskExecutor;
	}

}
