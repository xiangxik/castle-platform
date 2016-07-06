package com.whenling.castle.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.whenling.castle.main.entity.MenuEntity;

@Component
public class InitDataTools {

	@Autowired
	private MenuEntityService menuEntityService;

	public boolean existMenu() {
		return menuEntityService.count() > 0;
	}

	public MenuEntity createMenuByParent(String label, String code, String iconCls, String view, String config,
			Integer sortNo, MenuEntity parent) {
		MenuEntity menu = menuEntityService.newEntity();
		menu.setText(label);
		menu.setCode(code);
		menu.setIconCls(iconCls);
		menu.setView(view);
		menu.setConfig(config);
		menu.setSortNo(sortNo);
		menu.setParent(parent);
		menuEntityService.save(menu);
		return menu;
	}

	public MenuEntity createMenuByParentCode(String label, String code, String iconCls, String view, String config,
			Integer sortNo, String parentCode) {
		return createMenuByParent(label, parentCode, iconCls, view, config, sortNo,
				Strings.isNullOrEmpty(parentCode) ? null : menuEntityService.findByCode(parentCode));
	}

}
