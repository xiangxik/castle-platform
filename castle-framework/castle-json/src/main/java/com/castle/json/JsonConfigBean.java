package com.castle.json;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Persistable;
import org.springframework.validation.ObjectError;

import com.castle.repo.domain.Node;
import com.castle.repo.domain.Result;
import com.castle.repo.domain.Tree;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import ch.mfrey.jackson.antpathfilter.AntPathFilterMixin;

@Configuration
public class JsonConfigBean {

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.registerModule(simpleModule());
		// objectMapper.setAnnotationIntrospector(ai)

		objectMapper.addMixIn(Persistable.class, AntPathFilterMixin.class);

		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.disable(SerializationFeature.FAIL_ON_SELF_REFERENCES);

		return objectMapper;
	}

	@Bean
	public SimpleModule simpleModule() {
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(DateTime.class, new JodaDateTimeSerializer());
		simpleModule.addSerializer(Page.class, new PageSerializer<>());
		simpleModule.addSerializer(Tree.class, new TreeSerializer<>());
		simpleModule.addSerializer(Node.class, new NodeSerializer<>());
		simpleModule.addSerializer(Result.class, new ResultSerializer());
		simpleModule.addSerializer(ObjectError.class, new ObjectErrorSerializer());
		return simpleModule;
	}

}
