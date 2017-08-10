package com.castle.template.thymeleaf.dialect;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.servlet.support.BindStatus;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring4.util.FieldUtils;
import org.thymeleaf.templatemode.TemplateMode;

import com.google.common.base.Objects;

public class EnumOptionTagProcessor extends AbstractAttributeTagProcessor {

	private static final String ATTR_NAME = "enumOption";
	private static final int PRECEDENCE = 10000;

	private MessageSourceAccessor messageSourceAccessor;

	protected EnumOptionTagProcessor(String dialectPrefix, MessageSourceAccessor messageSourceAccessor) {
		super(TemplateMode.HTML, dialectPrefix, "select", false, ATTR_NAME, true, PRECEDENCE, true);
		this.messageSourceAccessor = messageSourceAccessor;
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {

		final BindStatus bindStatus = FieldUtils.getBindStatus(context, tag.getAttributeValue("th:field"));
		Object fieldValue = bindStatus.getActualValue();
		StringBuilder builder = new StringBuilder();
		try {
			Object[] enums = Class.forName(attributeValue).getEnumConstants();
			for (Object en : enums) {
				boolean selected = fieldValue != null && Objects.equal(fieldValue, en);
				String value = en.toString();
				String display = messageSourceAccessor.getMessage(attributeValue + "." + value, value);
				builder.append("<option value='" + value + "'" + (selected ? " selected='selected'" : "") + ">"
						+ display + "</option>");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		structureHandler.setBody(builder.toString(), false);
	}

}
