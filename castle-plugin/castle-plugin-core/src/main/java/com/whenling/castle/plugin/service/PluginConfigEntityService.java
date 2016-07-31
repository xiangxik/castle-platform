package com.whenling.castle.plugin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.castle.main.service.EntityService;
import com.whenling.castle.plugin.model.PluginConfigEntity;
import com.whenling.castle.plugin.repo.PluginConfigEntityRepository;

@Service
public class PluginConfigEntityService extends EntityService<PluginConfigEntity, Long> {

	@Autowired
	private PluginConfigEntityRepository pluginConfigRepository;

	public boolean pluginIdExists(String pluginId) {
		return pluginConfigRepository.countByPluginId(pluginId) > 0;
	}

	public PluginConfigEntity findByPluginId(String pluginId) {
		return pluginConfigRepository.findFirstByPluginId(pluginId);
	}

	public void deleteByPluginId(String pluginId) {
		pluginConfigRepository.deleteByPluginId(pluginId);
	}

}
