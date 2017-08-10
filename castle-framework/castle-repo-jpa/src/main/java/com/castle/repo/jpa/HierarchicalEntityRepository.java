package com.castle.repo.jpa;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.castle.repo.domain.Node;
import com.castle.repo.domain.Tree;
import com.querydsl.core.types.Predicate;

@NoRepositoryBean
public interface HierarchicalEntityRepository<T extends HierarchicalEntity<?, I, T>, I extends Serializable> extends EntityRepository<T, I> {

	List<T> findRoots();

	List<T> findAllChildren(T root);

	Tree<T> findByRoot(T root);

	Tree<T> findTree(Predicate predicate);

	Tree<T> findTree(Predicate predicate, Node<T> singleRoot);

	public T sort(T source, T target, String action);
}
