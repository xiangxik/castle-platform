package com.whenling.castle.plugin.oauth.repo;

import com.whenling.castle.plugin.oauth.model.OauthUserEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface OauthUserRepository extends BaseJpaRepository<OauthUserEntity, Long> {

	OauthUserEntity findByOauthPluginIdAndUserId(String oauthPluginId, String userId);

}
