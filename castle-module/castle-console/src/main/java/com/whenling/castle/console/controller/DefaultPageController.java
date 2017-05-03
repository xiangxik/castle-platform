package com.whenling.castle.console.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.castle.console.entity.Admin;
import com.whenling.castle.console.entity.ContactUs;
import com.whenling.castle.console.repo.ContactUsRepository;
import com.whenling.castle.console.security.AdminDetailsService.CurrentUserDetails;
import com.whenling.castle.repo.domain.Result;
import com.whenling.castle.security.CurrentUser;

@Controller
@RequestMapping
public class DefaultPageController {

	@Autowired
	private ContactUsRepository contactUsRepository;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "classpath:/login";
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboardPage(@CurrentUser Admin currentUser, Model model) {
		return "classpath:/dashboard";
	}

	@RequestMapping(value = "/console", method = RequestMethod.GET)
	public String dashboardPage(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Model model) {
		model.addAttribute("currentUser", currentUserDetails == null ? null : currentUserDetails.getCustomUser());
		return "classpath:/console";
	}

	@RequestMapping(value = "/contact_us", method = RequestMethod.POST)
	@ResponseBody
	public Result contactUs(@ModelAttribute @Valid ContactUs contactUs, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.validateError().error(bindingResult.getAllErrors());
		}

		contactUsRepository.save(contactUs);
		return Result.success();
	}

}
