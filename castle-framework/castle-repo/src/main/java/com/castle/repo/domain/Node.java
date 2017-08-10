package com.castle.repo.domain;

import java.util.List;

public class Node<T extends Hierarchical<T>> {

	private T data;
	private List<Node<T>> children;

	private Boolean checked;
	private Boolean expanded;

	private String text;

	private String iconCls;

	public boolean getLeaf() {
		List<Node<T>> children = getChildren();
		return children == null || children.size() == 0;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public void setChildren(List<Node<T>> children) {
		this.children = children;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

}
