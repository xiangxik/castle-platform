package com.castle.repo.domain;

import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import com.google.common.base.Strings;

public class TreeImpl<T extends Hierarchical<T>> implements Tree<T> {

	private List<Node<T>> roots;
	private Set<T> checked;
	private boolean checkable = false;
	private boolean expendAll = false;

	public TreeImpl(List<Node<T>> roots) {
		this.roots = roots;
	}

	@Override
	public List<Node<T>> getRoots() {
		return roots;
	}

	@Override
	public Set<T> getChecked() {
		return checked;
	}

	@Override
	public void setChecked(Set<T> checked) {
		this.checked = checked;
	}

	@Override
	public boolean isCheckable() {
		return checkable;
	}

	@Override
	public boolean isExpandAll() {
		return expendAll;
	}

	@Override
	public void makeCheckable() {
		this.checkable = true;
	}

	@Override
	public void makeExpandAll() {
		this.expendAll = true;
	}

	@Override
	public Tree<T> setTextProperty(String textProperty) {
		if (!Strings.isNullOrEmpty(textProperty)) {
			Tree.visitNodes(roots, node -> {
				try {
					node.setText((String) PropertyUtils.getProperty(node.getData(), textProperty));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
		return this;
	}

	@Override
	public Tree<T> setIconClsProperty(String iconClsProperty) {
		if (!Strings.isNullOrEmpty(iconClsProperty)) {
			Tree.visitNodes(roots, node -> {
				try {
					node.setIconCls((String) PropertyUtils.getProperty(node.getData(), iconClsProperty));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
		return this;
	}

}
