package com.castle.theme.adminlte.ztree;

import com.castle.repo.domain.Hierarchical;

public class ZTreeNode<T extends Hierarchical<T>> {

	private T data;

	private String text;
	private String iconUrl;
	private String iconCls;
	private Boolean checked;
	private Boolean expanded;

	public ZTreeNode(T data) {
		setData(data);
	}

	public T getData() {
		return data;
	}

	protected void setData(T data) {
		this.data = data;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
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
