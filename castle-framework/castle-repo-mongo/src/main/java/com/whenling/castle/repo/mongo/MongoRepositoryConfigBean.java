package com.whenling.castle.repo.mongo;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "com.whenling", includeFilters = {
		@Filter(value = MongoRepository.class, type = FilterType.ASSIGNABLE_TYPE) }, repositoryImplementationPostfix = "Impl", repositoryFactoryBeanClass = CustomMongoRepositoryFactoryBean.class)
@EnableMongoAuditing
public class MongoRepositoryConfigBean {

	@Value("${mongo.host?:localhost}")
	private String host;

	@Value("${mongo.database?:whenling}")
	private String database;

	@Bean
	public MongoTemplate mongoTemplate() throws UnknownHostException {
		DefaultDbRefResolver defaultDbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
		MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(defaultDbRefResolver,
				new MongoMappingContext());
		mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), mappingMongoConverter);
		return mongoTemplate;
	}

	@Bean
	public MongoDbFactory mongoDbFactory() throws UnknownHostException {
		return new SimpleMongoDbFactory(new MongoClient(host), database);
	}

}
