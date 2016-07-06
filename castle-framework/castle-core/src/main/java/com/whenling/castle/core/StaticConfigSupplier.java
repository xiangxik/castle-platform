package com.whenling.castle.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.plist.PropertyListConfiguration;
import org.apache.commons.configuration.tree.OverrideCombiner;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import com.google.common.base.Preconditions;

/**
 * 静态配置，可覆盖，可引入xml、plist、properties类型的配置文件
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:47:40
 */
public class StaticConfigSupplier {
	private static CombinedConfiguration CONFIGURATION;
	private static List<String> _configLocations = new ArrayList<>();
	private static boolean _frozen;

	private StaticConfigSupplier() {

	}

	public static void prepend(String configLocation) {
		Preconditions.checkState(!_frozen);
		Preconditions.checkArgument(!StringUtils.isBlank(configLocation));

		_configLocations.add(0, configLocation);
	}

	public static void append(String configLocation) {
		Preconditions.checkState(!_frozen);
		Preconditions.checkArgument(!StringUtils.isBlank(configLocation));

		_configLocations.add(configLocation);
	}

	public static CombinedConfiguration getConfiguration() {
		if (CONFIGURATION == null) {
			try {
				CombinedConfiguration combinedConfig = new CombinedConfiguration();
				combinedConfig.setNodeCombiner(new OverrideCombiner());

				for (String configLocation : _configLocations) {
					AbstractConfiguration subConfig = null;

					if (StringUtils.endsWith(configLocation, ".xml")) {
						subConfig = new XMLConfiguration(new ClassPathResource(configLocation).getPath());

					} else if (StringUtils.endsWith(configLocation, ".plist")) {
						subConfig = new PropertyListConfiguration(new ClassPathResource(configLocation).getPath());

					} else if (StringUtils.endsWith(configLocation, ".properties")) {
						subConfig = new PropertiesConfiguration(new ClassPathResource(configLocation).getPath());

					} else {
						throw new IllegalStateException("unsupport configuration file type '"
								+ FilenameUtils.getExtension(configLocation) + '"');
					}

					if (subConfig instanceof FileConfiguration) {
						((FileConfiguration) subConfig).setAutoSave(false);
					}

					combinedConfig.addConfiguration(subConfig);
				}

				combinedConfig.setForceReloadCheck(false);

				CONFIGURATION = combinedConfig;

				_frozen = true;
			} catch (ConfigurationException ex) {
				throw new RuntimeException(ex);
			}
		}

		return CONFIGURATION;
	}
}
