package com.whenling.castle.repo.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;

import com.querydsl.core.types.Predicate;
import com.whenling.castle.repo.domain.SortNoComparator;
import com.whenling.castle.repo.domain.Tree;
import com.whenling.castle.repo.domain.TreeHelper;

public class HierarchicalMongoRepositoryImpl<T extends HierarchicalDoc<?, T>> extends BaseMongoRepositoryImpl<T>
		implements HierarchicalMongoRepository<T> {

	public HierarchicalMongoRepositoryImpl(MongoEntityInformation<T, String> entityInformation, MongoOperations mongoOperations) {
		super(entityInformation, mongoOperations);
	}

	@Override
	public <S extends T> S save(S entity) {
		entity = super.save(entity);
		T parent = entity.getParent();
		if (parent != null) {
			List<T> children = parent.getChildren();
			if (children == null) {
				children = new ArrayList<>();
			}
			if (!children.contains(entity)) {
				children.add(entity);
				children.sort(SortNoComparator.COMPARATOR);

				parent.setChildren(children);
				save(parent);
			}
		}
		return entity;
	}

	@Override
	public List<T> findRoots() {
		Query query = new Query();
		query.addCriteria(Criteria.where("parent").is(null));
		return findByQuery(query);
	}

	@Override
	public List<T> findAllChildren(T root) {
		if (root == null) {
			return findAll();
		}
		String treePath = root.getTreePath();
		Query query = new Query();
		query.addCriteria(Criteria.where("treePath").regex("^" + treePath));
		return findByQuery(query);
	}

	@Override
	public Tree<T> findByRoot(T root) {
		List<T> allChildren = root == null ? findAll() : findAllChildren(root);
		return TreeHelper.toTree(root, allChildren);
	}

	@Override
	public Tree<T> findTree(Predicate predicate) {
		List<T> allChildren = findAll(predicate);
		return TreeHelper.toTree(null, allChildren);
	}

}
