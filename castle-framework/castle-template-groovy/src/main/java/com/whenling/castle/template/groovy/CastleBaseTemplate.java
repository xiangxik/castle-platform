package com.whenling.castle.template.groovy;

import java.util.Map;

import com.whenling.castle.core.ConfigWrapper;
import com.whenling.castle.core.StaticConfigSupplier;
import com.whenling.castle.web.WebSpringContext;

import groovy.text.markup.BaseTemplate;
import groovy.text.markup.MarkupTemplateEngine;
import groovy.text.markup.TemplateConfiguration;

public abstract class CastleBaseTemplate extends BaseTemplate {

	public CastleBaseTemplate(MarkupTemplateEngine templateEngine, Map<String, Object> model,
			Map<String, String> modelTypes, TemplateConfiguration configuration) {
		super(templateEngine, model, modelTypes, configuration);

		model.put("base", WebSpringContext.getContextPath());
		model.put("staticConfig", new ConfigWrapper(StaticConfigSupplier.getConfiguration()));
	}

}
