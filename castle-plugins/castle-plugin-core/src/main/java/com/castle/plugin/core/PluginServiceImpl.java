package com.castle.plugin.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class PluginServiceImpl implements PluginService, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public <S extends Plugin> List<S> getPlugins(Class<S> pluginClass) {
		Collection<S> plugins = applicationContext.getBeansOfType(pluginClass).values();
		if (plugins == null || plugins.isEmpty()) {
			return null;
		}
		List<S> result = Lists.newArrayList(plugins);
		Collections.sort(result);
		return result;
	}

	@Override
	public <S extends Plugin> List<S> getEnabledPlugins(Class<S> pluginClass) {
		Collection<S> plugins = applicationContext.getBeansOfType(pluginClass).values();
		if (plugins == null || plugins.isEmpty()) {
			return null;
		}

		List<S> result = new ArrayList<>();
		CollectionUtils.select(plugins, new Predicate() {
			@Override
			public boolean evaluate(Object object) {
				Plugin plugin = (Plugin) object;
				return !plugin.isDisabled();
			}
		}, result);
		Collections.sort(result);
		return result;
	}

	@Override
	public <S extends Plugin> S getPlugin(Class<S> pluginClass, String id) {
		return applicationContext.getBean(id, pluginClass);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
