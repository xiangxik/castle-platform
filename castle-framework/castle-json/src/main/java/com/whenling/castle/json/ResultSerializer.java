package com.whenling.castle.json;

import java.io.IOException;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.whenling.castle.repo.domain.Result;
import com.whenling.castle.repo.domain.Result.ResultCode;

public class ResultSerializer extends JsonSerializer<Result> {

	public static final String FIELD_SUCCESS = "success";
	public static final String FIELD_MESSAGE = "msg";
	public static final String FIELD_CODE = "code";

	@Override
	public void serialize(Result value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		if (value == null) {
			gen.writeNull();
			return;
		}

		gen.writeStartObject();

		gen.writeBooleanField(FIELD_SUCCESS, Objects.equal(value.getCode(), ResultCode.success));
		gen.writeStringField(FIELD_CODE, value.getCode().name());
		if (!Strings.isNullOrEmpty(value.getMessage())) {
			gen.writeStringField(FIELD_MESSAGE, value.getMessage());
		}
		if (value.getExtraProperties() != null) {
			for (Entry<String, Object> entry : value.getExtraProperties().entrySet()) {
				Object propertyValue = entry.getValue();
				if (propertyValue != null) {
					gen.writeFieldName(entry.getKey());
					serializers.findValueSerializer(propertyValue.getClass(), null).serialize(propertyValue, gen, serializers);
				}
			}
		}

		gen.writeEndObject();

	}

}
