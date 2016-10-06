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
import org.springframework.web.bind.annotation.ResponseBody;

import com.querydsl.core.types.Predicate;
import com.whenling.castle.cms.entity.FeedbackEntity;
import com.whenling.castle.cms.service.FeedbackEntityService;
import com.whenling.castle.repo.domain.Result;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackEntityService feedbackEntityService;

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@ResponseBody
	public Page<FeedbackEntity> doPage(Predicate predicate, Pageable pageable) {
		return feedbackEntityService.findAll(predicate, pageable);
	}

	@CrossOrigin
	@RequestMapping(value="/submit", method=RequestMethod.POST)
	@ResponseBody
	public Result submit(@ModelAttribute @Valid FeedbackEntity feedback, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return Result.validateError();
		}
		feedbackEntityService.save(feedback);
		
		return Result.success();
	}
}
