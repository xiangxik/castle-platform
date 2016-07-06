package com.whenling.castle.repo.jpa;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.core.types.Predicate;
import com.whenling.castle.repo.domain.Tree;

@NoRepositoryBean
public interface HierarchicalJpaRepository<T extends HierarchicalEntity<?, I, T>, I extends Serializable>
		extends BaseJpaRepository<T, I> {

	List<T> findRoots();

	List<T> findAllChildren(T root);

	Tree<T> findByRoot(T root);

	Tree<T> findTree(Predicate predicate);

	Tree<T> toTree(T current, List<T> nodes);
}
