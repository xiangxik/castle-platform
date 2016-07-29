package com.whenling.castle.plugin.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.castle.main.service.EntityService;
import com.whenling.castle.plugin.oauth.model.OauthUser;
import com.whenling.castle.plugin.oauth.repo.OauthUserRepository;

@Service
public class OauthUserService extends EntityService<OauthUser, Long> {

	@Autowired
	private OauthUserRepository oauthUserRepository;

	public OauthUser findByOauthPluginIdAndUserId(String oauthPluginId, String userId) {
		return oauthUserRepository.findByOauthPluginIdAndUserId(oauthPluginId, userId);
	}
}
