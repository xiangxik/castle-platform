package com.whenling.castle.plugin.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whenling.castle.plugin.controller.PluginController;
import com.whenling.castle.plugin.oauth.model.WeiboOauthPlugin;

@Controller
@RequestMapping("/oauth/weibo")
public class WeiboOauthController extends PluginController<WeiboOauthPlugin> {

}
