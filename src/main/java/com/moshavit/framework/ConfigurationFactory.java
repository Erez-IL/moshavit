/*
 User: Ophir
 Date: 05/02/13
 Time: 16:45
 */
package com.moshavit.framework;

import com.moshavit.SystemException;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

@org.springframework.context.annotation.Configuration
public class ConfigurationFactory {
	private static final Logger log = LoggerFactory.getLogger(ConfigurationFactory.class);

	@Bean
	public Configuration configuration() {
		CompositeConfiguration configuration = new CompositeConfiguration();
		configuration.addConfiguration(new SystemConfiguration());
		PropertiesConfiguration propertiesConfiguration = getPropertiesConfiguration();
		configuration.addConfiguration(propertiesConfiguration);
		return configuration;
	}

	private PropertiesConfiguration getPropertiesConfiguration() {
		String configuredLocation = System.getProperty("configLocation");
		String configPath = StringUtils.isNotBlank(configuredLocation) ? configuredLocation : "classpath:/config.properties";
		return loadPropertiesConfiguration(configPath);
	}

	private PropertiesConfiguration loadPropertiesConfiguration(String configLocation) {
		PropertiesConfiguration propertiesConfiguration;
		try {
			Resource resource = new DefaultResourceLoader().getResource(configLocation);
			propertiesConfiguration = new PropertiesConfiguration(resource.getFile());
			log.info("Loaded configuration properties file from " + configLocation);
		} catch (Exception e) {
			throw new SystemException("Failed loading configuration from: " + configLocation, e);
		}
		return propertiesConfiguration;
	}
}
