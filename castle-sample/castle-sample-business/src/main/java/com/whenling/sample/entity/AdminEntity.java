package com.whenling.sample.entity;

import java.util.Date;
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

import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "tbl_admin")
public class AdminEntity extends DataEntity<AdminEntity, Long> {

	private static final long serialVersionUID = -3613817204223653639L;

	/** 用户名 */
	@NotNull
	@Size(min = 2, max = 20)
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	private String username;

	/** 密码 */
	@NotNull
	@Pattern(regexp = "^[^\\s&\"<>]+$")
	@Size(min = 4, max = 20)
	@Column(nullable = false)
	private String password;

	/** E-mail */
	@Size(max = 200)
	private String email;

	/** 姓名 */
	@NotNull
	@Size(max = 200)
	private String name;

	/** 部门 */
	@Size(max = 200)
	private String department;

	/** 是否启用 */
	@Column(nullable = false)
	private boolean isEnabled;

	/** 是否锁定 */
	@Column(nullable = false)
	private boolean isLocked;

	/** 连续登录失败次数 */
	@Column(nullable = false)
	private int loginFailureCount = 0;

	/** 锁定日期 */
	private Date lockedDate;

	/** 最后登录日期 */
	private Date lastLoginDate;

	/** 最后登录IP */
	private String lastLoginIp;

	/** 角色 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tbl_admin_role")
	private Set<RoleEntity> roles = new HashSet<RoleEntity>();

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public int getLoginFailureCount() {
		return loginFailureCount;
	}

	public void setLoginFailureCount(int loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Set<RoleEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}

}