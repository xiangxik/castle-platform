package com.whenling.sample.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.whenling.castle.integration.ApplicationInitializer;
import com.whenling.sample.entity.AdminEntity;
import com.whenling.sample.entity.MenuItemEntity;
import com.whenling.sample.repo.AdminRepository;
import com.whenling.sample.repo.MenuItemRepository;

@Component
public class SampleApplicationInitializer extends ApplicationInitializer {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private MenuItemRepository menuItemRepository;

	@Override
	public void onRootContextRefreshed() {
		if (adminRepository.count() == 0) {
			AdminEntity admin = new AdminEntity();
			admin.setName("管理员");
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("asd123"));
			adminRepository.save(admin);
		}

		if (menuItemRepository.count() == 0) {
			int sortNo = 0;

			MenuItemEntity menuSystem = createMenu("系统管理", "system", "fa fa-desktop", null, null, sortNo++, null);
			createMenu("系统设置", "system_setting", "fa fa-cog", "/setting", null, sortNo++, menuSystem);
			createMenu("部门管理", "system_department", "fa fa-sitemap", "/department", null, sortNo++, menuSystem);
			createMenu("用户管理", "system_user", "fa fa-user", "/user", null, sortNo++, menuSystem);
			createMenu("管理员", "system_admin", "fa fa-user-circle", "/admin", null, sortNo++, menuSystem);
			createMenu("管理组", "system_admin_group", "fa fa-gavel", "/admingroup", null, sortNo++, menuSystem);

		}
	}

	private MenuItemEntity createMenu(String name, String code, String iconCls, String href, String parameters, Integer sortNo,
			MenuItemEntity parent) {
		MenuItemEntity item = new MenuItemEntity();
		item.setName(name);
		item.setCode(code);
		item.setIconCls(iconCls);
		item.setHref(href);
		item.setParameters(parameters);
		item.setSortNo(sortNo);
		item.setParent(parent);
		return menuItemRepository.save(item);
	}

}
