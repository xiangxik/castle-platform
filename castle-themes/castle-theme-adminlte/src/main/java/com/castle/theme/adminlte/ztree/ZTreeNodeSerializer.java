package com.castle.theme.adminlte.ztree;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ClassUtils;

import com.castle.repo.domain.Hierarchical;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ZTreeNodeSerializer<U extends Hierarchical<U>, T extends ZTreeNode<U>> extends JsonSerializer<T> {

	@Override
	public void serialize(T value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		if (value == null) {
			gen.writeNull();
			return;
		}

		gen.writeStartObject();

		if (value.getText() != null) {
			gen.writeStringField("name", value.getText());
		}
		if (value.getIconCls() != null) {
			gen.writeStringField("iconSkin", value.getIconCls());
		}
		if (value.getIconUrl() != null) {
			gen.writeStringField("icon", value.getIconUrl());
		}

		U data = value.getData();
		if (data != null) {
			List<U> children = data.getChildren();
			gen.writeBooleanField("isParent", children != null && children.size() > 0);

			BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl(data);

			for (PropertyDescriptor propertyDescriptor : beanWrapperImpl.getPropertyDescriptors()) {
				String propertyName = propertyDescriptor.getName();
				Class<?> propertyType = propertyDescriptor.getPropertyType();
				if (!ClassUtils.isAssignable(Iterable.class, propertyType)
						&& !ArrayUtils.contains(new String[] { "class", "new", "children", "parent", "leaf", "checked", "iconCls", "expanded",
								"createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" }, propertyName)) {
					Object propertyValue = beanWrapperImpl.getPropertyValue(propertyName);

					if (propertyValue != null) {
						gen.writeFieldName(propertyName);
						serializers.findValueSerializer(propertyValue.getClass(), null).serialize(propertyValue, gen, serializers);
					}

				}
			}

		}
		
		gen.writeEndObject();

	}

}
