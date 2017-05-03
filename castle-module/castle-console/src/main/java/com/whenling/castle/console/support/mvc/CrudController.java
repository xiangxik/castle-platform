package com.whenling.castle.console.support.mvc;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.whenling.castle.repo.domain.Result;
import com.whenling.castle.repo.jpa.BaseJpaRepository;

public abstract class CrudController<T, I extends Serializable> extends BaseController {

	@Autowired
	private BaseJpaRepository<T, I> baseJpaRepository;

	private String baseTemplatePath;

	private boolean inJar = false;

	public CrudController() {
		RequestMapping mapping = getClass().getAnnotation(RequestMapping.class);
		this.baseTemplatePath = mapping.value()[0];
	}

	public CrudController(String baseTemplatePath) {
		this.baseTemplatePath = baseTemplatePath;
	}

	public CrudController(boolean inJar) {
		this.inJar = inJar;
		RequestMapping mapping = getClass().getAnnotation(RequestMapping.class);
		this.baseTemplatePath = (inJar ? "classpath:" : "") + mapping.value()[0];
	}

	public CrudController(String baseTemplatePath, boolean inJar) {
		this.inJar = inJar;
		this.baseTemplatePath = (inJar ? "classpath:" : "") + baseTemplatePath;
	}

	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String show(Model model) {
		onShowIndexPage(model);
		return baseTemplatePath + "/index";
	}

	@RequestMapping(value = { "/add", "/edit" }, method = RequestMethod.GET)
	public String add(Model model) {
		return edit(baseJpaRepository.newEntity(), model);
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable("id") T entity, Model model) {
		model.addAttribute("entity", entity);
		onShowEditPage(entity, model);
		return baseTemplatePath + "/edit";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result doSave(@ModelAttribute @Valid T entity, BindingResult bindingResult) {
		onValidate(entity, bindingResult);
		if (bindingResult.hasErrors()) {
			return Result.validateError().error(bindingResult.getAllErrors());
		}

		onBeforeSave(entity);
		baseJpaRepository.save(entity);
		onAfterSave(entity);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids[]") T[] entities) {
		if (entities != null) {
			for (T entity : entities) {
				if (onBeforeDelete(entity)) {
					baseJpaRepository.delete(entity);
				}
			}
		}

		return Result.success();
	}

	protected void onShowIndexPage(Model model) {
	}

	protected void onShowEditPage(T entity, Model model) {
	}

	protected void onValidate(T entity, BindingResult bindingResult) {
	}

	protected void onBeforeSave(T entity) {
	}

	protected void onAfterSave(T entity) {
	}

	protected boolean onBeforeDelete(T entity) {
		return true;
	}

	protected void onAfterDelete(T entity) {
	}

	public BaseJpaRepository<T, I> getBaseJpaRepository() {
		return baseJpaRepository;
	}

	public String getBaseTemplatePath() {
		return baseTemplatePath;
	}

	public boolean isInJar() {
		return inJar;
	}

}
