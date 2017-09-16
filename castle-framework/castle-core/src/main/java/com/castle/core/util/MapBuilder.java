package com.castle.core.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.Builder;

public class MapBuilder<K, V> implements Builder<Map<K, V>> {

	private Map<K, V> map = new HashMap<>();
	private boolean ignoreNull = false;

	public MapBuilder<K, V> ignoreNull() {
		ignoreNull = true;
		return this;
	}

	public MapBuilder<K, V> put(K key, V value) {
		if (ignoreNull && value == null) {
			return this;
		}
		map.put(key, value);
		return this;
	}

	@Override
	public Map<K, V> build() {
		return map;
	}

}
