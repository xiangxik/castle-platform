package com.whenling.sample.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Sets;
import com.querydsl.core.types.Predicate;
import com.whenling.castle.integration.webapp.json.PathFilter;
import com.whenling.castle.repo.domain.Result;
import com.whenling.castle.repo.domain.Tree;
import com.whenling.castle.repo.domain.TreeHelper;
import com.whenling.sample.entity.DepartmentEntity;
import com.whenling.sample.repo.DepartmentRepository;

@Controller
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private DepartmentRepository departmentRepository;

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String show(Model model) {
		model.addAttribute("departments", TreeHelper.listTree(departmentRepository.findAll(new Sort(new Order("sortNo")))));
		return "/department/index";
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	@PathFilter("*,*.*,*.*.id,*.*.title,*.*.name")
	public Tree<DepartmentEntity> doTree(@RequestParam(value = "checked", required = false) DepartmentEntity[] checked, Predicate predicate) {

		Tree<DepartmentEntity> tree = departmentRepository.findTree(predicate).setTextProperty("name");

		if (checked != null && checked.length > 0) {
			tree.setChecked(Sets.newHashSet(checked));
			tree.makeCheckable();
		}

		return tree;
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") DepartmentEntity department, Model model) {
		model.addAttribute("department", department);
		return "/department/edit";
	}

	@RequestMapping(value = "/add/{id}", method = RequestMethod.GET)
	public String addByParent(@PathVariable("id") DepartmentEntity parent, Model model) {
		return add(parent, model);
	}

	@RequestMapping(value = { "/add", "/edit" }, method = RequestMethod.GET)
	public String add(@RequestParam(name = "parent", required = false) DepartmentEntity parent, Model model) {
		DepartmentEntity department = new DepartmentEntity();
		department.setParent(parent);
		return edit(department, model);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result doSave(@ModelAttribute @Valid DepartmentEntity department, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.validateError();
		}

		departmentRepository.save(department);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids[]") Long[] ids) {
		if (ids != null) {
			for (Long id : ids) {
				departmentRepository.delete(id);
			}
		}

		return Result.success();
	}
}
