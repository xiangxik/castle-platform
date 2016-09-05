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
import com.whenling.castle.cms.entity.ArticleEntity;
import com.whenling.castle.cms.service.ArticleEntityService;
import com.whenling.castle.repo.domain.Result;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleEntityService articleEntityService;

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Page<ArticleEntity> doPage(Predicate predicate, Pageable pageable) {
		return articleEntityService.findAll(predicate, pageable);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result doSave(@ModelAttribute @Valid ArticleEntity article, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.validateError();
		}

		articleEntityService.save(article);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "id")
	@ResponseBody
	public Result delete(@RequestParam(value = "id") ArticleEntity entity) {
		articleEntityService.delete(entity);
		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "ids")
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids") ArticleEntity[] entities) {
		for (ArticleEntity entity : entities) {
			articleEntityService.delete(entity);
		}

		return Result.success();
	}
}
