package com.whenling.castle.usercenter.webapp.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.whenling.castle.integration.dubbo.DubboBeanPostProcessor;
import com.whenling.castle.web.ServletSupport;

@Configuration
@ServletSupport
public class DubboConsumerConfigBean {

	@Bean
	public ConsumerConfig consumerConfig() {
		ConsumerConfig consumerConfig = new ConsumerConfig();
		consumerConfig.setTimeout(1000000);
//		consumerConfig.setCheck(false);
		// consumerConfig.setProxy("jdk");
		return consumerConfig;
	}

	@Bean
	public ApplicationConfig applicationConfig() {
		ApplicationConfig config = new ApplicationConfig("consumer" + System.currentTimeMillis());
		// config.setCompiler("jdk");
		return config;
	}

	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig config = new RegistryConfig();
		config.setAddress("multicast://224.5.6.7:1234");
//		config.setCheck(false);
		// 多个consumer开启
		// Map<String, String> parameters = new HashMap<>();
		// parameters.put("unicast", "fasle");
		// config.setParameters(parameters);
		return config;
	}

	@Bean
	public static DubboBeanPostProcessor dubboBeanPostProcessor() {
		DubboBeanPostProcessor beanPostProcessor = new DubboBeanPostProcessor();
		beanPostProcessor.setPackage("com.whenling");
		return beanPostProcessor;
	}
	// @Bean
	// public static AnnotationBean annotationBean() {
	// AnnotationBean annotationBean = new AnnotationBean();
	// annotationBean.setPackage("com.whenling");
	// return annotationBean;
	// }
}
