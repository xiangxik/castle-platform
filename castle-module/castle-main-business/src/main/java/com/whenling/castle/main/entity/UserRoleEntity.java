package com.whenling.castle.main.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "sys_user_role")
public class UserRoleEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = -3198810065611533451L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private UserEntity user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private RoleEntity role;

	public UserRoleEntity() {
	}

	public UserRoleEntity(UserEntity user, RoleEntity role) {
		this.user = user;
		this.role = role;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

}
