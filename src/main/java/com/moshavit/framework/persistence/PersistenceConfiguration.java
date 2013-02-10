/*
 User: erez
 Date: 1/30/13
 Time: 10:18 PM
 */
package com.moshavit.framework.persistence;

import com.jolbox.bonecp.BoneCPDataSource;
import com.moshavit.framework.EmbeddedWebServer;
import com.moshavit.framework.WebServerConfigurer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

/**
 Based on http://blog.springsource.org/2012/04/06/migrating-to-spring-3-1-and-hibernate-4-1/
 */
@Configuration
@EnableTransactionManagement
public class PersistenceConfiguration {

	@Inject private EmbeddedWebServer embeddedWebServer;

	@PostConstruct
	public void initialize() {
		new WebServerConfigurer() {
			@Override
			public void configure(EmbeddedWebServer server) {
				server.getServletContextHandler().addFilter(OpenSessionInViewFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
			}
		}.configure(embeddedWebServer);
	}

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource());
		sessionFactoryBuilder.setNamingStrategy(ImprovedNamingStrategy.INSTANCE);
		sessionFactoryBuilder.addPackages("com.moshavit");
		SessionFactory sessionFactory = sessionFactoryBuilder.buildSessionFactory();
		return sessionFactory;
	}

	@Bean
	public BoneCPDataSource dataSource() {
		BoneCPDataSource dataSource = new BoneCPDataSource();

		// TODO >> Use values from a properties file:
		dataSource.setDriverClass("org.hsqldb.jdbcDriver");
		dataSource.setJdbcUrl("jdbc:hsqldb:mem:testdb;shutdown=false");
		dataSource.setUser("sa");
		dataSource.setPassword("");
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
