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
import com.whenling.castle.repo.domain.Tree;
import com.whenling.castle.shop.entity.ProductCategoryEntity;
import com.whenling.castle.shop.service.ProductCategoryEntityService;

@Controller
@RequestMapping("/productCategory")
public class ProductCategoryController {

	@Autowired
	private ProductCategoryEntityService productCategoryEntityService;

	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	@ResponseBody
	public Tree<ProductCategoryEntity> tree(Predicate predicate) {
		Tree<ProductCategoryEntity> tree = productCategoryEntityService.findTree(predicate);
		tree.setTextProperty("name");
		return tree;
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Page<ProductCategoryEntity> page(Predicate predicate, Pageable pageable) {
		return productCategoryEntityService.findAll(predicate, pageable);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Result doSave(@ModelAttribute @Valid ProductCategoryEntity productCategory, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.validateError();
		}

		productCategoryEntityService.save(productCategory);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "id")
	@ResponseBody
	public Result delete(@RequestParam(value = "id") ProductCategoryEntity productCategory) {
		productCategoryEntityService.delete(productCategory);
		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "ids")
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids") ProductCategoryEntity[] entities) {
		for (ProductCategoryEntity entity : entities) {
			productCategoryEntityService.delete(entity);
		}

		return Result.success();
	}
}
