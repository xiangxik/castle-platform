package com.whenling.castle.plugin.repo;

import com.whenling.castle.plugin.model.PluginConfigEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface PluginConfigEntityRepository extends BaseJpaRepository<PluginConfigEntity, Long> {

	Long countByPluginId(String pluginId);

	PluginConfigEntity findFirstByPluginId(String pluginId);

	void deleteByPluginId(String pluginId);

}
