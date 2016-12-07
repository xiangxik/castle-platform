package com.whenling.castle.main.extjs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.querydsl.core.types.Predicate;
import com.whenling.castle.main.entity.AdminEntity;
import com.whenling.castle.main.entity.MenuEntity;
import com.whenling.castle.main.service.MenuEntityService;
import com.whenling.castle.repo.domain.Node;
import com.whenling.castle.repo.domain.Tree;
import com.whenling.castle.security.CurrentUser;

@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuEntityService menuEntityService;

	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@ResponseBody
	public Tree<MenuEntity> tree(Predicate predicate, @CurrentUser AdminEntity adminEntity) {
		if (adminEntity == null) {
			return null;
		} else {
			return menuEntityService.findTree(predicate).setTextProperty("name").setIconClsProperty("iconCls");
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
