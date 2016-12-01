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
	

}
