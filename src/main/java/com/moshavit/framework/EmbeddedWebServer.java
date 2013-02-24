/*
 User: Ophir
 Date: 22/12/11
 Time: 17:03
 */
package com.moshavit.framework;

import com.moshavit.SystemException;
import org.apache.commons.configuration.Configuration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.DispatcherType;
import java.io.IOException;
import java.util.EnumSet;

@Component
public class EmbeddedWebServer {

	private static final Logger log = LoggerFactory.getLogger(EmbeddedWebServer.class);

	public static final String PORT_PROPERTY = "embedded.httpServer.port";

	private String webContentLocation;

	private final ServletContextHandler servletContextHandler = createServletContext();
	private Server server;
	private final int port;

	public EmbeddedWebServer(int port) {
		this.port = port;
	}

	@Inject
	public EmbeddedWebServer(Configuration configuration) {
		this(configuration.getInt(PORT_PROPERTY, 8081));
		webContentLocation = configuration.getString("embedded.httpServer.webContentLocation", "classpath:/webapp");
	}

	public void start() {
		server = new Server(port);
		HandlerList handlerList = new HandlerList();
		handlerList.addHandler(servletContextHandler);
		servletContextHandler.addFilter(WebExceptionHandler.class, "/*", EnumSet.of(DispatcherType.REQUEST));
		handlerList.addHandler(createResourceHandler());
		server.setHandler(handlerList);
		try {
			server.start();
//			server.join();
			log.info("Embedded web server started, listening on port {}", port);
		} catch (Exception e) {
			log.error("Failed starting an embedded Jetty server", e);
		}
	}

	private ResourceHandler createResourceHandler() {
		ResourceHandler resourceHandler = new ResourceHandler();
		Resource resource = new DefaultResourceLoader().getResource(webContentLocation);
		if (resource.exists()) {
			try {
				resourceHandler.setResourceBase(resource.getURL().toString());
				log.info("Serving contents of '{}' as web resources.", webContentLocation);
			} catch (IOException e) {
				throw new SystemException(String.format("Failed trying to serve classpath package '%s' as a base for static resources", webContentLocation), e);
			}
		}
		return resourceHandler;
	}

	public void stop() {
		try {
			servletContextHandler.stop();
			if (server != null) {
				server.stop();
			}
			log.info("Shutdown web server");
		} catch (Exception e) {
			throw new SystemException("Failed stopping Jetty embedded web server", e);
		}
	}

	public ServletContextHandler getServletContextHandler() {
		return servletContextHandler;
	}

	private ServletContextHandler createServletContext() {
		ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletContextHandler.setContextPath("/api");
		return servletContextHandler;
	}

	public Server getServer() {
		return server;
	}

	public String getWebContentLocation() {
		return webContentLocation;
	}

	public void setWebContentLocation(String webContentLocation) {
		this.webContentLocation = webContentLocation;
	}

	public int getPort() {
		return port;
	}
}
