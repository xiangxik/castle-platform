package com.whenling.castle.cms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.querydsl.core.types.Predicate;
import com.whenling.castle.cms.entity.SubscribeEntity;
import com.whenling.castle.cms.service.SubscribeEntityService;
import com.whenling.castle.repo.domain.Result;

@Controller
@RequestMapping("/subscribe")
public class SubscribeController {

	@Autowired
	private SubscribeEntityService subscribeEntityService;

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Page<SubscribeEntity> doPage(Predicate predicate, Pageable pageable) {
		return subscribeEntityService.findAll(predicate, pageable);
	}

	@CrossOrigin
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public Result submit(@ModelAttribute @Valid SubscribeEntity subscribe, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.validateError();
		}
		subscribeEntityService.save(subscribe);

		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "id")
	@ResponseBody
	public Result delete(@RequestParam(value = "id") SubscribeEntity entity) {
		subscribeEntityService.delete(entity);
		return Result.success();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, params = "ids")
	@ResponseBody
	public Result batchDelete(@RequestParam(value = "ids") SubscribeEntity[] entities) {
		for (SubscribeEntity entity : entities) {
			subscribeEntityService.delete(entity);
		}

		return Result.success();
	}
}
