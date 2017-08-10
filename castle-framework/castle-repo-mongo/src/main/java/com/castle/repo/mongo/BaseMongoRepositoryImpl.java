package com.castle.repo.mongo;

import java.util.Collections;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;

import com.google.common.base.Strings;

public class BaseMongoRepositoryImpl<T> extends QueryDslMongoRepository<T, String> implements BaseMongoRepository<T> {

	private final MongoOperations mongoOperations;
	private final MongoEntityInformation<T, String> entityInformation;

	public BaseMongoRepositoryImpl(MongoEntityInformation<T, String> entityInformation, MongoOperations mongoOperations) {
		super(entityInformation, mongoOperations);

		this.entityInformation = entityInformation;
		this.mongoOperations = mongoOperations;
	}
	
	@Override
	public <S extends T> S save(S entity) {
		if(entity instanceof BaseDoc) {
			if(Strings.isNullOrEmpty(((BaseDoc) entity).getId())) {
				((BaseDoc) entity).setId(null);
			}
		}
		return super.save(entity);
	}

	protected List<T> findByQuery(Query query) {

		if (query == null) {
			return Collections.emptyList();
		}
		return mongoOperations.find(query, entityInformation.getJavaType(), entityInformation.getCollectionName());
	}
}
