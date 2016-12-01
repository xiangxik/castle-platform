package com.whenling.castle.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.jpa.HierarchicalEntity;

@Entity
@Table(name = "sys_menu")
public class MenuEntity extends HierarchicalEntity<AdminEntity, Long, MenuEntity> {

	private static final long serialVersionUID = 2487374581329950331L;

	@NotNull
	@Size(max = 50)
	@Column(nullable = false, length = 50)
	private String name;

	@NotNull
	@Size(max = 50)
	@Column(nullable = false, length = 50, unique = true)
	private String code;

	@Column(length = 50)
	private String iconCls;

	@Column(length = 100)
	private String href;

	@Column(length = 500)
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
