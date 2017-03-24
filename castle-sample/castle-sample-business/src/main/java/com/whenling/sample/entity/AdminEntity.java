package com.whenling.sample.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.whenling.castle.repo.jpa.DataEntity;

@Entity
@Table(name = "sys_admin")
public class AdminEntity extends DataEntity<AdminEntity, Long> {

	private static final long serialVersionUID = -3613817204223653639L;

	@NotNull
	private String username;

	private String password;

	@NotNull
	private String name;

	private Date lastLoginDate;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}