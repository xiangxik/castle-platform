package com.whenling.castle.shop.controller;

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
import com.whenling.castle.repo.domain.Result;
import com.whenling.castle.shop.entity.ProductTagEntity;
import com.whenling.castle.shop.service.ProductTagEntityService;

@Controller
@RequestMapping("/productTag")
public class ProductTagController {

	@Autowired
	private ProductTagEntityService productTagEntityService;

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Page<ProductTagEntity> doPage(Predicate predicate, Pageable pageable) {
		return productTagEntityService.findAll(predicate, pageable);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result doSave(@ModelAttribute @Valid ProductTagEntity productTag, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.validateError();
		}

		productTagEntityService.save(productTag);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "id")
	@ResponseBody
	public Result delete(@RequestParam(value = "id") ProductTagEntity entity) {
		productTagEntityService.delete(entity);
		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "ids")
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids") ProductTagEntity[] entities) {
		for (ProductTagEntity entity : entities) {
			productTagEntityService.delete(entity);
		}

		return Result.success();
	}
}
