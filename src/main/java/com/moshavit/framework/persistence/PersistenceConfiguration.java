/*
 User: erez
 Date: 1/30/13
 Time: 10:18 PM
 */
package com.moshavit.framework.persistence;

import com.jolbox.bonecp.BoneCPDataSource;
import org.apache.commons.configuration.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 Based on http://blog.springsource.org/2012/04/06/migrating-to-spring-3-1-and-hibernate-4-1/
 */
@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
public class PersistenceConfiguration {

	private static final Logger log = LoggerFactory.getLogger(PersistenceConfiguration.class);
	public static final String HIBERNATE_AUTO_SCHEMA_PROCESS = "hibernate.autoSchemaProcess";

	@Inject private Configuration configuration;

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource());
		sessionFactoryBuilder.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
		if (isNotBlank(configuration.getString("hibernate.dialect"))) {
			sessionFactoryBuilder.setProperty("hibernate.dialect", configuration.getString("hibernate.dialect"));
		}
		sessionFactoryBuilder.scanPackages("com.moshavit");
		String automaticSchemaHandling = configuration.getString(HIBERNATE_AUTO_SCHEMA_PROCESS, "update");
		if (automaticSchemaHandling.contains("create")) {
			log.warn("Please note: database will be newly created with Hibernate schema. Change '{}' configuration to avoid having it dropped and  created with every restart.", HIBERNATE_AUTO_SCHEMA_PROCESS);
		}
		sessionFactoryBuilder.setProperty("hibernate.hbm2ddl.auto", automaticSchemaHandling);
		return sessionFactoryBuilder.buildSessionFactory();
	}

	@Bean
	public BoneCPDataSource dataSource() {
		BoneCPDataSource dataSource = new BoneCPDataSource();
		dataSource.setDriverClass(configuration.getString("jdbcDriver"));
		dataSource.setJdbcUrl(configuration.getString("jdbcURL"));
		dataSource.setUser(configuration.getString("jdbcUsr"));
		dataSource.setPassword(configuration.getString("jdbcPassword"));
		dataSource.setIdleConnectionTestPeriod(60, TimeUnit.SECONDS);
		dataSource.setIdleMaxAge(2, TimeUnit.HOURS);
		dataSource.setMinConnectionsPerPartition(20);
		dataSource.setMaxConnectionsPerPartition(60);
		dataSource.setPartitionCount(3);
		dataSource.setAcquireIncrement(10);
		dataSource.setStatementsCacheSize(50);
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new HibernateTransactionManager(sessionFactory());
	}

	@PreDestroy
	public void shutdown() {
		sessionFactory().close();
		dataSource().close();
	}
}
