package com.moshavit.framework;

import com.google.common.collect.Iterables;
import com.moshavit.SystemException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.Arrays;
import java.util.Map;

public class SpringService {

	private static final Logger log = LoggerFactory.getLogger(SpringService.class);

	private static SpringService instance;

	private final ConfigurableApplicationContext applicationContext;

	public SpringService(ConfigurableApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		log.info("Initialized Spring ApplicationContext: " + applicationContext);
	}
	
	public static SpringService initialize(Class<?> configurationClass, String... activeProfiles) {
		assertNotInitializedYet();
		log.info("Initializing Spring context specified by configuration class: {}", configurationClass);
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(configurationClass);
		return initialize(context, activeProfiles);
	}

	/**
	 Creates and stores a Spring {@link ApplicationContext} by reading XML configuration files from the specified locations

	 @see ClassPathXmlApplicationContext
	 <strong >Call only once</strong>
	 */
	public static SpringService initialize(String configurationLocation, String... activeProfiles) {
		assertNotInitializedYet();
		log.info("Initializing Spring context specified by: {}", configurationLocation);
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();
		context.load(configurationLocation);
		return initialize(context, activeProfiles);
	}

	public static SpringService initialize(ConfigurableApplicationContext applicationContext, String... activeProfiles) {
		assertNotInitializedYet();
		setActiveProfiles(applicationContext, activeProfiles);
		applicationContext.refresh();
		return initialize(applicationContext);
	}

	private static void setActiveProfiles(ConfigurableApplicationContext applicationContext, String[] activeProfiles) {
		if (StringUtils.isNotBlank(System.getProperty("spring.profiles.active"))) {
			//noinspection DynamicRegexReplaceableByCompiledPattern
			activeProfiles = System.getProperty("spring.profiles.active").split(",");
		}

		if (activeProfiles != null && activeProfiles.length > 0) {
			applicationContext.getEnvironment().setActiveProfiles(activeProfiles);
			log.info("Spring active profiles set: " + Arrays.asList(activeProfiles));
		}
	}

	/**
	 Stores an {@link ApplicationContext} for future bean resolutions.

	 @param applicationContext An {@link ApplicationContext} to be stored.
	 @return self.
	 */
	public static SpringService initialize(ConfigurableApplicationContext applicationContext) {
		assertNotInitializedYet();
		instance = new SpringService(applicationContext);
		return instance;
	}

	public ConfigurableApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public <T> T getBean(Class<T> type) {
		Map<String, T> beansOfType = applicationContext.getBeansOfType(type);
		if (beansOfType.isEmpty()) throw new SystemException("No Spring bean of type " + type + " found.");
		if (beansOfType.size() > 1) {
			throw new SystemException("More than 1 Spring bean of type " + type + " exists: " + Iterables.toString(beansOfType.keySet()));
		}
		return beansOfType.values().iterator().next();
	}

	@SuppressWarnings({"unchecked"})
	public <T> T getBeanByName(String name) {
		return (T) applicationContext.getBean(name);
	}

	@SuppressWarnings("StaticVariableUsedBeforeInitialization")
	public static SpringService getInstance() {
		return instance;
	}

	private static void assertNotInitializedYet() {
		//noinspection StaticVariableUsedBeforeInitialization
		if (instance != null) throw new SystemException("Spring already initialized once!");
	}

	/**
	 <strong>NOTICE!</strong> This should in principle be used only in tests so that it would be possible to augment the ApplicationContext provider.
	 */
	public static void setInstance(SpringService instance) {
		SpringService.instance = instance;
	}
}
