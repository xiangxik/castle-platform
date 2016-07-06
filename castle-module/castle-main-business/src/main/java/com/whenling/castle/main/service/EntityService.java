package com.whenling.castle.main.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.whenling.castle.repo.jpa.BaseEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public abstract class EntityService<T extends BaseEntity<I>, I extends Serializable> {

	protected BaseJpaRepository<T, I> baseRepository;
	protected final Class<T> entityClass;

	@Autowired
	public void setBaseRepository(BaseJpaRepository<T, I> baseRepository) {
		this.baseRepository = baseRepository;
	}

	@SuppressWarnings("unchecked")
	public EntityService() {
		ResolvableType resolvableType = ResolvableType.forClass(getClass());
		entityClass = (Class<T>) resolvableType.as(EntityService.class).getGeneric(0).resolve();
	}

	public T newEntity() {
		try {
			return entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	@Transactional
	public T save(T entity) {
		return baseRepository.save(entity);
	}

	@Transactional
	public <S extends T> List<S> save(Iterable<S> entities) {
		return baseRepository.save(entities);
	}

	@Transactional
	public <S extends T> S saveAndFlush(S entity) {
		return baseRepository.saveAndFlush(entity);
	}

	@Transactional
	public void delete(I id) {
		baseRepository.delete(id);
	}

	@Transactional
	public void delete(T entity) {
		baseRepository.delete(entity);
	}

	@Transactional
	public void delete(Iterable<T> entities) {
		baseRepository.delete(entities);
	}

	@Transactional
	public void deleteInBatch(Iterable<T> entities) {
		baseRepository.deleteInBatch(entities);
	}

	@Transactional
	public void deleteAllInBatch() {
		baseRepository.deleteAllInBatch();
	}

	public T findOne(I id) {
		return baseRepository.findOne(id);
	}

	public T getOne(I id) {
		return baseRepository.getOne(id);
	}

	public boolean exists(I id) {
		return baseRepository.exists(id);
	}

	public long count() {
		return baseRepository.count();
	}

	public List<T> findAll() {
		return baseRepository.findAll();
	}

	public List<T> findAll(Iterable<I> ids) {
		return baseRepository.findAll(ids);
	}

	public List<T> findAll(Sort sort) {
		return baseRepository.findAll(sort);
	}

	public Page<T> findAll(Pageable pageable) {
		return baseRepository.findAll(pageable);
	}

	public T findOne(Predicate predicate) {
		return baseRepository.findOne(predicate);
	}

	public Iterable<T> findAll(Predicate predicate) {
		return baseRepository.findAll(predicate);
	}

	public Iterable<T> findAll(Predicate predicate, Sort sort) {
		return baseRepository.findAll(predicate, sort);
	}

	public Iterable<T> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
		return baseRepository.findAll(predicate, orders);
	}

	public Iterable<T> findAll(OrderSpecifier<?>... orders) {
		return baseRepository.findAll(orders);
	}

	public Page<T> findAll(Predicate predicate, Pageable pageable) {
		return baseRepository.findAll(predicate, pageable);
	}

	public long count(Predicate predicate) {
		return baseRepository.count(predicate);
	}

	public boolean exists(Predicate predicate) {
		return baseRepository.exists(predicate);
	}
}
