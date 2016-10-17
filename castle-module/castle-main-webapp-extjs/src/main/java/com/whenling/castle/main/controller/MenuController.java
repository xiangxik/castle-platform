package com.whenling.castle.main.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.querydsl.core.types.Predicate;
import com.whenling.castle.main.entity.MenuEntity;
import com.whenling.castle.main.entity.RoleEntity;
import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.main.service.MenuEntityService;
import com.whenling.castle.repo.domain.Node;
import com.whenling.castle.repo.domain.Tree;
import com.whenling.castle.repo.domain.TreeImpl;
import com.whenling.castle.security.CurrentUser;

@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuEntityService menuEntityService;

	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@ResponseBody
	public Tree<MenuEntity> tree(Predicate predicate, @CurrentUser UserEntity userEntity) {
		if (userEntity == null) {
			return null;
		} else {
			Tree<MenuEntity> tree = menuEntityService.findTree(predicate);
			Set<MenuEntity> menus = new HashSet<>();
			Set<RoleEntity> roles = userEntity.getRoles();
			for (RoleEntity role : roles) {
				if(Objects.equal(role.getCode(), "admin")) {
					tree.setIconClsProperty("iconCls");
					tree.setTextProperty("text");
					return tree;
				}
				menus.addAll(role.getMenus());
			}

			List<Node<MenuEntity>> newRoot = new ArrayList<>();

			if (tree.getRoots() != null) {
				for (Node<MenuEntity> node : tree.getRoots()) {
					if (node != null && menus != null) {
						MenuEntity menu = node.getData();
						if (menus.contains(menu)) {
							newRoot.add(node);
							node.setChildren(visit(node, menus));
						}
					}

				}
			}

			TreeImpl<MenuEntity> result = new TreeImpl<>(newRoot);

			result.setIconClsProperty("iconCls");
			result.setTextProperty("text");
			return result;
		}
	}

	protected List<Node<MenuEntity>> visit(Node<MenuEntity> node, Set<MenuEntity> menus) {
		if (node.getLeaf()) {
			return null;
		}

		List<Node<MenuEntity>> result = new ArrayList<>();
		if (node != null && menus != null) {
			for (Node<MenuEntity> child : node.getChildren()) {
				MenuEntity menu = child.getData();
				if (menus.contains(menu)) {
					result.add(child);
					child.setChildren(visit(child, menus));
				}
			}
		}
		return result;
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Page<MenuEntity> page(Predicate predicate, Pageable pageable) {
		return menuEntityService.findAll(predicate, pageable);
	}

}
