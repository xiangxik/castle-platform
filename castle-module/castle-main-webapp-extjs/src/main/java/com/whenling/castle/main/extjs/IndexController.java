package com.whenling.castle.main.extjs;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.whenling.castle.main.service.SettingEntityService;
import com.whenling.castle.security.CustomUserDetails;

import ch.mfrey.jackson.antpathfilter.AntPathPropertyFilter;

@Controller
@RequestMapping
public class IndexController {

	@Autowired
	private SettingEntityService settingEntityService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
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
		
		ObjectWriter objectWriter = objectMapper.writer(new SimpleFilterProvider().addFilter("antPathFilter",
							new AntPathPropertyFilter(new String[] { "*", "*.id", "*.name" })));
		try {
			model.addAttribute("setting", objectWriter.writeValueAsString(settingEntityService.get()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "/views/admin/index";
	}

}
