package com.whenling.castle.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "sys_user_credentials")
public class UserCredentialsEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = -2146596304920130358L;

	@NotNull
	@Column(length = 50)
	private String type;

	@Column(length = 200)
	private String account;

	@NotNull
	@Column(length = 200)
	private String secret;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private UserEntity user;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
