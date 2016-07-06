package com.whenling.castle.main.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.domain.Lockedable;
import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "sys_manager")
public class ManagerEntity extends DataEntity<ManagerEntity, Long>implements Lockedable, AreaSupport {

	private static final long serialVersionUID = -3554902892978919213L;

	@NotNull
	@Size(min = 4, max = 50)
	@Column(nullable = false, unique = true, length = 100)
	private String username;

	@Column(length = 225)
	private String password;

	@NotNull
	@Size(min = 2, max = 50)
	@Column(length = 100)
	private String name;

	@Pattern(regexp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$")
	@Column(length = 50)
	private String mobile;

	@Pattern(regexp = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
	@Column(length = 50)
	private String email;

	@Column(length = 500)
	private String photo;

	private Date lastLoginDate;

	@Column(length = 50)
	private String lastLoginIp;

	private boolean enabled = true;

	private boolean accountExpired = false;

	private boolean credentialsExpired = false;

	private boolean locked = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "org_id")
	private OrganizationEntity org;

	@ManyToOne(fetch = FetchType.LAZY)
	private AreaEntity area;

	@Column(length = 200)
	private String areaName;

	@OneToMany(mappedBy = "manager", fetch = FetchType.LAZY)
	private Set<ManagerRoleEntity> managerRoles = new HashSet<>();

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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public OrganizationEntity getOrg() {
		return org;
	}

	public void setOrg(OrganizationEntity org) {
		this.org = org;
	}

	public Set<ManagerRoleEntity> getManagerRoles() {
		return managerRoles;
	}

	public void setManagerRoles(Set<ManagerRoleEntity> managerRoles) {
		this.managerRoles = managerRoles;
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
		this.locked = true;
	}

	@Override
	public AreaEntity getArea() {
		return area;
	}

	@Override
	public void setArea(AreaEntity area) {
		this.area = area;
	}

	@Override
	public String getAreaName() {
		return areaName;
	}

	@Override
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}
