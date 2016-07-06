package com.whenling.castle.main.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.domain.Lockedable;
import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "sys_role")
public class RoleEntity extends DataEntity<ManagerEntity, Long>implements Lockedable {

	private static final long serialVersionUID = 5364423002312524895L;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(nullable = false, length = 100)
	private String name;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(nullable = false, length = 50)
	private String code;

	private boolean locked = false;

	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
	private Set<ManagerRoleEntity> managerRoles = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_role_menu")
	@OrderBy("sortNo asc")
	private Set<MenuEntity> menus = new HashSet<>();

	@ElementCollection
	@CollectionTable(name = "sys_role_authority")
	private List<String> authorities = new ArrayList<String>();

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

	public Set<ManagerRoleEntity> getManagerRoles() {
		return managerRoles;
	}

	public void setManagerRoles(Set<ManagerRoleEntity> managerRoles) {
		this.managerRoles = managerRoles;
	}

	public Set<MenuEntity> getMenus() {
		return menus;
	}

	public void setMenus(Set<MenuEntity> menus) {
		this.menus = menus;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isLocked() {
		return locked;
	}

	@Override
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public void markLocked() {
		locked = true;
	}

}
