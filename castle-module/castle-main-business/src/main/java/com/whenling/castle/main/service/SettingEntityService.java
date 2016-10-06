package com.whenling.castle.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.castle.main.entity.SettingEntity;
import com.whenling.castle.main.repo.SettingEntityRepository;

@Service
public class SettingEntityService extends EntityService<SettingEntity, Long> {

	@Autowired
	private SettingEntityRepository settingEntityRepository;

	private SettingEntity settingEntity;

	public SettingEntity get() {
		if (settingEntity == null) {
			settingEntity = settingEntityRepository.findTopByOrderByCreatedDateDesc();
			if (settingEntity == null) {
				settingEntity = newEntity();
			}
		}
		return settingEntity;
	}

	@Override
	public SettingEntity save(SettingEntity entity) {
		settingEntity = super.save(entity);
		return settingEntity;
	}

}
