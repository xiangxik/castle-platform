package com.castle.repo.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.ClassUtils;

import com.castle.repo.domain.OrganizationBean;

public class EntityRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {

	@Value("${jpa.supportOrg?:false}")
	private Boolean supportOrg;

	@Autowired(required = false)
	private WithOrgAware<? extends OrganizationBean> withOrgAware;

	public EntityRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
	}

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new CustomRepositoryFactory<>(entityManager, supportOrg, withOrgAware);
	}

	private static class CustomRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

		private final EntityManager entityManager;
		private final Boolean supportOrg;
		private final WithOrgAware<? extends OrganizationBean> withOrgAware;

		public CustomRepositoryFactory(EntityManager entityManager, Boolean supportOrg, WithOrgAware<? extends OrganizationBean> withOrgAware) {
			super(entityManager);

			this.entityManager = entityManager;
			this.supportOrg = supportOrg;
			this.withOrgAware = withOrgAware;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected Object getTargetRepository(RepositoryInformation information) {
			return isBaseJpaRepository(information.getRepositoryInterface())
					? (isHierarchicalJpaRepository(information.getRepositoryInterface())
							? new HierarchicalEntityRepositoryImpl(getEntityInformation(information.getDomainType()), entityManager, supportOrg,
									withOrgAware)
							: new EntityRepositoryImpl<>(getEntityInformation(information.getDomainType()), entityManager, supportOrg, withOrgAware))
					: super.getTargetRepository(information);
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return isBaseJpaRepository(metadata.getRepositoryInterface())
					? (isHierarchicalJpaRepository(metadata.getRepositoryInterface()) ? HierarchicalEntityRepositoryImpl.class
							: EntityRepositoryImpl.class)
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
