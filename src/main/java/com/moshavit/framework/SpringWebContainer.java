/*
 User: Ophir
 Date: 27/02/12
 Time: 17:17
 */
package com.moshavit.framework;

import com.google.common.collect.Iterables;
import com.moshavit.SystemException;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Set;

public abstract class SpringWebContainer extends AbstractRuntime {
	private static final Logger log = LoggerFactory.getLogger(SpringWebContainer.class);

	private final EmbeddedWebServer webServer;
	private WebServerConfigurer webServerConfigurer;

	protected SpringWebContainer() {
		SpringService springService = initializeSpring();
		webServer = initializeWebServer(springService);
	}

	private EmbeddedWebServer initializeWebServer(SpringService springService) {
		try {
			EmbeddedWebServer webServer = createWebServer(getDefaultHttpPort());
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
			if (webServerConfigurer != null) {
				webServerConfigurer.configure(webServer);
			}
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
		new SpringResteasyInitializer().configureServlet(resteasyDeployment, servletContextHandler);
	}

	/**
	 The default port for the embedded web server.
	 Override with the property: '{@code http.embedded.port}'
	 */
	protected abstract int getDefaultHttpPort();

	private EmbeddedWebServer createWebServer(Integer port) {
		return new EmbeddedWebServer(port);
	}

	public EmbeddedWebServer getWebServer() {
		return webServer;
	}

	public void setWebServerConfigurer(WebServerConfigurer webServerConfigurer) {
		this.webServerConfigurer = webServerConfigurer;
	}
}
