package com.castle.repo.jpa;

import java.io.Serializable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class HierarchicalEntityListener {

	/** 树路径分隔符 */
	private static final String TREE_PATH_SEPARATOR = ",";

	/**
	 * 
	 * @param entity
	 */
	@PrePersist
	public <U, I extends Serializable, T> void prePersist(HierarchicalEntity<U, I, T> entity) {
		@SuppressWarnings("unchecked")
		HierarchicalEntity<U, I, T> parent = (HierarchicalEntity<U, I, T>) entity.getParent();
		if (parent != null) {
			entity.setTreePath(parent.getTreePath() + parent.getId() + TREE_PATH_SEPARATOR);
		} else {
			entity.setTreePath(TREE_PATH_SEPARATOR);
		}
	}

	/**
	 * 
	 * @param entity
	 */
	@PreUpdate
	public <U, I extends Serializable, T> void preUpdate(HierarchicalEntity<U, I, T> entity) {
		@SuppressWarnings("unchecked")
		HierarchicalEntity<U, I, T> parent = (HierarchicalEntity<U, I, T>) entity.getParent();
		if (parent != null) {
			entity.setTreePath(parent.getTreePath() + parent.getId() + TREE_PATH_SEPARATOR);
		} else {
			entity.setTreePath(TREE_PATH_SEPARATOR);
		}
	}
}
