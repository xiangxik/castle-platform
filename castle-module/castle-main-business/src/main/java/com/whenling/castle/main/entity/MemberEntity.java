package com.whenling.castle.main.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.whenling.castle.repo.domain.Lockedable;
import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "sys_member")
public class MemberEntity extends DataEntity<ManagerEntity, Long>implements AreaSupport, Lockedable {

	private static final long serialVersionUID = 6091891837567884358L;

	@Column(length = 50)
	private String name;

	/**
	 * 年龄
	 */
	private Integer age;

	/** 邮箱 */
	@Column(length = 50)
	private String email;

	/** 手机 */
	@Column(length = 50)
	private String mobile;

	/** 头像 */
	@Column(length = 200)
	private String avatar;

	/** 性别 */
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private Sex sex;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	private boolean locked = false;

	@ManyToOne(fetch = FetchType.LAZY)
	private AreaEntity area;

	@Column(length = 200)
	private String areaName;

	public enum Sex {
		/** 男 */
		male,

		/** 女 */
		female
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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
