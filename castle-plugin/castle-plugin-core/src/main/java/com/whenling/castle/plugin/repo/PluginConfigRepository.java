package com.whenling.castle.plugin.repo;

import com.whenling.castle.plugin.model.PluginConfig;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface PluginConfigRepository extends BaseJpaRepository<PluginConfig, Long> {

	Long countByPluginId(String pluginId);

	PluginConfig findFirstByPluginId(String pluginId);

}
