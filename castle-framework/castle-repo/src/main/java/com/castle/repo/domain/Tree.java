package com.castle.repo.domain;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public interface Tree<T extends Hierarchical<T>> {

	List<Node<T>> getRoots();

	Set<T> getChecked();

	void setChecked(Set<T> checked);

	boolean isCheckable();

	boolean isExpandAll();

	void makeCheckable();

	void makeExpandAll();

	Tree<T> setTextProperty(String textProperty);

	Tree<T> setIconClsProperty(String iconClsProperty);

	static void visitNodes(List<? extends Node<?>> nodes, Consumer<Node<?>> consumer) {
		if (nodes != null) {
			nodes.forEach((node) -> {
				consumer.accept(node);
				visitNodes(node.getChildren(), consumer);
			});
		}
	}
}
