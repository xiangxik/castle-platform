package com.whenling.sample.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.whenling.castle.repo.jpa.HierarchicalEntity;

@Entity
@Table(name = "tbl_menu_item")
public class MenuItemEntity extends HierarchicalEntity<AdminEntity, Long, MenuItemEntity> {

	private static final long serialVersionUID = -4506364204676824312L;

	@NotNull
	private String name;
	
	@NotNull
	private String code;
	
	private String iconCls;
	private String href;
	private String parameters;

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
}
