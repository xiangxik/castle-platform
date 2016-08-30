package com.whenling.castle.repo.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.util.ClassUtils;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.whenling.castle.repo.domain.Lockedable;
import com.whenling.castle.repo.domain.LogicDeleteable;

public class BaseJpaRepositoryImpl<T, I extends Serializable> extends QueryDslJpaRepository<T, I> implements BaseJpaRepository<T, I> {

	public BaseJpaRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

	public BaseJpaRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager, EntityPathResolver resolver) {
		super(entityInformation, entityManager, resolver);
	}

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
}
