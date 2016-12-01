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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.whenling.castle.core.helper.Patterns;
import com.whenling.castle.repo.jpa.DataEntity;

/**
 * 管理员
 * 
 * @author kongxiangxi
 *
 */
@Entity
@Table(name = "sys_admin")
public class AdminEntity extends DataEntity<AdminEntity, Long> {

	private static final long serialVersionUID = -3536885696423643478L;

	@NotNull
	@Size(min = 2, max = 20)
	@Column(unique = true, nullable = false, length = 50)
	private String username;

	@JsonIgnore
	@NotNull
	@Column(nullable = false, length = 225)
	private String password;

	@NotNull
	@Size(min = 2, max = 20)
	@Column(nullable = false, length = 50)
	private String name;

	@Pattern(regexp = Patterns.REGEX_MOBILE)
	@Column(length = 50)
	private String mobile;

	@Pattern(regexp = Patterns.REGEX_MAIL)
	@Column(length = 50)
	private String email;

	private boolean root = false;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "sys_admin_to_group")
	private Set<AdminGroupEntity> groups = new HashSet<>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public Set<AdminGroupEntity> getGroups() {
		return groups;
	}

	public void setGroups(Set<AdminGroupEntity> groups) {
		this.groups = groups;
	}

}
