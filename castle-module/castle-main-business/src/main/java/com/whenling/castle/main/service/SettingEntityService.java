package com.whenling.castle.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.castle.main.entity.SettingEntity;
import com.whenling.castle.main.repo.SettingEntityRepository;

@Service
public class SettingEntityService extends EntityService<SettingEntity, Long> {

	@Autowired
	private SettingEntityRepository settingEntityRepository;

	public SettingEntity get() {
		SettingEntity settingEntity = settingEntityRepository.findTopByOrderByCreatedDateDesc();
		return settingEntity == null ? newEntity() : settingEntity;
	}

}
