package com.castle.repo.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;

public class TreeHelper {

	public static <T extends Hierarchical<T>> List<T> listTree(List<T> all) {
		return listTree(all, null);
	}

	public static <T extends Hierarchical<T>> List<T> listTree(List<T> all, T parent) {
		List<T> result = new ArrayList<>();
		if (all != null) {
			for (T one : all) {
				if ((one.getParent() != null && Objects.equal(one.getParent(), parent))
						|| (one.getParent() == null && parent == null)) {
					result.add(one);
					result.addAll(listTree(all, one));
				}
			}
		}
		return result;
	}

	public static <T extends Hierarchical<T>> Tree<T> toTree(T current, List<T> nodes) {
		return toTree(current, nodes, null);
	}

	public static <T extends Hierarchical<T>> Tree<T> toSubTree(List<T> nodes) {
		Collections.sort(nodes, SortNoComparator.COMPARATOR);

		List<T> roots = new ArrayList<>();
		if (nodes != null) {
			for (T node : nodes) {
				if (!Iterables.tryFind(nodes, n -> Objects.equal(n.getParent(), node)).isPresent()) {
					roots.add(node);
				}
			}
		}

		List<Node<T>> rootNodes = new ArrayList<>();
		for (T root : roots) {
			rootNodes.add(toNode(root, findDirectSubordinates(root, nodes)));
		}

		return new TreeImpl<>(rootNodes);
	}

	public static <T extends Hierarchical<T>> Tree<T> toTree(T current, List<T> nodes, Node<T> singleRoot) {
		Collections.sort(nodes, SortNoComparator.COMPARATOR);
		List<Node<T>> directSubordinates = findDirectSubordinates(current, nodes);
		if (current != null) {
			Node<T> root = toNode(current, directSubordinates);
			directSubordinates = new ArrayList<>();
			directSubordinates.add(root);
		}

		if (singleRoot == null) {
			return new TreeImpl<>(directSubordinates);
		}

		singleRoot.setChildren(directSubordinates);
		List<Node<T>> newRoots = new ArrayList<>();
		newRoots.add(singleRoot);
		Tree<T> tree = new TreeImpl<>(newRoots);
		return tree;
	}

	public static <T extends Hierarchical<T>> void visitNodes(List<? extends Node<T>> nodes,
			Consumer<Node<T>> consumer) {
		if (nodes != null) {
			nodes.forEach((node) -> {
				consumer.accept(node);
				visitNodes(node.getChildren(), consumer);
			});
		}
	}

	public static <T extends Hierarchical<T>> List<Node<T>> findDirectSubordinates(T root, List<T> allChildren) {
		List<Node<T>> nodes = new ArrayList<>();
		for (T entity : allChildren) {
			if (Objects.equal(entity.getParent(), root)) {
				nodes.add(toNode(entity, findDirectSubordinates(entity, allChildren)));
			}
		}
		return nodes;
	}

	public static <T extends Hierarchical<T>> Node<T> toNode(T entity, List<Node<T>> children) {
		Node<T> node = new Node<>();
		node.setData(entity);
		if (children != null) {
			Collections.sort(children, NodeComparator.COMPARATOR);
			node.setChildren(children);
		}

		return node;
	}

}
