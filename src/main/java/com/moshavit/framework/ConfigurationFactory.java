/*
 User: Ophir
 Date: 05/02/13
 Time: 16:45
 */
package com.moshavit.framework;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.SystemConfiguration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class ConfigurationFactory {

	@Bean
	public Configuration configuration() {
		CompositeConfiguration configuration = new CompositeConfiguration();
		configuration.addConfiguration(new SystemConfiguration());
		return configuration;
	}
}
