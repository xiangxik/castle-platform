package com.whenling.castle.repo.jpa;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseJpaRepository<T, I extends Serializable>
		extends JpaRepository<T, I>, QueryDslPredicateExecutor<T> {

}
