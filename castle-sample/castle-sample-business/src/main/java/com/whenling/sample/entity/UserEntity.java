package com.whenling.sample.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.whenling.castle.core.helper.Patterns;
import com.whenling.castle.repo.domain.Disabledable;
import com.whenling.castle.repo.domain.Lockedable;
import com.whenling.castle.repo.domain.LogicDeleteable;
import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "tbl_user")
public class UserEntity extends DataEntity<AdminEntity, Long> implements Lockedable, Disabledable, LogicDeleteable {

	private static final long serialVersionUID = -7165317453689472732L;

	/**
	 * 性别
	 */
	public enum Gender {

		/** 男 */
		male,

		/** 女 */
		female
	}

	/** 用户名 */
	@NotNull
	@Pattern(regexp = Patterns.REGEX_USERNAME)
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	private String username;

	/** 密码 */
	@NotNull
	@Pattern(regexp = Patterns.REGEX_PASSWORD)
	@Column(nullable = false)
	private String password;

	/** E-mail */
	@Size(max = 200)
	@Pattern(regexp = Patterns.REGEX_MAIL)
	private String email;

	@NotNull
	@Size(max = 200)
	private String name;

	/** 性别 */
	private Gender gender;

	/** 出生日期 */
	private Date birth;

	/** 地址 */
	@Size(max = 200)
	private String address;

	/** 邮编 */
	@Size(max = 200)
	private String zipCode;

	/** 电话 */
	@Size(max = 200)
	private String phone;

	/** 手机 */
	@Size(max = 200)
	@Pattern(regexp = Patterns.REGEX_MOBILE)
	private String mobile;

	/** 是否禁用 */
	@Column(nullable = false)
	private boolean disabled = false;

	/** 是否锁定 */
	@Column(nullable = false)
	private boolean locked = false;

	/** 是否删除 */
	@Column(nullable = false)
	private boolean deleted = false;

	/** 连续登录失败次数 */
	@Column(nullable = false)
	private int loginFailureCount = 0;

	/** 锁定日期 */
	private Date lockedDate;

	/** 注册IP */
	@Column(nullable = false, updatable = false)
	private String registerIp;

	/** 最后登录日期 */
	private Date lastLoginDate;

	/** 最后登录IP */
	private String lastLoginIp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	private DepartmentEntity department;

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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
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

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}

	@Override
	public void markDeleted() {
		this.deleted = true;
	}

	@Override
	public void markDisabled() {
		this.disabled = true;
	}

	@Override
	public void markLocked() {
		this.locked = true;
	}

}
