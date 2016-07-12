package com.whenling.castle.integration.dubbo.javaconfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.container.Container;

public class JavaConfigContainer implements Container {

	private static final Logger logger = LoggerFactory.getLogger(JavaConfigContainer.class);

	public static final String SPRING_JAVACONFIG = "dubbo.spring.javaconfig";

	public static final String DEFAULT_SPRING_JAVACONFIG = "dubbo.spring.javaconfig";

	static AnnotationConfigApplicationContext context;

	public static AnnotationConfigApplicationContext getContext() {
		return context;
	}

	public void start() {
		String configPath = ConfigUtils.getProperty(SPRING_JAVACONFIG);
		if (configPath == null || configPath.length() == 0) {
			configPath = DEFAULT_SPRING_JAVACONFIG;
		}
		context = new AnnotationConfigApplicationContext(configPath);
		context.start();
	}

	public void stop() {
		try {
			if (context != null) {
				context.stop();
				context.close();
				context = null;
			}
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
		}
	}

}
