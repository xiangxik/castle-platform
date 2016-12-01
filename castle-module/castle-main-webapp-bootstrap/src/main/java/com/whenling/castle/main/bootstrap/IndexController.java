package com.whenling.castle.main.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whenling.castle.main.entity.AdminEntity;
import com.whenling.castle.main.service.MenuEntityService;
import com.whenling.castle.security.CurrentUser;

@Controller
@RequestMapping("${path.admin}")
public class IndexController {
	
	@Autowired
	private MenuEntityService menuEntityService;

	@RequestMapping(value = { "", "/", "/login" }, method = RequestMethod.GET)
	public String loginPage() {
		return "/bootstrap/admin/login";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexPage(@CurrentUser AdminEntity currentUser, Model model) {
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("menuRoots", menuEntityService.findRoots());
		return "/bootstrap/admin/index";
	}
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboardPage(Model model) {
		return "/bootstrap/admin/dashboard";
	}

}
