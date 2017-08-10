package com.castle.template.thymeleaf.dialect;

import java.util.Set;

import org.springframework.context.support.MessageSourceAccessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.spring4.dialect.SpringStandardDialect;

import com.google.common.collect.Sets;

public class CastleStandardDialect extends AbstractProcessorDialect {

	private MessageSourceAccessor messageSourceAccessor;

	public CastleStandardDialect(MessageSourceAccessor messageSourceAccessor) {
		super("Castle Standard Dialect", "th", SpringStandardDialect.PROCESSOR_PRECEDENCE - 1);
		this.messageSourceAccessor = messageSourceAccessor;
	}

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		return Sets.newHashSet(new EnumOptionTagProcessor(dialectPrefix, this.messageSourceAccessor),
				new ColumnEnumTagProcessor(dialectPrefix, this.messageSourceAccessor));
	}

}