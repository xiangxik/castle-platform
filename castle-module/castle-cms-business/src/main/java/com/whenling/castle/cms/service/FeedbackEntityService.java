package com.whenling.castle.cms.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.whenling.castle.cms.entity.FeedbackEntity;
import com.whenling.castle.main.service.EntityService;

@Service
public class FeedbackEntityService extends EntityService<FeedbackEntity, Long> {

	@Override
	public FeedbackEntity save(FeedbackEntity entity) {
		if (entity.isNew()) {
			entity.setCreatedDate(new Date());
		}
		return super.save(entity);
	}
}
