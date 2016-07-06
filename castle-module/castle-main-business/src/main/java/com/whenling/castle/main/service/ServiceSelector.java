package com.whenling.castle.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Objects;

@Component
public class ServiceSelector {

	@Autowired(required = false)
	private List<EntityService<?, ?>> entityServices;

	public EntityService<?, ?> getEntityService(final Class<?> entityClass) {
		if (entityServices != null) {
			for (EntityService<?, ?> entityService : entityServices) {
				if (Objects.equal(entityService.getEntityClass(), entityClass)) {
					return entityService;
				}
			}
		}
		return null;
	}
}
