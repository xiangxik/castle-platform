package com.castle.plugin.core;

import java.util.List;

public interface PluginService {

	<S extends Plugin> List<S> getPlugins(Class<S> pluginClass);

	<S extends Plugin> List<S> getEnabledPlugins(Class<S> pluginClass);

	<S extends Plugin> S getPlugin(Class<S> pluginClass, String id);
}
