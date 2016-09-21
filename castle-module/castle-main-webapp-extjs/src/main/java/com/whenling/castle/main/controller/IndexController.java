package com.whenling.castle.main.controller;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whenling.castle.security.CustomUserDetails;

@Controller
@RequestMapping
public class IndexController {

	@RequestMapping(value = { "${path.admin}", "${path.admin}/", "${path.admin}/index" }, method = RequestMethod.GET)
	public String get(Model model) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof CustomUserDetails) {
			try {
				model.addAttribute("currentUser", BeanUtils.getProperty(((CustomUserDetails<?, ?>) principal).getCustomUser(), "name"));
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
		} else {
			model.addAttribute("currentUser", "游客");
		}
		return "/views/admin/index";
	}

}
