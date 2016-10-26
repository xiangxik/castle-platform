package com.whenling.plugin.oauth.domain;

import java.util.Map;

public class OauthPlugin {

	private Map<String, Object> authorizationParameterMap;
	private String AuthorizationUrl;

	public Map<String, Object> getAuthorizationParameterMap() {
		return authorizationParameterMap;
	}

	public void setAuthorizationParameterMap(Map<String, Object> authorizationParameterMap) {
		this.authorizationParameterMap = authorizationParameterMap;
	}

	public String getAuthorizationUrl() {
		return AuthorizationUrl;
	}

	public void setAuthorizationUrl(String authorizationUrl) {
		AuthorizationUrl = authorizationUrl;
	}
}
