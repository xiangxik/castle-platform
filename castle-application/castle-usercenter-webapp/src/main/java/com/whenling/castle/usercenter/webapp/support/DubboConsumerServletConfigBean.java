package com.whenling.castle.usercenter.webapp.support;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.whenling.castle.web.ServletSupport;

@Configuration
@ServletSupport
public class DubboConsumerServletConfigBean {

	@Bean
	public static AnnotationBean annotationBean() {
		AnnotationBean annotationBean = new AnnotationBean();
		return annotationBean;
	}
}
