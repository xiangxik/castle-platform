package com.whenling.castle.cms.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.whenling.castle.cms.entity.SubscribeEntity;
import com.whenling.castle.main.service.EntityService;

@Service
public class SubscribeEntityService extends EntityService<SubscribeEntity, Long> {

	@Override
	public SubscribeEntity save(SubscribeEntity entity) {
		if (entity.isNew()) {
			entity.setCreatedDate(new Date());
		}
		return super.save(entity);
	}
}
