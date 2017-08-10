package com.castle.template.thymeleaf.dialect;

import org.springframework.context.support.MessageSourceAccessor;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class ColumnEnumTagProcessor extends AbstractAttributeTagProcessor {

	private static final String ATTR_NAME = "column-enum";
	private static final int PRECEDENCE = 10000;

	private MessageSourceAccessor messageSourceAccessor;

	protected ColumnEnumTagProcessor(String dialectPrefix, MessageSourceAccessor messageSourceAccessor) {
		super(TemplateMode.HTML, dialectPrefix, "th", false, ATTR_NAME, true, PRECEDENCE, true);
		this.messageSourceAccessor = messageSourceAccessor;
	}

	@Override
	protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName,
			String attributeValue, IElementTagStructureHandler structureHandler) {

		StringBuilder builder = new StringBuilder();
		builder.append("{");
		try {
			Object[] enums = Class.forName(attributeValue).getEnumConstants();
			for (int i = 0; i < enums.length; i++) {
				String value = enums[i].toString();
				String display = messageSourceAccessor.getMessage(attributeValue + "." + value, value);

				if (i > 0) {
					builder.append(",");
				}
				builder.append("'" + value + "':'" + display + "'");
			}
		} catch (

		ClassNotFoundException e) {
			e.printStackTrace();
		}
		builder.append("}");
		structureHandler.setAttribute("data-map", builder.toString());
		structureHandler.setAttribute("data-converter", "enums");
	}

}
