package com.castle.theme.adminlte.ztree;

import java.io.IOException;

import com.castle.repo.domain.Hierarchical;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

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
