package com.moshavit.framework;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;

import java.util.EnumSet;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class SpringResteasyInitializer {

	public static final String DEFAULT_PREFIX = "";

	private final String prefix;

	public SpringResteasyInitializer() {
		this(DEFAULT_PREFIX);
	}

	public SpringResteasyInitializer(String prefix) {
		this.prefix = prefix;
	}

	public void configureServlet(ResteasyDeployment deployment, ServletContextHandler context) {
		if (isNotBlank(prefix)) {
			context.setInitParameter("resteasy.servlet.mapping.prefix", prefix);
		}

		context.addServlet(HttpServletDispatcher.class, prefix + "/*");
		configureServletContext(deployment, context.getServletContext());

		context.addFilter(CORSFilter.class, prefix + "/*", EnumSet.allOf(DispatcherType.class));
	}

	public void configureServletContext(ResteasyDeployment deployment, ServletContext servletContext) {
		servletContext.setAttribute(Dispatcher.class.getName(), deployment.getDispatcher());
		servletContext.setAttribute(ResteasyProviderFactory.class.getName(), deployment.getProviderFactory());
	}
}
