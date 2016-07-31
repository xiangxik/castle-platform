package com.whenling.castle.plugin.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.castle.main.service.EntityService;
import com.whenling.castle.plugin.oauth.model.OauthUserEntity;
import com.whenling.castle.plugin.oauth.repo.OauthUserRepository;

@Service
public class OauthUserService extends EntityService<OauthUserEntity, Long> {

	@Autowired
	private OauthUserRepository oauthUserRepository;

	public OauthUserEntity findByOauthPluginIdAndUserId(String oauthPluginId, String userId) {
		return oauthUserRepository.findByOauthPluginIdAndUserId(oauthPluginId, userId);
	}
}
