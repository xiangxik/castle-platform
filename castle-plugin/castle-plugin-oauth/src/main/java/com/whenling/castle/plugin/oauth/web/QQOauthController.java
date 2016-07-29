package com.whenling.castle.plugin.oauth.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.whenling.castle.plugin.oauth.model.QQOauthPlugin;
import com.whenling.castle.plugin.web.PluginController;

@Controller
@RequestMapping("/oauth/qq")
public class QQOauthController extends PluginController<QQOauthPlugin> {

}
