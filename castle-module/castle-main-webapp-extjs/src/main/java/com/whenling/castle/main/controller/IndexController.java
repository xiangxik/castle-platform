package com.whenling.castle.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping
public class IndexController {
	
	@RequestMapping(value = { "${path.admin}", "${path.admin}/", "${path.admin}/index" }, method = RequestMethod.GET)
	public String get() {
		return "/views/admin/index";
	}

}
