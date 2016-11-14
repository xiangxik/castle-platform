package com.whenling.bbs.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.whenling.bbs.api.TabService;
import com.whenling.bbs.domain.Tab;
import com.whenling.castle.bbs.entity.TabEntity;
import com.whenling.castle.bbs.service.TabEntityService;

@com.alibaba.dubbo.config.annotation.Service
@Service
public class TabServiceProvider implements TabService {

	@Autowired
	private TabEntityService tabEntityService;

	@Override
	public Tab findOne(Long id) {
		return toDomain(tabEntityService.findOne(id));
	}

	@Override
	public List<Tab> findAll() {
		return Lists.newArrayList(Lists.transform(tabEntityService.findAll(), this::toDomain));
	}

	public Tab toDomain(TabEntity entity) {
		Tab tab = new Tab();
		tab.setId(entity.getId());
		tab.setCode(entity.getCode());
		tab.setName(entity.getName());
		return tab;
	}

	@Override
	public Tab findByCode(String code) {
		return toDomain(tabEntityService.findByCode(code));
	}

}
