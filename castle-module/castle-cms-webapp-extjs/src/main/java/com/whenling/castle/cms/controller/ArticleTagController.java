package com.whenling.castle.cms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.querydsl.core.types.Predicate;
import com.whenling.castle.cms.entity.ArticleTagEntity;
import com.whenling.castle.cms.service.ArticleTagEntityService;
import com.whenling.castle.repo.domain.Result;

@Controller
@RequestMapping("/articleTag")
public class ArticleTagController {

	@Autowired
	private ArticleTagEntityService articleTagEntityService;

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Page<ArticleTagEntity> doPage(Predicate predicate, Pageable pageable) {
		return articleTagEntityService.findAll(predicate, pageable);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result doSave(@ModelAttribute @Valid ArticleTagEntity articleTag, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.validateError();
		}

		articleTagEntityService.save(articleTag);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "id")
	@ResponseBody
	public Result delete(@RequestParam(value = "id") ArticleTagEntity entity) {
		articleTagEntityService.delete(entity);
		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "ids")
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids") ArticleTagEntity[] entities) {
		for (ArticleTagEntity entity : entities) {
			articleTagEntityService.delete(entity);
		}

		return Result.success();
	}
}
