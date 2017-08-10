package com.castle.config;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration2.Configuration;

import com.google.common.collect.Sets;

public class ConfigWrapper implements Map<String, Object> {

	private Configuration target;

	public ConfigWrapper(Configuration target) {
		this.target = target;
	}

	@Override
	public int size() {
		return target.size();
	}

	@Override
	public boolean isEmpty() {
		return target.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return target.containsKey((String) key);
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public Object get(Object key) {
		return target.getProperty((String) key);
	}

	@Override
	public Object put(String key, Object value) {
		return value;
	}

	@Override
	public Object remove(Object key) {
		return get(key);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {

	}

	@Override
	public void clear() {

	}

	@Override
	public Set<String> keySet() {
		return Sets.newHashSet(target.getKeys());
	}

	@Override
	public Collection<Object> values() {
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return null;
	}

}
