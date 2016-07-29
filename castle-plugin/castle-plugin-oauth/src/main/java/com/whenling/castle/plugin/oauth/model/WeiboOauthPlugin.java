package com.whenling.castle.plugin.oauth.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.whenling.castle.plugin.oauth.service.OauthUserService;

/**
 * http://open.weibo.com/wiki/%E6%8E%88%E6%9D%83%E6%9C%BA%E5%88%B6%E8%AF%B4%E6%
 * 98%8E
 * 
 * http://open.weibo.com/wiki/API%E6%96%87%E6%A1%A3_V2
 * 
 * @author ken
 *
 */
@Component("weiboOauthPlugin")
public class WeiboOauthPlugin extends OauthPlugin {

	@Autowired
	private OauthUserService oauthUserService;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${info.siteUrl?:http://mdmadmin.whenling.com}")
	private String siteUrl;

	@Override
	public String getAuthorizationUrl() {
		return "https://api.weibo.com/oauth2/authorize";
	}

	@Override
	public Map<String, Object> getAuthorizationParameterMap() {
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("client_id", getClientId());
		parameterMap.put("redirect_uri", getRedirectUri());
		parameterMap.put("response_type", "code");
		return parameterMap;
	}

	@Override
	public String getAccessToken(String code) {
		Assert.hasText(code);
		Map<String, Object> parameterMap = new HashMap<>();
		parameterMap.put("client_id", getClientId());
		parameterMap.put("client_secret", getClientSecret());
		parameterMap.put("grant_type", "authorization_code");
		parameterMap.put("redirect_uri", getRedirectUri());
		parameterMap.put("code", code);
		String accessTokenResponse = post("https://api.weibo.com/oauth2/access_token", parameterMap);

		Map<String, Object> jsonObject = null;
		try {
			jsonObject = objectMapper.readValue(accessTokenResponse, MapType.construct(HashMap.class, SimpleType.construct(String.class), SimpleType.construct(Object.class)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return (String) jsonObject.get("access_token");
	}

	@Override
	public OauthUser getOauthUser(String accessToken) {
		Assert.hasText(accessToken);
		Map<String, Object> apiParameterMap = new HashMap<>();
		apiParameterMap.put("access_token", accessToken);
		String apiResponse = post("https://api.weibo.com/oauth2/get_token_info", apiParameterMap);

		Map<String, Object> jsonObject = null;
		try {
			jsonObject = objectMapper.readValue(apiResponse, MapType.construct(HashMap.class, SimpleType.construct(String.class), SimpleType.construct(Object.class)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String uid = (String) jsonObject.get("uid");
		OauthUser oauthUser = oauthUserService.findByOauthPluginIdAndUserId(getId(), uid);
		if (oauthUser == null) {
			Map<String, Object> apiMap = new HashMap<>();
			apiMap.put("access_token", accessToken);
			apiMap.put("uid", uid);
			String apiString = get("https://api.weibo.com/2/users/show.json", apiMap);

			Map<String, Object> userObject = null;
			try {
				userObject = objectMapper.readValue(apiString, MapType.construct(HashMap.class, SimpleType.construct(String.class), SimpleType.construct(Object.class)));
			} catch (IOException e) {
				e.printStackTrace();
			}

			oauthUser = oauthUserService.newEntity();
			oauthUser.setOauthPluginId(getId());
			oauthUser.setUserId(uid);
			oauthUser.setUsername(uid);
			oauthUser.setName((String) userObject.get("screen_name"));
			oauthUser.setAvatarUrl((String) userObject.get("profile_image_url"));
		}
		return oauthUser;
	}

	@Override
	public String getName() {
		return "Weibo";
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
		return "app.view.plugin.WeiboOauthForm";
	}

	@Override
	public String getSiteUrl() {
		return siteUrl;
	}

	@Override
	public String getInstallUrl() {
		return "/admin/oauth/weibo/install";
	}

	@Override
	public String getUninstallUrl() {
		return "/admin/oauth/weibo/uninstall";
	}

	@Override
	public String getSettingUrl() {
		return "/admin/oauth/weibo/setting";
	}

}
