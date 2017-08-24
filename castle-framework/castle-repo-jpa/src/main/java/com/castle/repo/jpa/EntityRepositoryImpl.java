package com.castle.repo.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import com.castle.repo.domain.Defaultable;
import com.castle.repo.domain.Lockedable;
import com.castle.repo.domain.LogicDeleteable;
import com.castle.repo.domain.MultiTenant;
import com.castle.repo.domain.Tenant;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

public class EntityRepositoryImpl<T, I extends Serializable> extends QueryDslJpaRepository<T, I> implements EntityRepository<T, I> {

	private static final EntityPathResolver DEFAULT_ENTITY_PATH_RESOLVER = SimpleEntityPathResolver.INSTANCE;

	private JpaEntityInformation<T, I> entityInformation;
	private EntityManager entityManager;

	private final EntityPath<T> path;
	private final PathBuilder<T> builder;
	private final Querydsl querydsl;

	private Boolean multiTenant;
	private MultiTenantAware<? extends Tenant> multiTenantAware;

	public EntityRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager, Boolean multiTenant,
			MultiTenantAware<? extends Tenant> multiTenantAware) {
		this(entityInformation, entityManager, DEFAULT_ENTITY_PATH_RESOLVER);

		this.multiTenant = multiTenant;
		this.multiTenantAware = multiTenantAware;
	}

	public EntityRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager, EntityPathResolver resolver) {
		super(entityInformation, entityManager, resolver);

		this.entityInformation = entityInformation;
		this.entityManager = entityManager;

		this.path = resolver.createPath(entityInformation.getJavaType());
		this.builder = new PathBuilder<T>(path.getType(), path.getMetadata());
		this.querydsl = new Querydsl(entityManager, builder);
	}

	@Transactional
	@Override
	public <S extends T> S save(S entity) {
		if (multiTenant) {
			if (entity instanceof MultiTenant) {
				Tenant entityTenant = ((MultiTenant<?>) entity).getTenant();
				if (entityTenant != null) {
					Tenant tenant = multiTenantAware.getCurrentTenant();
					if (!Objects.equal(tenant, entityTenant)) {
						throw new RuntimeException();
					}
				}
			}
		}
		if (entity instanceof Defaultable) {
			if (((Defaultable) entity).isDefaulted()) {
				Specification<T> defaultedIsTrue = new PropertyBooleanSpecification<>("defaulted", true);
				if (!entityInformation.isNew(entity)) {
					defaultedIsTrue = Specifications.where(defaultedIsTrue)
							.and((root, query, cb) -> cb.notEqual(root.get("id"), entityInformation.getId(entity)));
				}

				List<T> defaultTrueEntities = findAll(defaultedIsTrue);
				if (defaultTrueEntities != null) {
					for (T defaultTrueEntity : defaultTrueEntities) {
						if (defaultTrueEntity instanceof Defaultable) {
							((Defaultable) defaultTrueEntity).setDefaulted(false);
							entityManager.merge(defaultTrueEntity);
						}
					}
				}
			}
		}
		return super.save(entity);
	}

	@Transactional
	@Override
	public void delete(T entity) {
		if (entity instanceof Lockedable) {
			if (((Lockedable) entity).isLocked()) {
				throw new RuntimeException("cannot delete the locked entity.");
			}
		}

		if (entity instanceof LogicDeleteable) {
			((LogicDeleteable) entity).markDeleted();
			super.save(entity);
		} else {
			super.delete(entity);
		}
	}

	@Override
	protected JPQLQuery<?> createQuery(Predicate... predicate) {
		JPQLQuery<?> query = null;
		Class<T> domainClass = getDomainClass();
		if (ClassUtils.isAssignable(LogicDeleteable.class, domainClass)) {
			BeanPath<T> logicDeleteBeanPath = new BeanPath<>(domainClass, StringUtils.uncapitalize(domainClass.getSimpleName()));
			BooleanExpression defaultedPropertyIsFalse = Expressions.booleanPath(logicDeleteBeanPath, "deleted").isFalse();
			if (predicate == null) {
				query = super.createQuery(defaultedPropertyIsFalse);
			} else {
				List<Predicate> predicates = Lists.newArrayList(predicate);
				predicates.add(defaultedPropertyIsFalse);
				query = super.createQuery(Iterables.toArray(predicates, Predicate.class));
			}
		} else {
			query = super.createQuery(predicate);
		}

		if (multiTenant) {
			if (ClassUtils.isAssignable(MultiTenant.class, domainClass)) {
				Tenant tenant = multiTenantAware.getCurrentTenant();
				if (tenant != null) {
					BeanPath<T> orgSupportBeanPath = new BeanPath<>(domainClass, StringUtils.uncapitalize(domainClass.getSimpleName()));
					BooleanExpression eqTenant = Expressions.path(Tenant.class, orgSupportBeanPath, "tenant").eq(tenant);

					if (predicate == null) {
						query = super.createQuery(eqTenant);
					} else {
						List<Predicate> predicates = Lists.newArrayList(predicate);
						predicates.add(eqTenant);
						query = super.createQuery(Iterables.toArray(predicates, Predicate.class));
					}
				}

			}
		}

		if (ClassUtils.isAssignable(DataEntity.class, domainClass)) {
			Sort createdDateDesc = new Sort(Direction.DESC, "createdDate");
			query = querydsl.applySorting(createdDateDesc, query);
		}
		if (ClassUtils.isAssignable(SortEntity.class, domainClass)) {
			Sort sortNoAsc = new Sort("sortNo");
			query = querydsl.applySorting(sortNoAsc, query);
		}
		return query;
	}

	@Override
	protected JPQLQuery<?> createCountQuery(Predicate predicate) {
		Class<T> domainClass = getDomainClass();
		if (ClassUtils.isAssignable(LogicDeleteable.class, domainClass)) {
			BeanPath<T> logicDeleteBeanPath = new BeanPath<>(domainClass, StringUtils.uncapitalize(domainClass.getSimpleName()));
			BooleanExpression defaultedPropertyIsFalse = Expressions.booleanPath(PathMetadataFactory.forProperty(logicDeleteBeanPath, "deleted"))
					.isFalse();
			predicate = new BooleanBuilder(predicate).and(defaultedPropertyIsFalse);
		}
		if (multiTenant) {
			if (ClassUtils.isAssignable(MultiTenant.class, domainClass)) {
				Tenant tenant = multiTenantAware.getCurrentTenant();
				if (tenant != null) {
					BeanPath<T> orgSupportBeanPath = new BeanPath<>(domainClass, StringUtils.uncapitalize(domainClass.getSimpleName()));
					BooleanExpression eqTenant = Expressions.path(Tenant.class, orgSupportBeanPath, "tenant").eq(tenant);
					predicate = new BooleanBuilder(predicate).and(eqTenant);
				}
			}
		}

		return super.createCountQuery(predicate);
	}

	@Override
	protected <S extends T> TypedQuery<S> getQuery(Specification<S> spec, Class<S> domainClass, Sort sort) {

		if (ClassUtils.isAssignable(LogicDeleteable.class, domainClass)) {
			spec = Specifications.where(spec).and((root, query, cb) -> cb.equal(root.get("deleted"), false));
		}

		if (multiTenant) {
			if (ClassUtils.isAssignable(MultiTenant.class, domainClass)) {
				Tenant tenant = multiTenantAware.getCurrentTenant();
				if (tenant != null) {
					spec = Specifications.where(spec).and((root, query, cb) -> cb.equal(root.get("tenant"), tenant));
				}
			}
		}

		// 排序实体
		if (ClassUtils.isAssignable(SortEntity.class, domainClass)) {

			Sort sortNoAsc = new Sort("sortNo");
			if (sort == null) {
				sort = sortNoAsc;
			} else {
				if (!Iterables.tryFind(sort, s -> {
					return Objects.equal(s.getProperty(), "sortNo");
				}).isPresent()) {
					sort.and(sortNoAsc);
				}

			}
		}

		// 数据默认按创建时间倒序排序
		if (ClassUtils.isAssignable(DataEntity.class, domainClass)) {
			Sort createdDateDesc = new Sort(Direction.DESC, "createdDate");

			if (sort == null) {
				sort = createdDateDesc;
			} else {
				if (!Iterables.tryFind(sort, s -> {
					return Objects.equal(s.getProperty(), "createdDate");
				}).isPresent()) {
					sort.and(createdDateDesc);
				}
			}
		}
		return super.getQuery(spec, domainClass, sort);
	}

	@Override
	protected <S extends T> TypedQuery<Long> getCountQuery(Specification<S> spec, Class<S> domainClass) {
		if (ClassUtils.isAssignable(LogicDeleteable.class, domainClass)) {
			spec = Specifications.where(spec).and((root, query, cb) -> cb.equal(root.get("deleted"), false));
		}
		return super.getCountQuery(spec, domainClass);
	}

	@Override
	public T initDomain() {
		try {
			return getDomainClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
