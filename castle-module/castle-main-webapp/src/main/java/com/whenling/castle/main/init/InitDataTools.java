package com.whenling.castle.main.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.whenling.castle.main.entity.MenuEntity;
import com.whenling.castle.main.entity.SettingEntity;
import com.whenling.castle.main.service.MenuEntityService;
import com.whenling.castle.main.service.SettingEntityService;

@Component
public class InitDataTools {

	@Autowired
	private MenuEntityService menuEntityService;
	
	@Autowired
	private SettingEntityService settingEntityService;

	public void setting(String siteName, String logo, String adminLoginBanner, String phone1, String phone2, String email, String address) {
		SettingEntity settingEntity = settingEntityService.get();
		settingEntity.setSiteName(siteName);
		settingEntity.setLogo(logo);
		settingEntity.setAdminLoginBanner(adminLoginBanner);
		settingEntity.setPhone1(phone1);
		settingEntity.setPhone2(phone2);
		settingEntity.setEmail(email);
		settingEntity.setAddress(address);
		settingEntityService.save(settingEntity);
	}
	
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
