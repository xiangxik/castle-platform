package com.whenling.castle.main.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Sets;
import com.querydsl.core.types.Predicate;
import com.whenling.castle.main.entity.MenuEntity;
import com.whenling.castle.main.entity.RoleEntity;
import com.whenling.castle.main.service.MenuEntityService;
import com.whenling.castle.main.service.RoleEntityService;
import com.whenling.castle.repo.domain.Result;
import com.whenling.castle.repo.domain.Tree;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleEntityService roleEntityService;

	@Autowired
	private MenuEntityService menuEntityService;

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Page<RoleEntity> doPage(Predicate predicate, Pageable pageable) {
		return roleEntityService.findAll(predicate, pageable);
	}

	@RequestMapping(value = "/menu", method = RequestMethod.GET)
	@ResponseBody
	public Tree<MenuEntity> getMenu(@RequestParam(value = "id") RoleEntity entity) {
		Tree<MenuEntity> tree = menuEntityService.findByRoot(null);
		tree.setIconClsProperty("iconCls");
		tree.setTextProperty("text");
		tree.setChecked(entity.getMenus());
		tree.makeCheckable();
		tree.makeExpandAll();
		return tree;
	}

	@RequestMapping(value = "/menu", method = RequestMethod.POST)
	@ResponseBody
	public Result doMenu(@RequestParam(value = "id") RoleEntity entity, @RequestParam(value = "menus") MenuEntity[] menus) {
		entity.setMenus(Sets.newHashSet(menus));
		roleEntityService.save(entity);
		return Result.success();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result doSave(@ModelAttribute @Valid RoleEntity manager, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.validateError();
		}

		roleEntityService.save(manager);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "id")
	@ResponseBody
	public Result delete(@RequestParam(value = "id") RoleEntity entity) {
		roleEntityService.delete(entity);
		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "ids")
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids") RoleEntity[] entities) {
		for (RoleEntity entity : entities) {
			roleEntityService.delete(entity);
		}

		return Result.success();
	}

}
