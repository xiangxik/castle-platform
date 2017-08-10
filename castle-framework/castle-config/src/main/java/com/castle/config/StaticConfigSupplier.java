package com.castle.config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.plist.PropertyListConfiguration;
import org.apache.commons.configuration2.tree.OverrideCombiner;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.castle.core.CastleConstants;
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
					FileBasedConfiguration subConfig = null;
					Resource resource = new ClassPathResource(configLocation);

					if (StringUtils.endsWith(configLocation, ".xml")) {
						subConfig = new XMLConfiguration();

					} else if (StringUtils.endsWith(configLocation, ".plist")) {
						subConfig = new PropertyListConfiguration();

					} else if (StringUtils.endsWith(configLocation, ".properties")) {
						subConfig = new PropertiesConfiguration();
					} else {
						throw new IllegalStateException("unsupport configuration file type '"
								+ FilenameUtils.getExtension(configLocation) + '"');
					}
					try {
						subConfig.read(new InputStreamReader(resource.getInputStream(), CastleConstants.charset));
						combinedConfig.addConfiguration(subConfig);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				CONFIGURATION = combinedConfig;

				_frozen = true;
			} catch (ConfigurationException ex) {
				throw new RuntimeException(ex);
			}
		}

		return CONFIGURATION;
	}

}
