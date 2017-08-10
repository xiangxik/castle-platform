package com.castle.json;

import java.io.IOException;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ObjectErrorSerializer extends JsonSerializer<ObjectError> {

	@Override
	public void serialize(ObjectError value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		if (value == null) {
			gen.writeNull();
			return;
		}

		gen.writeStartObject();

		gen.writeStringField("objectName", value.getObjectName());
		gen.writeStringField("code", value.getCode());
		gen.writeStringField("defaultMessage", value.getDefaultMessage());

		if (value instanceof FieldError) {
			gen.writeStringField("field", ((FieldError) value).getField());
			gen.writeObjectField("rejectedValue", ((FieldError) value).getRejectedValue());
		}

		gen.writeEndObject();
	}

}
