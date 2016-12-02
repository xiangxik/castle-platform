package com.whenling.castle.main.service;

import java.io.Serializable;
import java.util.List;

import com.querydsl.core.types.Predicate;
import com.whenling.castle.repo.domain.Tree;
import com.whenling.castle.repo.domain.TreeHelper;
import com.whenling.castle.repo.jpa.HierarchicalEntity;
import com.whenling.castle.repo.jpa.HierarchicalJpaRepository;

public abstract class HierarchicalEntityService<T extends HierarchicalEntity<?, I, T>, I extends Serializable>
		extends EntityService<T, I> {

	public List<T> findRoots() {
		return getHierarchicalJpaRepository().findRoots();
	}

	public List<T> findAllChildren(T root) {
		return getHierarchicalJpaRepository().findAllChildren(root);
	}

	public Tree<T> findByRoot(T root) {
		return getHierarchicalJpaRepository().findByRoot(root);
	}

	public Tree<T> findTree(Predicate predicate) {
		return getHierarchicalJpaRepository().findTree(predicate);
	}

	public Tree<T> toTree(T root, List<T> nodes) {
		return TreeHelper.toTree(root, nodes);
	}

	protected HierarchicalJpaRepository<T, I> getHierarchicalJpaRepository() {
		return ((HierarchicalJpaRepository<T, I>) baseRepository);
	}

}
