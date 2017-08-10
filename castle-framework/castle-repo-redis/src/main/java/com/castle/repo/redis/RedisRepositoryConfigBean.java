package com.castle.repo.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import com.google.common.base.Strings;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableRedisRepositories(basePackages = { "com.castle" })
public class RedisRepositoryConfigBean {

	@Value("${redis.host?:120.76.217.241}")
	private String hostName;

	@Value("${redis.port?:6379}")
	private String port;

	@Value("${redis.password?:abcde123451}")
	private String password;

	@Value("${redis.maxIdle?:" + GenericObjectPoolConfig.DEFAULT_MAX_IDLE + "}")
	private Integer maxIdle;

	@Value("${redis.maxTotal?:" + GenericObjectPoolConfig.DEFAULT_MAX_TOTAL + "}")
	private Integer maxTotal;

	@Value("${redis.minIdle?:" + GenericObjectPoolConfig.DEFAULT_MIN_IDLE + "}")
	private Integer minIdle;

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMaxTotal(maxTotal);
		config.setMinIdle(minIdle);
		return config;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setPoolConfig(jedisPoolConfig());
		connectionFactory.setHostName(hostName);
		connectionFactory.setPort(Integer.parseInt(port));
		if (!Strings.isNullOrEmpty(password)) {
			connectionFactory.setPassword(password);
		}
		return connectionFactory;
	}

	@Bean
	public StringRedisTemplate redisTemplate() {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(jedisConnectionFactory());
		return stringRedisTemplate;
	}

}
