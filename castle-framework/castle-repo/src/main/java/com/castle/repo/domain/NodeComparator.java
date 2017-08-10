package com.castle.repo.domain;

import java.util.Comparator;

import com.google.common.base.MoreObjects;

public class NodeComparator<T extends Node<? extends Hierarchical<?>>> implements Comparator<T> {

	public static final NodeComparator<Node<?>> COMPARATOR = new NodeComparator<>();

	@Override
	public int compare(T o1, T o2) {
		Integer sortNo1 = o1 == null || o1.getData() == null ? 0
				: MoreObjects.firstNonNull(o1.getData().getSortNo(), 0);
		Integer sortNo2 = o2 == null || o2.getData() == null ? 0
				: MoreObjects.firstNonNull(o2.getData().getSortNo(), 0);

		return sortNo1 - sortNo2;
	}

}
