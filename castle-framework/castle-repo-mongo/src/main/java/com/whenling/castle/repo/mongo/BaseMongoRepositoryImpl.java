package com.whenling.castle.repo.mongo;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;

public class BaseMongoRepositoryImpl<T> extends QueryDslMongoRepository<T, String>implements BaseMongoRepository<T> {

	public BaseMongoRepositoryImpl(MongoEntityInformation<T, String> entityInformation,
			MongoOperations mongoOperations) {
		super(entityInformation, mongoOperations);
	}

}
