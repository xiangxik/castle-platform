package com.castle.json;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.validation.ObjectError;

import com.castle.repo.domain.Result;
import com.castle.repo.domain.Result.ResultCode;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class ResultSerializer extends JsonSerializer<Result> {

	public static final String FIELD_SUCCESS = "success";
	public static final String FIELD_MESSAGE = "msg";
	public static final String FIELD_CODE = "code";

	@Override
	public void serialize(Result value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		if (value == null) {
			gen.writeNull();
			return;
		}

		gen.writeStartObject();

		gen.writeBooleanField(FIELD_SUCCESS, Objects.equal(value.getCode(), ResultCode.success));
		gen.writeStringField(FIELD_CODE, value.getCode().name());
		if (Objects.equal(ResultCode.validateError, value.getCode())) {
			List<ObjectError> objectErrors = value.getErrors();
			if (objectErrors != null) {
				String errorMessage = Joiner.on("<br>")
						.join(Lists.transform(objectErrors, objectError -> objectError.getDefaultMessage()));
				gen.writeStringField(FIELD_MESSAGE, errorMessage);
			}
		} else {
			if (!Strings.isNullOrEmpty(value.getMessage())) {
				gen.writeStringField(FIELD_MESSAGE, value.getMessage());
			}
		}

		if (value.getExtraProperties() != null) {
			for (Entry<String, Object> entry : value.getExtraProperties().entrySet()) {
				Object propertyValue = entry.getValue();
				if (propertyValue != null) {
					gen.writeFieldName(entry.getKey());
					serializers.findValueSerializer(propertyValue.getClass(), null).serialize(propertyValue, gen,
							serializers);
				}
			}
		}
		if (Objects.equal(value.getCode(), ResultCode.validateError)) {
			if (value.getErrors() != null && value.getErrors().size() > 0) {
				gen.writeObjectField("validates", value.getErrors());
			}
		}
		gen.writeEndObject();

	}

}
