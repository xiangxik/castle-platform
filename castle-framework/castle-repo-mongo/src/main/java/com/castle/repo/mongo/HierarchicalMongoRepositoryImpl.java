package com.castle.repo.mongo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;

import com.castle.repo.domain.Node;
import com.castle.repo.domain.SortNoComparator;
import com.castle.repo.domain.Tree;
import com.castle.repo.domain.TreeHelper;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;

public class HierarchicalMongoRepositoryImpl<T extends HierarchicalDoc<?, T>> extends BaseMongoRepositoryImpl<T>
		implements HierarchicalMongoRepository<T> {

	public HierarchicalMongoRepositoryImpl(MongoEntityInformation<T, String> entityInformation, MongoOperations mongoOperations) {
		super(entityInformation, mongoOperations);
	}

	@Override
	public <S extends T> S save(S entity) {
		String beforeModifiedTreePath = entity.getTreePath();

		entity = super.save(entity);

		String afterModifiedTreePath = entity.getTreePath();

		if (!Objects.equal(beforeModifiedTreePath, afterModifiedTreePath)) {
			// 修改后的父级的子级，要把当前entity添加进去
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

			// 改变层级关系，原来的父级的子级要去掉当前entity
			if (!Strings.isNullOrEmpty(beforeModifiedTreePath)
					&& !Objects.equal(beforeModifiedTreePath, HierarchicalBeforeConvertListener.TREE_PATH_SEPARATOR)) {

				String sourceParentId = StringUtils.substringAfterLast(
						StringUtils.removeEnd(beforeModifiedTreePath, HierarchicalBeforeConvertListener.TREE_PATH_SEPARATOR),
						HierarchicalBeforeConvertListener.TREE_PATH_SEPARATOR);
				T sourceParent = findOne(sourceParentId);
				List<T> sourceChildren = sourceParent.getChildren();
				if (sourceChildren != null) {
					sourceChildren.remove(entity);
					save(sourceParent);
				}
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
		query.addCriteria(Criteria.where("treePath").regex("^" + treePath + root.getId()));
		return findByQuery(query);
	}

	@Override
	public Tree<T> findByRoot(T root) {
		List<T> allChildren = root == null ? findAll() : findAllChildren(root);
		return TreeHelper.toTree(root, allChildren);
	}

	@Override
	public Tree<T> findTree(Predicate predicate) {
		return findTree(predicate, null);
	}

	@Override
	public Tree<T> findTree(Predicate predicate, Node<T> singleRoot) {
		List<T> allChildren = findAll(predicate);
		return TreeHelper.toTree(null, allChildren, singleRoot);
	}

}
