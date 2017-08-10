package com.castle.repo.mongo.json;

import java.io.IOException;

import org.springframework.data.mongodb.core.MongoOperations;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Strings;

public class MongoBeanDeserializer extends JsonDeserializer<Object> {

	private MongoOperations mongoOperations;
	private JavaType type;

	public MongoBeanDeserializer(MongoOperations mongoOperations, JavaType type) {
		this.mongoOperations = mongoOperations;
		this.type = type;
	}

	@Override
	public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		String id = p.readValueAs(String.class);
		return Strings.isNullOrEmpty(id) ? null : mongoOperations.findById(id, type.getRawClass());
	}

}
