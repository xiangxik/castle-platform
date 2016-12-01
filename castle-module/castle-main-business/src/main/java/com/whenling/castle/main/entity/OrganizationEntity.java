package com.whenling.castle.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.whenling.castle.core.helper.Patterns;
import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "sys_organization")
public class OrganizationEntity extends DataEntity<AdminEntity, Long> {

	private static final long serialVersionUID = -7362271235798217688L;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(nullable = false, length = 50)
	private String code;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(nullable = false, length = 100)
	private String name;

	/** logo */
	@Column(length = 200)
	private String logo;

	/** 介绍 */
	@Lob
	private String introduction;

	@Size(max = 100)
	@Column(length = 200)
	private String address;

	@Pattern(regexp = Patterns.REGEX_MOBILE)
	@Column(length = 50)
	private String phone;

	@Pattern(regexp = Patterns.REGEX_MAIL)
	@Column(length = 50)
	private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "primary_leader_id")
	private UserEntity primaryLeader;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "secondary_leader_id")
	private UserEntity secondaryLeader;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserEntity getPrimaryLeader() {
		return primaryLeader;
	}

	public void setPrimaryLeader(UserEntity primaryLeader) {
		this.primaryLeader = primaryLeader;
	}

	public UserEntity getSecondaryLeader() {
		return secondaryLeader;
	}

	public void setSecondaryLeader(UserEntity secondaryLeader) {
		this.secondaryLeader = secondaryLeader;
	}
}
