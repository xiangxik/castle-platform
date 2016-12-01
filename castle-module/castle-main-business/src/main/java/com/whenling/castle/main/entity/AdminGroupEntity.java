package com.whenling.castle.main.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.jpa.DataEntity;

/**
 * 管理组
 * 
 * @author kongxiangxi
 *
 */
@Entity
@Table(name = "sys_admin_group")
public class AdminGroupEntity extends DataEntity<AdminEntity, Long> {

	private static final long serialVersionUID = 356148307535670234L;

	@NotNull
	@Size(min = 2, max = 20)
	@Column(length = 50, nullable = false)
	private String name;

	@ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
	private Set<AdminEntity> admins = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_admin_group_scope")
	private Set<DepartmentEntity> scope = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_admin_group_data_authority")
	private Set<DataAuthorityEntity> dataAuthorities = new HashSet<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<AdminEntity> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<AdminEntity> admins) {
		this.admins = admins;
	}

	public Set<DepartmentEntity> getScope() {
		return scope;
	}

	public void setScope(Set<DepartmentEntity> scope) {
		this.scope = scope;
	}

	public Set<DataAuthorityEntity> getDataAuthorities() {
		return dataAuthorities;
	}

	public void setDataAuthorities(Set<DataAuthorityEntity> dataAuthorities) {
		this.dataAuthorities = dataAuthorities;
	}

}
