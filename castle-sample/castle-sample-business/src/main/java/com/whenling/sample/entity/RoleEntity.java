package com.whenling.sample.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "tbl_role")
public class RoleEntity extends DataEntity<AdminEntity, Long> {

	private static final long serialVersionUID = 5007088407621589171L;

	/** 名称 */
	@NotNull
	@Size(max = 200)
	private String name;

	/** 是否内置 */
	@Column(nullable = false, updatable = false)
	private boolean isSystem;

	/** 描述 */
	@Size(max = 200)
	private String description;

	/** 权限 */
	@ElementCollection
	@CollectionTable(name = "tbl_role_authority")
	private List<String> authorities = new ArrayList<String>();

	/** 管理员 */
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private Set<AdminEntity> admins = new HashSet<AdminEntity>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSystem() {
		return isSystem;
	}

	public void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	public Set<AdminEntity> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<AdminEntity> admins) {
		this.admins = admins;
	}

}
