package com.whenling.castle.main.service;

import org.springframework.stereotype.Service;

import com.whenling.castle.main.entity.OrganizationEntity;

@Service
public class OrganizationEntityService extends EntityService<OrganizationEntity, Long> {

	public OrganizationEntity defaultCreate(String name, String code) {
		OrganizationEntity org = newEntity();
		org.setName(name);
		org.setCode(code);
		return save(org);
	}

}
