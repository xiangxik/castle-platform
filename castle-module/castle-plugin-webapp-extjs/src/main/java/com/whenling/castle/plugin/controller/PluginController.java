package com.whenling.castle.plugin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.castle.plugin.entity.Plugin;
import com.whenling.castle.plugin.entity.PluginConfigEntity;
import com.whenling.castle.plugin.service.PluginConfigEntityService;
import com.whenling.castle.repo.domain.Result;

public class PluginController<T extends Plugin> {

	@Autowired
	private PluginConfigEntityService pluginConfigService;

	@Autowired
	private T plugin;

	/**
	 * 安装
	 */
	@RequestMapping(value = "/install", method = RequestMethod.POST)
	public @ResponseBody Result install() {
		if (!plugin.getIsInstalled()) {
			PluginConfigEntity pluginConfig = pluginConfigService.newEntity();
			pluginConfig.setPluginId(plugin.getId());
			pluginConfig.setIsEnabled(false);
			pluginConfigService.save(pluginConfig);
		}
		return Result.success();
	}

	/**
	 * 卸载
	 */
	@RequestMapping(value = "/uninstall", method = RequestMethod.POST)
	public @ResponseBody Result uninstall() {
		if (plugin.getIsInstalled()) {
			PluginConfigEntity pluginConfig = plugin.getPluginConfig();
			pluginConfigService.delete(pluginConfig);
		}
		return Result.success();
	}

	/**
	 * 设置
	 */
	@RequestMapping(value = "/setting", method = RequestMethod.GET)
	public @ResponseBody PluginConfigEntity setting(ModelMap model) {
		PluginConfigEntity pluginConfig = plugin.getPluginConfig();
		return pluginConfig;
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Result update(@ModelAttribute("pluginConfig") @Valid PluginConfigEntity pluginConfig, BindingResult result, Model model) {

		preUpdate(pluginConfig, result, model);

		if (result.hasErrors()) {
			return Result.validateError();
		}

		pluginConfigService.save(pluginConfig);

		return Result.success();
	}

	protected void preUpdate(PluginConfigEntity pluginConfig, BindingResult result, Model model) {

	}

	public PluginConfigEntityService getPluginConfigService() {
		return pluginConfigService;
	}

	public T getPlugin() {
		return plugin;
	}

}
