package com.whenling.castle.plugin.oauth.web;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whenling.castle.main.service.UserEntityService;
import com.whenling.castle.plugin.oauth.model.OauthPlugin;
import com.whenling.castle.plugin.oauth.model.OauthUser;
import com.whenling.castle.plugin.oauth.service.OauthService;
import com.whenling.castle.plugin.oauth.service.OauthUserService;

@Controller
@RequestMapping("/oauth")
public class OauthController {

	@Autowired
	private OauthService oauthService;

	@Autowired
	private OauthUserService oauthUserService;

	@Autowired
	private UserEntityService userEntityService;

	@RequestMapping(value = "/authorization/{oauthPluginId}", method = RequestMethod.GET)
	public String redirectUsersToRequestAccess(@PathVariable("oauthPluginId") String oauthPluginId, RedirectAttributes redirectAttributes) {
		OauthPlugin oauthPlugin = oauthService.getOauthPlugin(oauthPluginId);
		redirectAttributes.addAllAttributes(oauthPlugin.getAuthorizationParameterMap());
		return "redirect:" + oauthPlugin.getAuthorizationUrl();
	}

	@RequestMapping(value = "/api/{oauthPluginId}", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
	public String getAccessTokenAndProcess(@PathVariable("oauthPluginId") String oauthPluginId, String code, HttpServletRequest request) {
		OauthPlugin oauthPlugin = oauthService.getOauthPlugin(oauthPluginId);
		String accessToken = oauthPlugin.getAccessToken(code);
		OauthUser oauthUser = oauthPlugin.getOauthUser(accessToken);
		if (oauthUser.isNew() || oauthUser.getOwner() == null) {
//			User user = userEntityService.newEntity();
//			user.setUsername(oauthUser.getUsername());
//			user.setName(oauthUser.getName());
//			user.setAvatar(oauthUser.getAvatarUrl());
//			userService.changePassword(user, null, "");
//			userService.save(user);
//			oauthUser.setOwner(user);
//			oauthUserService.save(oauthUser);
//			login(oauthUser, request);
			return "redirect:/center/user/account";
		} else {
//			login(oauthUser, request);
			return "redirect:/center";
		}
	}

//	private void login(OauthUser oauthUser, HttpServletRequest request) {
//		OauthUserToken token = new OauthUserToken(oauthUser, request.getRemoteHost(), true);
//		Subject subject = SecurityUtils.getSubject();
//		subject.login(token);
//	}

}
