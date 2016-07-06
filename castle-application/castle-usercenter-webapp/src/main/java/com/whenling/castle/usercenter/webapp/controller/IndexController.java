package com.whenling.castle.usercenter.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whenling.castle.integration.dubbo.Consumer;
import com.whenling.castle.usercenter.api.UserService;

@Controller
@RequestMapping
public class IndexController {

	@Consumer
	private UserService userService;

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String get() {
		userService.test();
		return "/index";
	}

}
