package com.whenling.castle.plugin.oauth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "plugin_oauth_user")
public class OauthUserEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = -3724865400156658638L;

	@Column(nullable = false, updatable = false, length = 100)
	private String oauthPluginId;

	private String userId;

	@Column(length = 200)
	private String username;

	@Column(length = 200)
	private String name;

	@Column(length = 500)
	private String avatarUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", nullable = false)
	private UserEntity owner;

	public String getOauthPluginId() {
		return oauthPluginId;
	}

	public void setOauthPluginId(String oauthPluginId) {
		this.oauthPluginId = oauthPluginId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public UserEntity getOwner() {
		return owner;
	}

	public void setOwner(UserEntity owner) {
		this.owner = owner;
	}

}
