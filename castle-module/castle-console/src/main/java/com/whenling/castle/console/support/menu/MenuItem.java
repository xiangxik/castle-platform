package com.whenling.castle.console.support.menu;

import java.util.List;

import com.whenling.castle.repo.domain.Hierarchical;

public class MenuItem implements Hierarchical<MenuItem> {

	private String name;

	private String code;

	private String iconCls;

	private String href;

	private String parameters;

	private MenuItem parent;

	private List<MenuItem> children;

	private Integer sortNo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public MenuItem getParent() {
		return parent;
	}

	public void setParent(MenuItem parent) {
		this.parent = parent;
	}

	public List<MenuItem> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

}
