package com.whenling.castle.console.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.querydsl.core.types.Predicate;
import com.whenling.castle.console.support.mvc.CrudController;
import com.whenling.castle.core.helper.Patterns;
import com.whenling.castle.data.entity.Admin;
import com.whenling.castle.data.entity.QAdmin;

@Controller
@RequestMapping("/admin")
public class AdminController extends CrudController<Admin, Long> {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public AdminController() {
		super(true);
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	@ResponseBody
	public Page<Admin> doPage(Predicate predicate, Pageable pageable) {
		return getBaseJpaRepository().findAll(predicate, pageable);
	}

	@Override
	protected void onValidate(Admin entity, BindingResult bindingResult) {
		super.onValidate(entity, bindingResult);
		String newPassword = getRequest().getParameter("newPassword");
		if (Strings.isNullOrEmpty(newPassword)) {
			if (entity.isNew()) {
				bindingResult.addError(new FieldError("entity", "password", "密码不能为空"));
			}
		} else {
			if (!Patterns.PATTENR_PASSWORD.matcher(newPassword).matches()) {
				bindingResult.addError(new FieldError("entity", "password", "密码不符合格式"));
			}
		}

		if (!Strings.isNullOrEmpty(entity.getEmail())) {
			Admin otherAdmin = getBaseJpaRepository().findOne(QAdmin.admin.email.eq(entity.getEmail()));
			if (otherAdmin != null && !Objects.equal(otherAdmin, entity)) {
				bindingResult.addError(new FieldError("entity", "email", "该邮箱已占用"));
			}
		}

		if (!Strings.isNullOrEmpty(entity.getMobile())) {
			Admin otherAdmin = getBaseJpaRepository().findOne(QAdmin.admin.mobile.eq(entity.getMobile()));
			if (otherAdmin != null && !Objects.equal(otherAdmin, entity)) {
				bindingResult.addError(new FieldError("entity", "mobile", "该手机号码已占用"));
			}
		}
	}

	@Override
	protected void onBeforeSave(Admin entity) {
		super.onBeforeSave(entity);
		String newPassword = getRequest().getParameter("newPassword");
		if (!Strings.isNullOrEmpty(newPassword)) {
			entity.setPassword(passwordEncoder.encode(newPassword));
		}
	}
}
