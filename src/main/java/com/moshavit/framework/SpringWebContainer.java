/*
 User: Ophir
 Date: 27/02/12
 Time: 17:17
 */
package com.moshavit.framework;

import com.google.common.collect.Iterables;
import com.moshavit.SystemException;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.RequestContextFilter;

import javax.servlet.DispatcherType;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public abstract class SpringWebContainer extends AbstractRuntime {
	private static final Logger log = LoggerFactory.getLogger(SpringWebContainer.class);

	private EmbeddedWebServer webServer;

	protected SpringWebContainer() {
	}

	public void initialize() {
		SpringService springService = initializeSpring();
		webServer = springService.getBean(EmbeddedWebServer.class);
		initializeWebServer(springService);
	}

	private EmbeddedWebServer initializeWebServer(SpringService springService) {
		try {
			webServer.getServletContextHandler().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, springService.getApplicationContext());
			initializeResteasy(webServer.getServletContextHandler());
			return webServer;
		} catch (Throwable e) {
			log.error("Failed to initialize embedded web server");
			throw new SystemException("Failed to initialize embedded web server", e);
		}
	}

	protected abstract void onStartingUp();

	protected abstract void onShuttingDown();

	@Override public final void onStartup() {
		try {
			initialize();
			webServer.start();
		} catch (Throwable e) {
			log.error("Failed starting embedded web server on port " + webServer.getPort(), e);
			throw new SystemException("Failed starting embedded web server on port " + webServer.getPort(), e);
		}
		try {
			onStartingUp();
		} catch (Throwable e) {
			throw new SystemException("Failed to invoke startup sequence", e);
		}
	}

	@Override protected final void onShutdown() {
		webServer.stop();
		onShuttingDown();
		SpringService.getInstance().getApplicationContext().stop();
	}

	private SpringService initializeSpring() {
		try {
			String[] activeProfiles = Iterables.toArray(getDefaultProfiles(), String.class);
			return SpringService.initialize(StandardAnnotatedSpringConfiguration.class, activeProfiles);
		} catch (Exception e) {
			log.error("Failed to initialize Spring context", e);
			// rethrow although logged, because we're about to exit anyway
			throw new SystemException("Failed to initialize Spring context", e);
		}
	}

	protected Set<String> getDefaultProfiles() {
		return Collections.emptySet();
	}

	private void initializeResteasy(ServletContextHandler servletContextHandler) {
		ResteasyDeployment resteasyDeployment = SpringService.getInstance().getBean(ResteasyDeployment.class);
		RequestContextFilter requestContextFilter = new RequestContextFilter();
		requestContextFilter.setEnvironment(SpringService.getInstance().getApplicationContext().getEnvironment());
		requestContextFilter.setServletContext(servletContextHandler.getServletContext());
		servletContextHandler.addFilter(new FilterHolder(requestContextFilter), SpringResteasyInitializer.DEFAULT_PREFIX + "/*", EnumSet.of(DispatcherType.REQUEST));

		new SpringResteasyInitializer().configureServlet(resteasyDeployment, servletContextHandler);
	}
}
