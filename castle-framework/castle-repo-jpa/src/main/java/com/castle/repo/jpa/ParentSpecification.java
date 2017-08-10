package com.castle.repo.jpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class ParentSpecification<T> implements Specification<T> {

	private T parent;

	private String parentProperty;

	public ParentSpecification(T parent, String parentProperty) {
		this.parent = parent;
		this.parentProperty = parentProperty;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		return parent == null ? cb.isNull(root.get(parentProperty)) : cb.equal(root.get(parentProperty), parent);
	}

}
