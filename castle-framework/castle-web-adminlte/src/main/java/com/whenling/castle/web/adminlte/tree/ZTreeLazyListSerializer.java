package com.whenling.castle.web.adminlte.tree;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.whenling.castle.repo.domain.Hierarchical;

public class ZTreeLazyListSerializer<U extends Hierarchical<U>, T extends ZTreeLazyList<U>> extends JsonSerializer<T> {

	@Override
	public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		if (value == null) {
			gen.writeNull();
			return;
		}

		Iterable<ZTreeNode<U>> content = value.getContent();
		if (content == null) {
			gen.writeNull();
			return;
		}

		serializers.findValueSerializer(Iterable.class, null).serialize(content, gen, serializers);
	}

}
