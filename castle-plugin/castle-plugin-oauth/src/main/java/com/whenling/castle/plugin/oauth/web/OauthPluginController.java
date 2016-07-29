package com.whenling.castle.plugin.oauth.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.whenling.castle.plugin.model.PluginModel;
import com.whenling.castle.plugin.oauth.model.OauthPlugin;
import com.whenling.castle.plugin.oauth.service.OauthService;

@Controller
@RequestMapping("/oauth/plugin")
public class OauthPluginController {

	@Autowired
	private OauthService oauthService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Page<PluginModel> list(Pageable pageable) {
		Iterable<OauthPlugin> it = oauthService.getOauthPlugins();
		List<OauthPlugin> oauthPlugins = it == null ? new ArrayList<>() : Lists.newArrayList(it);
		oauthPlugins.sort(null);
		return new PageImpl<>(oauthPlugins).map(PluginModel::convert);
	}

}
