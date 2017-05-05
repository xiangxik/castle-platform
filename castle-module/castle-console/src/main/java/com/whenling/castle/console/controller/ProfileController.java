package com.whenling.castle.console.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.whenling.castle.core.helper.Patterns;
import com.whenling.castle.data.entity.Admin;
import com.whenling.castle.data.entity.QAdmin;
import com.whenling.castle.data.repo.AdminRepository;
import com.whenling.castle.repo.domain.Result;
import com.whenling.castle.security.CurrentUser;

@Controller
@RequestMapping("/profile")
public class ProfileController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AdminRepository adminRepository;
	
	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String profilePage(@CurrentUser Admin currentUser, Model model) {
		model.addAttribute("currentUser", currentUser);
		return "classpath:/profile";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result saveProfile(@CurrentUser Admin currentUser, Model model, String name, String oldPassword,
			String newPassword, String email, String mobile) {
		List<ObjectError> objectErrors = new ArrayList<>();
		if (Strings.isNullOrEmpty(name)) {
			objectErrors.add(new FieldError("currentUser", "name", "姓名不能为空"));
		}

		if (!Strings.isNullOrEmpty(newPassword)) {
			boolean matched = passwordEncoder.matches(oldPassword, currentUser.getPassword());
			if (!matched) {
				objectErrors.add(new FieldError("currentUser", "oldPassword", "旧密码错误"));
			}
			if (!Patterns.PATTENR_PASSWORD.matcher(newPassword).matches()) {
				objectErrors.add(new FieldError("currentUser", "newPassword", "密码不符合格式"));
			}
			currentUser.setPassword(passwordEncoder.encode(newPassword));
		}

		if (!Strings.isNullOrEmpty(email)) {
			Admin otherAdmin = adminRepository.findOne(QAdmin.admin.email.eq(email));
			if (otherAdmin != null && !Objects.equal(otherAdmin, currentUser)) {
				objectErrors.add(new FieldError("currentUser", "email", "该邮箱已占用"));
			}
		}

		if (!Strings.isNullOrEmpty(mobile)) {
			Admin otherAdmin = adminRepository.findOne(QAdmin.admin.mobile.eq(mobile));
			if (otherAdmin != null && !Objects.equal(otherAdmin, currentUser)) {
				objectErrors.add(new FieldError("currentUser", "mobile", "该手机号码已占用"));
			}
		}

		if (objectErrors.size() > 0) {
			return Result.validateError().error(objectErrors);
		}

		currentUser.setName(name);
		currentUser.setEmail(email);
		currentUser.setMobile(mobile);
		adminRepository.save(currentUser);
		return Result.success();
	}

	@RequestMapping(value = "/photo", method = RequestMethod.POST)
	@ResponseBody
	public Result saveProfilePhoto(@CurrentUser Admin currentUser, Model model, String url) {
		if (Strings.isNullOrEmpty(url)) {
			return Result.failure();
		}

		currentUser.setPhoto(url);
		adminRepository.save(currentUser);
		return Result.success();
	}
}
