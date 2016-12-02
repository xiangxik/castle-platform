package com.whenling.castle.repo.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import com.querydsl.core.types.Predicate;
import com.whenling.castle.repo.domain.Tree;
import com.whenling.castle.repo.domain.TreeHelper;

public class HierarchicalJpaRepositoryImpl<T extends HierarchicalEntity<?, I, T>, I extends Serializable> extends BaseJpaRepositoryImpl<T, I>
		implements HierarchicalJpaRepository<T, I> {

	public HierarchicalJpaRepositoryImpl(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

	@Override
	public List<T> findRoots() {
		return getQuery(new ParentSpecification<>(null, "parent"), (Sort) null).getResultList();
	}

	@Override
	public List<T> findAllChildren(T root) {
		return getQuery(new AllChildrenSpecification<>(root, "treePath"), (Sort) null).getResultList();
	}

	@Override
	public Tree<T> findTree(Predicate predicate) {
		List<T> allChildren = findAll(predicate);
		return TreeHelper.toTree(null, allChildren);
	}

	@Override
	public Tree<T> findByRoot(T root) {
		List<T> allChildren = root == null ? findAll() : findAllChildren(root);
		return TreeHelper.toTree(root, allChildren);
	}

}
