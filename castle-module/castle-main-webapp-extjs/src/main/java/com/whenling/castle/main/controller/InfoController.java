package com.whenling.castle.main.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app_info")
public class InfoController {

	@Value("${info.system_name?:dashboard}")
	private String systemName;

	@Value("${info.system_version?:1.0}")
	private String systemVersion;

	@Value("${info.company_name?:whenling}")
	private String companyName;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> get() {
		Map<String, Object> appInfo = new HashMap<>();
		appInfo.put("system_name", systemName);
		appInfo.put("system_version", systemVersion);
		appInfo.put("company_name", companyName);
		return appInfo;
	}

}
