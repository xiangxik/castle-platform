package com.whenling.castle.plugin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.castle.main.service.EntityService;
import com.whenling.castle.plugin.model.PluginConfig;
import com.whenling.castle.plugin.repo.PluginConfigRepository;

@Service
public class PluginConfigService extends EntityService<PluginConfig, Long> {

	@Autowired
	private PluginConfigRepository pluginConfigRepository;

	public boolean pluginIdExists(String pluginId) {
		return pluginConfigRepository.countByPluginId(pluginId) > 0;
	}

	public PluginConfig findByPluginId(String pluginId) {
		return pluginConfigRepository.findFirstByPluginId(pluginId);
	}
}
