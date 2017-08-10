package com.castle.plugin.core.config;

import com.castle.repo.jpa.EntityRepository;

public interface PluginConfigRepository extends EntityRepository<PluginConfigEntity, Long> {

	PluginConfigEntity findByPluginId(String pluginId);

}
