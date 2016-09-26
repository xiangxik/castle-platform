package com.whenling.castle.main.repo;

import com.whenling.castle.main.entity.SettingEntity;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public interface SettingEntityRepository extends BaseJpaRepository<SettingEntity, Long> {

	SettingEntity findTopByOrderByCreatedDateDesc();
	
}
