package com.whenling.castle.usercenter.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String registerPage() {
		return "/register";
	}

}
