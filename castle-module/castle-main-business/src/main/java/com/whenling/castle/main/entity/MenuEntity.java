package com.whenling.castle.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.jpa.HierarchicalEntity;

@Entity
@Table(name = "sys_menu")
public class MenuEntity extends HierarchicalEntity<UserEntity, Long, MenuEntity> {

	private static final long serialVersionUID = 2487374581329950331L;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(nullable = false, length = 50)
	private String text;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(nullable = false, length = 50, unique = true)
	private String code;

	@Column(length = 50)
	private String iconCls;

	@Column(length = 100)
	private String view;

	@Column(length = 500)
	private String config;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

}
