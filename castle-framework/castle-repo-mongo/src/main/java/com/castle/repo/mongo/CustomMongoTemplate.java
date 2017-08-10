package com.castle.repo.mongo;

import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;

public class CustomMongoTemplate extends MongoTemplate {

	public CustomMongoTemplate(MongoDbFactory mongoDbFactory, MongoConverter mongoConverter) {
		super(mongoDbFactory, mongoConverter);
	}

	
}
