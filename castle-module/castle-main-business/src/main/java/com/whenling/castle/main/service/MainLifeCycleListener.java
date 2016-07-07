package com.whenling.castle.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.whenling.castle.integration.LifeCycleListener;
import com.whenling.castle.main.entity.ManagerEntity;
import com.whenling.castle.main.entity.MenuEntity;
import com.whenling.castle.main.entity.OrganizationEntity;
import com.whenling.castle.main.entity.RoleEntity;
import com.whenling.castle.main.entity.OrganizationEntity.OrgType;

@Service
public class MainLifeCycleListener implements LifeCycleListener {

	@Autowired
	private ManagerEntityService managerEntityService;

	@Autowired
	private OrganizationEntityService organizationEntityService;

	@Autowired
	private RoleEntityService roleEntityService;

	@Autowired
	private InitDataTools tools;

	@Transactional
	@Override
	public void onRootContextRefreshed() {
		if (roleEntityService.count() == 0) {
			for (int i = 0; i < 100; i++) {
				RoleEntity role = roleEntityService.newEntity();
				role.setName("管理员" + i);
				role.setCode("admin" + i);
				role.setLocked(true);
				roleEntityService.save(role);
			}
		}
		if (organizationEntityService.count() == 0) {
			OrganizationEntity org = organizationEntityService.newEntity();
			org.setName("广州当凌信息科技有限公司");
			org.setCode("whenling_company");
			org.setType(OrgType.company);
			organizationEntityService.save(org);

			ManagerEntity admin = managerEntityService.newEntity();
			admin.setUsername("admin");
			managerEntityService.changePassword(admin, null, "asd123");
			admin.setMobile("13265323553");
			admin.setEmail("ken@whenling.com");
			admin.setName("孔祥溪");
			admin.setOrg(org);
			managerEntityService.save(admin);

			org.setPrimaryLeader(admin);
			organizationEntityService.save(org);
		}

		if (!tools.existMenu()) {
			int sortNo = 0;

			MenuEntity systemManagement = tools.createMenuByParent("系统管理", "system_management", "fa fa-desktop", null,
					null, sortNo++, null);
			tools.createMenuByParent("参数设置", "parameter_setting", "fa fa-cogs", "app.view.setting.SettingList", null,
					sortNo++, systemManagement);
			tools.createMenuByParent("菜单管理", "menu_management", "fa fa-navicon", "app.view.menu.MenuList", null,
					sortNo++, systemManagement);
			tools.createMenuByParent("区域管理", "area_management", "fa fa-map", "app.view.area.AreaList", null, sortNo++,
					systemManagement);
			tools.createMenuByParent("字典管理", "dict_management", "fa fa-book", "app.view.dict.DictList", null, sortNo++,
					systemManagement);
			tools.createMenuByParent("系统日志", "system_log", "fa fa-building", "app.view.log.LogList", null, sortNo++,
					systemManagement);

			MenuEntity memberManagement = tools.createMenuByParent("会员管理", "member_management", "fa fa-users", null,
					null, sortNo++, null);
			tools.createMenuByParent("会员列表", "member_list", "fa fa-user", "app.view.member.MemberList", null, sortNo++,
					memberManagement);
			tools.createMenuByParent("会员级别", "member_rank", "fa fa-glass", "app.view.rank.RankList", null, sortNo++,
					memberManagement);

			MenuEntity adminManagement = tools.createMenuByParent("管理员管理", "admin_management", "fa fa-dashboard", null,
					null, sortNo++, null);
			tools.createMenuByParent("管理员列表", "admin_list", "fa fa-user", "app.view.manager.ManagerList", null,
					sortNo++, adminManagement);
			tools.createMenuByParent("组织机构列表 ", "org_list", "fa fa-object-group", "app.view.org.OrgList", null,
					sortNo++, adminManagement);
			tools.createMenuByParent("角色权限", "role_list", "fa fa-gavel", "app.view.role.RoleList", null, sortNo++,
					adminManagement);

		}

		System.out.println("admin root refreshed.");
	}

	@Override
	public void onServletContextRefreshed() {
		System.out.println("admin servlet refreshed.");
	}

}
