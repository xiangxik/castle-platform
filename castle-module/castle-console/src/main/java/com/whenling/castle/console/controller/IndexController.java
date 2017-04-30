package com.whenling.castle.console.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whenling.castle.console.entity.Admin;
import com.whenling.castle.console.security.AdminDetailsService.CurrentUserDetails;
import com.whenling.castle.security.CurrentUser;

@Controller
@RequestMapping
public class IndexController {

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String indexPage(Model model) {
		return "classpath:/META-INF/templates/index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "classpath:/META-INF/templates/login";
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboardPage(@CurrentUser Admin currentUser, Model model) {
		return "classpath:/META-INF/templates/dashboard";
	}

	@RequestMapping(value = "/console", method = RequestMethod.GET)
	public String dashboardPage(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
		model.addAttribute("currentUser", currentUserDetails == null ? null : currentUserDetails.getCustomUser());
		return "classpath:/META-INF/templates/console";
	}

}
