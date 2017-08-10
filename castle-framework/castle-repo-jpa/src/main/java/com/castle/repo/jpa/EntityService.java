package com.castle.repo.jpa;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

public class EntityService<T, I extends Serializable> {

	@Autowired
	private EntityRepository<T, I> baseJpaRepository;

	public EntityRepository<T, I> getRepository() {
		return baseJpaRepository;
	}

	public List<T> findAll() {
		return getRepository().findAll();
	}

	public List<T> findAll(Sort sort) {
		return getRepository().findAll(sort);
	}

	public List<T> findAll(Iterable<I> ids) {
		return getRepository().findAll(ids);
	}

	public <S extends T> List<S> save(Iterable<S> entities) {
		return getRepository().save(entities);
	}

	public void flush() {
		getRepository().flush();
	}

	public <S extends T> S saveAndFlush(S entity) {
		return getRepository().saveAndFlush(entity);
	}

	public void deleteInBatch(Iterable<T> entities) {
		getRepository().deleteInBatch(entities);
	}

	public void deleteAllInBatch() {
		getRepository().deleteAllInBatch();
	}

	public T getOne(I id) {
		return getRepository().getOne(id);
	}

	public <S extends T> List<S> findAll(Example<S> example) {
		return getRepository().findAll(example);
	}

	public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
		return getRepository().findAll(example, sort);
	}

	public Page<T> findAll(Pageable pageable) {
		return getRepository().findAll(pageable);
	}

	public <S extends T> S save(S entity) {
		return getRepository().save(entity);
	}

	public T findOne(I id) {
		return getRepository().findOne(id);
	}

	public boolean exists(I id) {
		return getRepository().exists(id);
	}

	public long count() {
		return getRepository().count();
	}

	public void delete(I id) {
		getRepository().delete(id);
	}

	public void delete(T entity) {
		getRepository().delete(entity);
	}

	public void delete(Iterable<? extends T> entities) {
		getRepository().delete(entities);
	}

	public void deleteAll() {
		getRepository().deleteAll();
	}

	public <S extends T> S findOne(Example<S> example) {
		return getRepository().findOne(example);
	}

	public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
		return getRepository().findAll(example, pageable);
	}

	public <S extends T> long count(Example<S> example) {
		return getRepository().count(example);
	}

	public <S extends T> boolean exists(Example<S> example) {
		return getRepository().exists(example);
	}

	public T findOne(Predicate predicate) {
		return getRepository().findOne(predicate);
	}

	public Iterable<T> findAll(Predicate predicate) {
		return getRepository().findAll(predicate);
	}

	public Iterable<T> findAll(Predicate predicate, Sort sort) {
		return getRepository().findAll(predicate, sort);
	}

	public Iterable<T> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
		return getRepository().findAll(predicate, orders);
	}

	public Iterable<T> findAll(OrderSpecifier<?>... orders) {
		return getRepository().findAll(orders);
	}

	public Page<T> findAll(Predicate predicate, Pageable pageable) {
		return getRepository().findAll(predicate, pageable);
	}

	public long count(Predicate predicate) {
		return getRepository().count(predicate);
	}

	public boolean exists(Predicate predicate) {
		return getRepository().exists(predicate);
	}

	public T initDomain() {
		return getRepository().initDomain();
	}
}
