package com.castle.repo.jpa;

import java.io.Serializable;
import java.util.List;

import com.castle.repo.domain.Node;
import com.castle.repo.domain.Tree;
import com.querydsl.core.types.Predicate;

public class HierarchicalEntityService<T extends HierarchicalEntity<?, I, T>, I extends Serializable>
		extends EntityService<T, I> {

	@Override
	public HierarchicalEntityRepository<T, I> getRepository() {
		return (HierarchicalEntityRepository<T, I>) super.getRepository();
	}

	public List<T> findRoots() {
		return getRepository().findRoots();
	}

	public List<T> findAllChildren(T root) {
		return getRepository().findAllChildren(root);
	}

	public Tree<T> findByRoot(T root) {
		return getRepository().findByRoot(root);
	}

	public Tree<T> findTree(Predicate predicate) {
		return getRepository().findTree(predicate);
	}

	public Tree<T> findTree(Predicate predicate, Node<T> singleRoot) {
		return getRepository().findTree(predicate, singleRoot);
	}

	public T sort(T source, T target, String action) {
		return getRepository().sort(source, target, action);
	}

}
