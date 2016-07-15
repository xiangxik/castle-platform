package com.whenling.castle.usercenter.provider.support;

import java.util.Properties;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.whenling.castle.integration.dubbo.javaconfig.JavaConfigContainer;

public class DubboProviderMain {

	public static void main(String[] args) throws Exception {

		String[] customArgs = new String[] { "javaconfig" };
		Properties properties = new Properties();
		properties.setProperty(JavaConfigContainer.SPRING_JAVACONFIG, "com.whenling");
		ConfigUtils.setProperties(properties);
		com.alibaba.dubbo.container.Main.main(customArgs);
	}
}
