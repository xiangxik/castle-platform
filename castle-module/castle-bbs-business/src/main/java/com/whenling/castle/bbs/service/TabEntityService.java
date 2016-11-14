package com.whenling.castle.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whenling.castle.bbs.entity.TabEntity;
import com.whenling.castle.bbs.repo.TabEntityRepository;
import com.whenling.castle.main.service.EntityService;

@Service
public class TabEntityService extends EntityService<TabEntity, Long> {

	@Autowired
	private TabEntityRepository tabEntityRepository;
	
	public TabEntity findByCode(String code) {
		return tabEntityRepository.findByCode(code);
	}
	
}
