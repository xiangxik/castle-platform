package com.whenling.castle.console.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "tbl_admin")
public class Admin extends DataEntity<Admin, Long> implements Lockedable, Disabledable, LogicDeleteable {

	private static final long serialVersionUID = -5778996032468144613L;
	/** 用户名 */
	@NotNull
	@Size(min = 2, max = 20)
	@Pattern(regexp = Patterns.REGEX_USERNAME)
	@Column(nullable = false, updatable = false, unique = true, length = 100)
	private String username;

	/** 密码 */
	// @NotNull
	// @Pattern(regexp = Patterns.REGEX_PASSWORD)
	// @Size(min = 4, max = 20)
	@Column(nullable = false)
	private String password;

	/** E-mail */
	@Size(max = 200)
	@Pattern(regexp = Patterns.REGEX_MAIL)
	private String email;

	/** 手机号码 */
	@Size(max = 200)
	@Pattern(regexp = Patterns.REGEX_MOBILE)
	private String mobile;

	/** 姓名 */
	@NotNull
	@Size(max = 200)
	private String name;

	private String photo;

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

	/** 最后登录日期 */
	private Date lastLoginDate;

	/** 最后登录IP */
	private String lastLoginIp;

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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
