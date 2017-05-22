package com.whenling.castle.website.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.castle.data.entity.ContactUs;
import com.whenling.castle.data.repo.ContactUsRepository;
import com.whenling.castle.repo.domain.Result;

@Controller
@RequestMapping("/contact_us")
public class ContactUsController {

	@Autowired
	private ContactUsRepository contactUsRepository;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Result doSave(@ModelAttribute @Valid ContactUs contactUs, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.validateError().error(bindingResult.getAllErrors());
		}

		contactUsRepository.save(contactUs);

		return Result.success();
	}

}
