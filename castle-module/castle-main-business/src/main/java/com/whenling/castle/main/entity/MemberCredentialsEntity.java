package com.whenling.castle.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "sys_member_credentials")
public class MemberCredentialsEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = -2146596304920130358L;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	private CredentialsType credentialsType;

	@Column(length = 200)
	private String userId;

	@NotNull
	@Column(length = 200)
	private String secret;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private MemberEntity member;

	public CredentialsType getCredentialsType() {
		return credentialsType;
	}

	public void setCredentialsType(CredentialsType credentialsType) {
		this.credentialsType = credentialsType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public MemberEntity getMember() {
		return member;
	}

	public void setMember(MemberEntity member) {
		this.member = member;
	}

	public enum CredentialsType {
		username_password, mobile_password, email_password, qq_oauth, wechat_oauth, sina_oauth, github_oauth
	}

}
