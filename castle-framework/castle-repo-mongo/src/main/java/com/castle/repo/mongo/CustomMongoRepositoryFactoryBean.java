package com.castle.repo.mongo;

import java.io.Serializable;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.ClassUtils;

public class CustomMongoRepositoryFactoryBean<R extends MongoRepository<T, I>, T, I extends Serializable>
		extends MongoRepositoryFactoryBean<R, T, I> {

	public CustomMongoRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@Override
	protected RepositoryFactorySupport getFactoryInstance(MongoOperations operations) {
		return new CustomRepository(operations);
	}

	private static class CustomRepository extends MongoRepositoryFactory {

		private final MongoOperations mongoOperations;

		public CustomRepository(MongoOperations mongoOperations) {
			super(mongoOperations);

			this.mongoOperations = mongoOperations;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected Object getTargetRepository(RepositoryInformation information) {
			return isBaseRepository(information.getRepositoryInterface())
					? (isHierarchicalRepository(information.getRepositoryInterface())
							? new HierarchicalMongoRepositoryImpl(getEntityInformation(information.getDomainType()),
									mongoOperations)
							: new BaseMongoRepositoryImpl<>(getEntityInformation(information.getDomainType()),
									mongoOperations))
					: super.getTargetRepository(information);
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return isBaseRepository(metadata.getRepositoryInterface())
					? (isHierarchicalRepository(metadata.getRepositoryInterface())
							? HierarchicalMongoRepositoryImpl.class : BaseMongoRepositoryImpl.class)
					: super.getRepositoryBaseClass(metadata);
		}

		private boolean isHierarchicalRepository(Class<?> repositoryInterface) {
			return ClassUtils.isAssignable(HierarchicalMongoRepository.class, repositoryInterface);
		}

		private boolean isBaseRepository(Class<?> repositoryInterface) {
			return ClassUtils.isAssignable(BaseMongoRepository.class, repositoryInterface);
		}

	}
}
