package com.whenling.sample.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;
import com.whenling.castle.repo.domain.Result;
import com.whenling.sample.entity.AdminEntity;
import com.whenling.sample.entity.UserEntity;
import com.whenling.sample.repo.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String show() {
		return "/user/index";
	}

	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public String selectPage() {
		return "/user/select";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Iterable<UserEntity> doList(Predicate predicate) {
		return userRepository.findAll(predicate);
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<UserEntity> doPage(Predicate predicate, Pageable pageable) {
		return userRepository.findAll(predicate, pageable);
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") AdminEntity user, Model model) {
		model.addAttribute("user", user);
		return "/user/edit";
	}

	@RequestMapping(value = "/password/{id}", method = RequestMethod.GET)
	public String password(@PathVariable("id") AdminEntity user, Model model) {
		model.addAttribute("user", user);
		return "/user/password";
	}

	@RequestMapping(value = "/password/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result doPassword(@PathVariable("id") UserEntity user, Model model, String newPassword) {
		if (Strings.isNullOrEmpty(newPassword)) {
			return Result.validateError().message("密码不能为空");
		}
		user.setPassword(passwordEncoder.encode(newPassword));

		userRepository.save(user);

		return Result.success();
	}

	@RequestMapping(value = { "/add", "/edit" }, method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("user", new UserEntity());
		return "/user/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result doSave(@ModelAttribute @Valid UserEntity user, BindingResult bindingResult, String newPassword) {
		if (bindingResult.hasErrors()) {
			return Result.validateError();
		}

		if (user.isNew()) {
			if (Strings.isNullOrEmpty(newPassword)) {
				return Result.validateError().message("密码不能为空");
			}
			user.setPassword(passwordEncoder.encode(newPassword));
		}

		userRepository.save(user);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids[]") Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				userRepository.delete(id);
			}
		}

		return Result.success();
	}

}
