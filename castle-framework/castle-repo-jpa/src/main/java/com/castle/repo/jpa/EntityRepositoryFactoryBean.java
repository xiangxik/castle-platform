package com.castle.repo.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.ClassUtils;

public class EntityRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable>
		extends JpaRepositoryFactoryBean<R, T, I> {

	public EntityRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new CustomRepositoryFactory<>(entityManager);
	}

	private static class CustomRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

		private final EntityManager entityManager;

		public CustomRepositoryFactory(EntityManager entityManager) {
			super(entityManager);

			this.entityManager = entityManager;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected Object getTargetRepository(RepositoryInformation information) {
			return isBaseJpaRepository(information.getRepositoryInterface())
					? (isHierarchicalJpaRepository(information.getRepositoryInterface())
							? new HierarchicalEntityRepositoryImpl(getEntityInformation(information.getDomainType()),
									entityManager)
							: new EntityRepositoryImpl<>(getEntityInformation(information.getDomainType()),
									entityManager))
					: super.getTargetRepository(information);
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return isBaseJpaRepository(metadata.getRepositoryInterface())
					? (isHierarchicalJpaRepository(metadata.getRepositoryInterface())
							? HierarchicalEntityRepositoryImpl.class : EntityRepositoryImpl.class)
					: super.getRepositoryBaseClass(metadata);
		}

		private boolean isHierarchicalJpaRepository(Class<?> repositoryInterface) {
			return ClassUtils.isAssignable(HierarchicalEntityRepository.class, repositoryInterface);
		}

		private boolean isBaseJpaRepository(Class<?> repositoryInterface) {
			return ClassUtils.isAssignable(EntityRepository.class, repositoryInterface);
		}

	}

}
