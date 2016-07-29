package com.whenling.castle.plugin.oauth.repo;

import com.whenling.castle.plugin.oauth.model.OauthUser;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface OauthUserRepository extends BaseJpaRepository<OauthUser, Long> {

	OauthUser findByOauthPluginIdAndUserId(String oauthPluginId, String userId);

}
