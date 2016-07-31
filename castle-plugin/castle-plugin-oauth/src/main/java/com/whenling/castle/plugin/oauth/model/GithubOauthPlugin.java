package com.whenling.castle.plugin.oauth.model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.whenling.castle.plugin.oauth.service.OauthUserService;

/**
 * https://developer.github.com/v3/oauth/
 * 
 * @author ken
 *
 */
@Component("githubOauthPlugin")
public class GithubOauthPlugin extends OauthPlugin {

	@Value("${info.siteUrl?:http://mdmadmin.whenling.com}")
	private String siteUrl;

	@Autowired
	private OauthUserService oauthUserService;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public String getAuthorizationUrl() {
		return "https://github.com/login/oauth/authorize";
	}

	@Override
	public Map<String, Object> getAuthorizationParameterMap() {
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("client_id", getClientId());
		parameterMap.put("redirect_uri", getRedirectUri());
		return parameterMap;
	}

	@Override
	public String getAccessToken(String code) {
		Assert.hasText(code);
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("client_id", getClientId());
		parameterMap.put("client_secret", getClientSecret());
		parameterMap.put("code", code);
		String accessTokenResponse = post("https://github.com/login/oauth/access_token", parameterMap);
		List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(accessTokenResponse, Charset.forName("utf-8"));
		Map<String, Object> result = new HashMap<>();
		for (NameValuePair nameValuePair : nameValuePairs) {
			result.put(nameValuePair.getName(), nameValuePair.getValue());
		}
		return getParameter(nameValuePairs, "access_token");
	}

	@Override
	public OauthUserEntity getOauthUser(String accessToken) {
		Assert.hasText(accessToken);
		Map<String, Object> apiParameterMap = new HashMap<>();
		apiParameterMap.put("access_token", accessToken);
		String apiResponse = get("https://api.github.com/user", apiParameterMap);
		Map<String, Object> jsonObject = null;
		try {
			jsonObject = objectMapper.readValue(apiResponse, MapType.construct(HashMap.class, SimpleType.construct(String.class), SimpleType.construct(Object.class)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String userId = jsonObject.get("id").toString();
		OauthUserEntity oauthUser = oauthUserService.findByOauthPluginIdAndUserId(getId(), userId);
		if (oauthUser == null) {
			oauthUser = oauthUserService.newEntity();
			oauthUser.setOauthPluginId(getId());
			oauthUser.setUserId(userId);
			oauthUser.setUsername((String) jsonObject.get("login"));
			oauthUser.setName((String) jsonObject.get("name"));
			oauthUser.setAvatarUrl((String) jsonObject.get("avatar_url"));
		}
		return oauthUser;
	}

	@Override
	public String getName() {
		return "Github";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public String getAuthor() {
		return "ken";
	}

	@Override
	public String getSettingView() {
		return "app.view.plugin.GithubOauthForm";
	}

	@Override
	public String getSiteUrl() {
		return siteUrl;
	}

	@Override
	public String getInstallUrl() {
		return "/oauth/github/install";
	}

	@Override
	public String getUninstallUrl() {
		return "/oauth/github/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "/oauth/github/setting";
	}

}
