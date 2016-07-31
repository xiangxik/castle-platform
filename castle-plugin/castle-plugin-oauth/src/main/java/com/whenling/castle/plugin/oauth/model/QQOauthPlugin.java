package com.whenling.castle.plugin.oauth.model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
 * http://wiki.connect.qq.com/oauth2-0%e7%ae%80%e4%bb%8b
 * 
 * @author ken
 *
 */
@Component("qqOauthPlugin")
public class QQOauthPlugin extends OauthPlugin {

	@Value("${info.siteUrl?:http://mdmadmin.whenling.com}")
	private String siteUrl;

	@Autowired
	private OauthUserService oauthUserService;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public String getAuthorizationUrl() {
		return "https://graph.qq.com/oauth2.0/authorize";
	}

	@Override
	public Map<String, Object> getAuthorizationParameterMap() {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("response_type", "code");
		parameters.put("client_id", getClientId());
		parameters.put("redirect_uri", getRedirectUri());
		parameters.put("state", "test");
		return parameters;
	}

	@Override
	public String getAccessToken(String code) {
		Assert.hasText(code);
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("grant_type", "authorization_code");
		parameterMap.put("client_id", getClientId());
		parameterMap.put("client_secret", getClientSecret());
		parameterMap.put("code", code);
		parameterMap.put("redirect_uri", getRedirectUri());
		String responseString = get("https://graph.qq.com/oauth2.0/token", parameterMap);

		List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(responseString, Charset.forName("utf-8"));
		Map<String, Object> result = new HashMap<>();
		for (NameValuePair nameValuePair : nameValuePairs) {
			result.put(nameValuePair.getName(), nameValuePair.getValue());
		}

		return getParameter(nameValuePairs, "access_token");
	}

	@Override
	public OauthUserEntity getOauthUser(String accessToken) {
		Assert.hasText(accessToken);
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("access_token", accessToken);
		String responseString = get("https://graph.qq.com/oauth2.0/me", parameterMap);
		responseString = StringUtils.trim(responseString);
		responseString = StringUtils.removeStartIgnoreCase(responseString, "callback(");
		responseString = StringUtils.removeEndIgnoreCase(responseString, ");");

		Map<String, Object> jsonObject = null;
		try {
			jsonObject = objectMapper.readValue(responseString, MapType.construct(HashMap.class, SimpleType.construct(String.class), SimpleType.construct(Object.class)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String openid = (String) jsonObject.get("openid");
		OauthUserEntity oauthUser = oauthUserService.findByOauthPluginIdAndUserId(getId(), openid);
		if (oauthUser == null) {
			Map<String, Object> apiMap = new HashMap<>();
			apiMap.put("access_token", accessToken);
			apiMap.put("oauth_consumer_key", (String) jsonObject.get("client_id"));
			apiMap.put("openid", openid);
			String apiString = get("https://graph.qq.com/user/get_user_info", apiMap);

			Map<String, Object> userObject = null;
			try {
				userObject = objectMapper.readValue(apiString, MapType.construct(HashMap.class, SimpleType.construct(String.class), SimpleType.construct(Object.class)));
			} catch (IOException e) {
				e.printStackTrace();
			}

			oauthUser = oauthUserService.newEntity();
			oauthUser.setOauthPluginId(getId());
			oauthUser.setUserId(openid);
			oauthUser.setUsername(openid);
			oauthUser.setName((String) userObject.get("nickname"));
			oauthUser.setAvatarUrl((String) userObject.get("figureurl_qq_2"));
		}

		return oauthUser;
	}

	@Override
	public String getName() {
		return "QQ";
	}

	@Override
	public String getVersion() {
		return "1";
	}

	@Override
	public String getAuthor() {
		return "ken";
	}

	@Override
	public String getSettingView() {
		return "app.view.plugin.QQOauthForm";
	}

	@Override
	public String getSiteUrl() {
		return siteUrl;
	}

	@Override
	public String getInstallUrl() {
		return "/oauth/qq/install";
	}

	@Override
	public String getUninstallUrl() {
		return "/oauth/qq/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "/oauth/qq/setting";
	}

}
