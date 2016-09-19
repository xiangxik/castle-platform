package com.whenling.castle.main.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;
import com.whenling.castle.main.entity.UserEntity;
import com.whenling.castle.main.service.UserEntityService;
import com.whenling.castle.repo.domain.Result;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserEntityService userEntityService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Page<UserEntity> doPage(Predicate predicate, Pageable pageable) {
		return userEntityService.findAll(predicate, pageable);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result doSave(@ModelAttribute @Valid UserEntity user, BindingResult bindingResult, String newPassword) {
		if (bindingResult.hasErrors()) {
			return Result.validateError();
		}

		if(user.isNew()) {
			if(Strings.isNullOrEmpty(newPassword)) {
				return Result.validateError();
			}
			user.setPassword(passwordEncoder.encode(newPassword));
		}

		userEntityService.save(user);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "id")
	@ResponseBody
	public Result delete(@RequestParam(value = "id") UserEntity entity) {
		userEntityService.delete(entity);
		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "ids")
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids") UserEntity[] entities) {
		for (UserEntity entity : entities) {
			userEntityService.delete(entity);
		}

		return Result.success();
	}

}
