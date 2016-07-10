package com.whenling.castle.usercenter.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.whenling.castle.usercenter.api.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Reference
	private UserService userService;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String loginPage() {
		userService.test();
		return "/login";
	}

}
