package com.whenling.castle.repo.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;

public class BaseJpaRepositoryImpl<T, I extends Serializable> extends QueryDslJpaRepository<T, I>
		implements BaseJpaRepository<T, I> {

	public BaseJpaRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

	public BaseJpaRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager,
			EntityPathResolver resolver) {
		super(entityInformation, entityManager, resolver);
	}

}
