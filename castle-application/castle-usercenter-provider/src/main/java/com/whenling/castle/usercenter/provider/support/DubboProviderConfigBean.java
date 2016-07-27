package com.whenling.castle.usercenter.provider.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;

@Configuration
public class DubboProviderConfigBean {

	@Bean
	public ProviderConfig providerConfig() {
		ProviderConfig providerConfig = new ProviderConfig();
		providerConfig.setTimeout(1000000);
		// providerConfig.setProxy("jdk");
		return providerConfig;
	}

	@Bean
	public ApplicationConfig applicationConfig() {
		ApplicationConfig config = new ApplicationConfig("provider" + System.currentTimeMillis());
		// config.setCompiler("jdk");
		return config;
	}

	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig config = new RegistryConfig();
//		config.setAddress("multicast://224.5.6.7:1234");
		config.setAddress("zookeeper://112.74.124.44:2181");
		return config;
	}

	@Bean
	public ProtocolConfig protocolConfig() {
		ProtocolConfig config = new ProtocolConfig("dubbo", 20881);
		return config;
	}

	@Bean
	public static AnnotationBean annotationBean() {
		AnnotationBean annotationBean = new AnnotationBean();
		return annotationBean;
	}
}
