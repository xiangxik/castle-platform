package com.whenling.castle.main.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "sys_manager_role")
public class ManagerRoleEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = -3198810065611533451L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id", nullable = false)
	private ManagerEntity manager;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private RoleEntity role;

	public ManagerRoleEntity() {
	}

	public ManagerRoleEntity(ManagerEntity manager, RoleEntity role) {
		this.manager = manager;
		this.role = role;
	}

	public ManagerEntity getManager() {
		return manager;
	}

	public void setManager(ManagerEntity manager) {
		this.manager = manager;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

}
