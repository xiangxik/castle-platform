package com.castle.json;

import java.io.IOException;
import java.util.Date;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JodaDateTimeSerializer extends JsonSerializer<DateTime> {

	@Override
	public void serialize(DateTime value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		if (value == null) {
			serializers.defaultSerializeNull(gen);
		} else {
			serializers.findValueSerializer(Date.class).serialize(value.toDate(), gen, serializers);
		}
	}

}
