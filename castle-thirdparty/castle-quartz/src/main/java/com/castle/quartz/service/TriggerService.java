package com.castle.quartz.service;

import org.springframework.stereotype.Service;

import com.castle.quartz.entity.TriggerEntity;
import com.castle.repo.jpa.EntityService;

@Service
public class TriggerService extends EntityService<TriggerEntity, String> {

}
