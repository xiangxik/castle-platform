package com.castle.config;

import java.util.Properties;

import org.apache.commons.configuration2.Configuration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.util.Assert;
import org.springframework.util.StringValueResolver;

/**
 * 使用apache configuration作为spring的placeholder
 * 
 * @作者 孔祥溪
 * @博客 http://ken.whenling.com
 * @创建时间 2016年3月1日 下午4:47:18
 */
public class ConfigurationPropertyResourceConfigurer extends PlaceholderConfigurerSupport implements EnvironmentAware {

	public static final String LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME = "localProperties";

	public static final String ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME = "environmentProperties";

	private MutablePropertySources propertySources;

	private PropertySources appliedPropertySources;

	private Environment environment;

	private Configuration configuration;

	public ConfigurationPropertyResourceConfigurer(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setPropertySources(PropertySources propertySources) {
		this.propertySources = new MutablePropertySources(propertySources);
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (this.propertySources == null) {
			this.propertySources = new MutablePropertySources();
			if (this.environment != null) {
				this.propertySources.addLast(
						new PropertySource<Environment>(ENVIRONMENT_PROPERTIES_PROPERTY_SOURCE_NAME, this.environment) {
							@Override
							public String getProperty(String key) {
								return this.source.getProperty(key);
							}
						});
			}
			PropertySource<?> localPropertySource = new PropertySource<Configuration>(
					PropertySourcesPlaceholderConfigurer.LOCAL_PROPERTIES_PROPERTY_SOURCE_NAME, configuration) {
				@Override
				public Object getProperty(String name) {
					if (name != null && name.indexOf("?:") > 0) {
						name = name.substring(0, name.indexOf("?:"));
					}
					return this.source.getProperty(name);
				}
			};
			propertySources.addFirst(localPropertySource);
		}

		processProperties(beanFactory, new PropertySourcesPropertyResolver(this.propertySources));
		this.appliedPropertySources = this.propertySources;
	}

	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
			final ConfigurablePropertyResolver propertyResolver) throws BeansException {

		propertyResolver.setPlaceholderPrefix(this.placeholderPrefix);
		propertyResolver.setPlaceholderSuffix(this.placeholderSuffix);
		propertyResolver.setValueSeparator(this.valueSeparator);

		StringValueResolver valueResolver = new StringValueResolver() {
			@Override
			public String resolveStringValue(String strVal) {
				String resolved = ignoreUnresolvablePlaceholders ? propertyResolver.resolvePlaceholders(strVal)
						: propertyResolver.resolveRequiredPlaceholders(strVal);
				return (resolved.equals(nullValue) ? null : resolved);
			}
		};

		doProcessProperties(beanFactoryToProcess, valueResolver);
	}

	@Override
	@Deprecated
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) {
		throw new UnsupportedOperationException(
				"Call processProperties(ConfigurableListableBeanFactory, ConfigurablePropertyResolver) instead");
	}

	public PropertySources getAppliedPropertySources() throws IllegalStateException {
		Assert.state(this.appliedPropertySources != null, "PropertySources have not get been applied");
		return this.appliedPropertySources;
	}

}