package com.whenling.castle.usercenter.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.config.annotation.Reference;
import com.whenling.castle.usercenter.api.UserService;

@Controller
@RequestMapping
public class IndexController {

	@Reference
	private UserService userService;
	
	public IndexController() {
		System.out.println("index");
	}

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String get() {
		userService.test();
		return "/index";
	}

}
