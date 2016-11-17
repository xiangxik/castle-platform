package com.whenling.castle.main.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.main.service.SettingEntityService;
import com.whenling.castle.security.CurrentUser;

import ch.mfrey.jackson.antpathfilter.AntPathPropertyFilter;

@Controller
@RequestMapping
public class IndexController {

	@Autowired
	private SettingEntityService settingEntityService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@RequestMapping(value = { "${path.admin}", "${path.admin}/", "${path.admin}/login" }, method = RequestMethod.GET)
	public String loginPage() {
		return "/bootstrap/admin/login";
	}

	@RequestMapping(value = "${path.admin}/index", method = RequestMethod.GET)
	public String get(@CurrentUser UserEntity currentUser, Model model) {
		model.addAttribute("currentUser", currentUser);

		ObjectWriter objectWriter = objectMapper
				.writer(new SimpleFilterProvider().addFilter("antPathFilter", new AntPathPropertyFilter(new String[] { "*", "*.id", "*.name" })));
		try {
			model.addAttribute("setting", objectWriter.writeValueAsString(settingEntityService.get()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "/bootstrap/admin/index";
	}

}
