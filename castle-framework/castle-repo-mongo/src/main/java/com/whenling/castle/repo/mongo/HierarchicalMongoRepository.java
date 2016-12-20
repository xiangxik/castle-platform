package com.whenling.castle.repo.mongo;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.core.types.Predicate;
import com.whenling.castle.repo.domain.Node;
import com.whenling.castle.repo.domain.Tree;

@NoRepositoryBean
public interface HierarchicalMongoRepository<T extends HierarchicalDoc<?, T>> extends BaseMongoRepository<T> {

	List<T> findRoots();

	List<T> findAllChildren(T root);

	Tree<T> findByRoot(T root);

	Tree<T> findTree(Predicate predicate);

	Tree<T> findTree(Predicate predicate, Node<T> singleRoot);
}
