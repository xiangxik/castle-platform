package com.whenling.castle.plugin.oauth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.castle.plugin.oauth.model.OauthPlugin;

@Service
public class OauthService {

	@Autowired
	private Map<String, OauthPlugin> oauthPluginMap = new HashMap<>();

	public Iterable<OauthPlugin> getOauthPlugins() {
		return oauthPluginMap.values();
	}

	public OauthPlugin getOauthPlugin(String oauthPluginId) {
		return oauthPluginMap.get(oauthPluginId);
	}

}
