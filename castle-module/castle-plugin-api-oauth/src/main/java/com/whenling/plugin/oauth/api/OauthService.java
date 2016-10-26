package com.whenling.plugin.oauth.api;

import com.whenling.main.domain.User;
import com.whenling.plugin.oauth.domain.OauthPlugin;

public interface OauthService {

	User auth(String oauthPluginId, String code);

	OauthPlugin getOauthPlugin(String oauthPluginId);
}
