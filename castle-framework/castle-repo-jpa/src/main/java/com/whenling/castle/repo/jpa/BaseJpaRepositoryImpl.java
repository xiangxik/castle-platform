package com.whenling.castle.repo.jpa;

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
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.PathMetadataFactory;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.whenling.castle.repo.domain.Defaultable;
import com.whenling.castle.repo.domain.Lockedable;
import com.whenling.castle.repo.domain.LogicDeleteable;

public class BaseJpaRepositoryImpl<T, I extends Serializable> extends QueryDslJpaRepository<T, I>
		implements BaseJpaRepository<T, I> {

	private JpaEntityInformation<T, I> entityInformation;
	private EntityManager entityManager;

	public BaseJpaRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.entityManager = entityManager;
	}

	public BaseJpaRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager,
			EntityPathResolver resolver) {
		super(entityInformation, entityManager, resolver);
		this.entityInformation = entityInformation;
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public <S extends T> S save(S entity) {
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
		Class<T> domainClass = getDomainClass();
		if (ClassUtils.isAssignable(LogicDeleteable.class, domainClass)) {
			BeanPath<T> logicDeleteBeanPath = new BeanPath<>(domainClass,
					StringUtils.uncapitalize(domainClass.getSimpleName()));
			BooleanExpression defaultedPropertyIsFalse = Expressions
					.booleanPath(PathMetadataFactory.forProperty(logicDeleteBeanPath, "deleted")).isFalse();
			if (predicate == null) {
				return super.createQuery(defaultedPropertyIsFalse);
			} else {
				List<Predicate> predicates = Lists.newArrayList(predicate);
				predicates.add(defaultedPropertyIsFalse);
				return super.createQuery(Iterables.toArray(predicates, Predicate.class));
			}
		}
		return super.createQuery(predicate);
	}

	@Override
	protected JPQLQuery<?> createCountQuery(Predicate predicate) {
		Class<T> domainClass = getDomainClass();
		if (ClassUtils.isAssignable(LogicDeleteable.class, domainClass)) {
			BeanPath<T> logicDeleteBeanPath = new BeanPath<>(domainClass,
					StringUtils.uncapitalize(domainClass.getSimpleName()));
			BooleanExpression defaultedPropertyIsFalse = Expressions
					.booleanPath(PathMetadataFactory.forProperty(logicDeleteBeanPath, "deleted")).isFalse();
			predicate = new BooleanBuilder(predicate).and(defaultedPropertyIsFalse);
		}
		return super.createCountQuery(predicate);
	}

	@Override
	protected <S extends T> TypedQuery<S> getQuery(Specification<S> spec, Class<S> domainClass, Sort sort) {

		if (ClassUtils.isAssignable(LogicDeleteable.class, domainClass)) {
			spec = Specifications.where(spec).and((root, query, cb) -> cb.equal(root.get("deleted"), false));
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
	public T newEntity() {
		try {
			return getDomainClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
