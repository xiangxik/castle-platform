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

	@Transactional(readOnly = true)
	public T findOne(I id) {
		return baseRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	public T getOne(I id) {
		return baseRepository.getOne(id);
	}

	@Transactional(readOnly = true)
	public boolean exists(I id) {
		return baseRepository.exists(id);
	}

	@Transactional(readOnly = true)
	public long count() {
		return baseRepository.count();
	}

	@Transactional(readOnly = true)
	public List<T> findAll() {
		return baseRepository.findAll();
	}

	@Transactional(readOnly = true)
	public List<T> findAll(Iterable<I> ids) {
		return baseRepository.findAll(ids);
	}

	@Transactional(readOnly = true)
	public List<T> findAll(Sort sort) {
		return baseRepository.findAll(sort);
	}

	@Transactional(readOnly = true)
	public Page<T> findAll(Pageable pageable) {
		return baseRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public T findOne(Predicate predicate) {
		return baseRepository.findOne(predicate);
	}

	@Transactional(readOnly = true)
	public Iterable<T> findAll(Predicate predicate) {
		return baseRepository.findAll(predicate);
	}

	@Transactional(readOnly = true)
	public Iterable<T> findAll(Predicate predicate, Sort sort) {
		return baseRepository.findAll(predicate, sort);
	}

	@Transactional(readOnly = true)
	public Iterable<T> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
		return baseRepository.findAll(predicate, orders);
	}

	@Transactional(readOnly = true)
	public Iterable<T> findAll(OrderSpecifier<?>... orders) {
		return baseRepository.findAll(orders);
	}

	@Transactional(readOnly = true)
	public Page<T> findAll(Predicate predicate, Pageable pageable) {
		return baseRepository.findAll(predicate, pageable);
	}

	@Transactional(readOnly = true)
	public long count(Predicate predicate) {
		return baseRepository.count(predicate);
	}

	@Transactional(readOnly = true)
	public boolean exists(Predicate predicate) {
		return baseRepository.exists(predicate);
	}
}
