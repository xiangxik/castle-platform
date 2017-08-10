package com.castle.repo.mongo;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.castle.repo.domain.Node;
import com.castle.repo.domain.Tree;
import com.querydsl.core.types.Predicate;

@NoRepositoryBean
public interface HierarchicalMongoRepository<T extends HierarchicalDoc<?, T>> extends BaseMongoRepository<T> {

	List<T> findRoots();

	List<T> findAllChildren(T root);

	Tree<T> findByRoot(T root);

	Tree<T> findTree(Predicate predicate);

	Tree<T> findTree(Predicate predicate, Node<T> singleRoot);
}
