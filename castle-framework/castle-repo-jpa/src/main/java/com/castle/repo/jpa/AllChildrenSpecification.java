package com.castle.repo.jpa;

import java.io.Serializable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class AllChildrenSpecification<T extends HierarchicalEntity<?, I, T>, I extends Serializable> implements Specification<T> {

	private T current;

	private String treePathProperty;

	public AllChildrenSpecification(T current, String treePathProperty) {
		this.current = current;
		this.treePathProperty = treePathProperty;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		String rootPath = current.getTreePath() + current.getId();
		return cb.like(root.get(treePathProperty), rootPath + "%");
	}

}
