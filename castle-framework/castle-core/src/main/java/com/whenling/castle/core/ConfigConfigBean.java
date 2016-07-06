package com.whenling.castle.core;

import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置项配置
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:47:03
 */
@Configuration
public class ConfigConfigBean {

	@Bean
	public static PlaceholderConfigurerSupport placeholderConfigurer() {
		ConfigurationPropertyResourceConfigurer placeholderConfigurer = new ConfigurationPropertyResourceConfigurer(
				StaticConfigSupplier.getConfiguration());
		placeholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
		return placeholderConfigurer;
	}

}
