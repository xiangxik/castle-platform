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

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;
import com.whenling.castle.repo.domain.Result;
import com.whenling.sample.entity.AdminEntity;
import com.whenling.sample.repo.AdminRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String show() {
		return "/admin/index";
	}

	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public String selectPage() {
		return "/admin/select";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ResponseBody
	public Iterable<AdminEntity> doList(Predicate predicate) {
		return adminRepository.findAll(predicate);
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<AdminEntity> doPage(Predicate predicate, Pageable pageable) {
		return adminRepository.findAll(predicate, pageable);
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") AdminEntity admin, Model model) {
		model.addAttribute("admin", admin);
		return "/admin/edit";
	}

	@RequestMapping(value = "/password/{id}", method = RequestMethod.GET)
	public String password(@PathVariable("id") AdminEntity admin, Model model) {
		model.addAttribute("admin", admin);
		return "/admin/password";
	}

	@RequestMapping(value = "/password/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Result doPassword(@PathVariable("id") AdminEntity admin, Model model, String newPassword) {
		if (Strings.isNullOrEmpty(newPassword)) {
			return Result.validateError().message("密码不能为空");
		}
		admin.setPassword(passwordEncoder.encode(newPassword));

		adminRepository.save(admin);

		return Result.success();
	}

	@RequestMapping(value = { "/add", "/edit" }, method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("admin", new AdminEntity());
		return "/admin/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result doSave(@ModelAttribute @Valid AdminEntity admin, BindingResult bindingResult, String newPassword) {
		if (bindingResult.hasErrors()) {
			return Result.validateError();
		}

		AdminEntity otherAdmin = adminRepository.findByUsername(admin.getUsername());
		if (admin.isNew()) {
			if (otherAdmin != null) {
				return Result.validateError().message("该账号已存在");
			}
		} else {
			if (!Objects.equal(otherAdmin.getId(), admin.getId())) {
				return Result.validateError().message("该账号已存在");
			}
		}

		if (Strings.isNullOrEmpty(newPassword)) {
			if (admin.isNew()) {
				return Result.validateError().message("密码不能为空");
			}
		} else {
			admin.setPassword(passwordEncoder.encode(newPassword));
		}

		adminRepository.save(admin);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids[]") Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				adminRepository.delete(id);
			}
		}

		return Result.success();
	}

}
