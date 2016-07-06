package com.whenling.castle.usercenter.provider.support;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DubboProviderMain {

	public static void main(String[] args) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				DubboProviderConfigBean.class);
		context.start();

		System.in.read();
		context.close();
	}
}
