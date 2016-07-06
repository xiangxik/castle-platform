package com.whenling.castle.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.whenling.castle.cache.infinispan.provider.SpringEmbeddedCacheManagerFactoryBean;

@Configuration
@EnableCaching
public class CacheConfigBean implements CachingConfigurer {

	public static final String INFINISPAN_CONFIG = "/com/whenling/castle/cache/infinispan/infinispan.xml";

	@Value("${cacheConfigFile?:" + INFINISPAN_CONFIG + "}")
	private String cacheConfigFileLocation;

	@Bean
	public SpringEmbeddedCacheManagerFactoryBean springEmbeddedCacheManagerFactoryBean() {
		SpringEmbeddedCacheManagerFactoryBean cacheManagerFactoryBean = new SpringEmbeddedCacheManagerFactoryBean();
		cacheManagerFactoryBean.setConfigurationFileLocation(new ClassPathResource(cacheConfigFileLocation));
		return cacheManagerFactoryBean;
	}

	@Override
	public CacheManager cacheManager() {
		try {
			return springEmbeddedCacheManagerFactoryBean().getObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public CacheResolver cacheResolver() {
		return new SimpleCacheResolver(cacheManager());
	}

	@Override
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

	@Override
	public CacheErrorHandler errorHandler() {
		return new SimpleCacheErrorHandler();
	}

}
