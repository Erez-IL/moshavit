/*
 User: Ophir
 Date: 25/12/12
 Time: 23:43
 */
package com.moshavit;

import com.moshavit.framework.EmbeddedWebServer;
import com.moshavit.framework.SpringService;
import com.moshavit.framework.SpringWebContainer;
import com.moshavit.framework.WebServerConfigurer;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class ServerStarter extends SpringWebContainer {

	public static void main(String[] args) {
		ServerStarter serverStarter = new ServerStarter();
		serverStarter.start();
	}

	@Override
	protected void onStartingUp() {
		EmbeddedWebServer embeddedWebServer = SpringService.getInstance().getBean(EmbeddedWebServer.class);
		new WebServerConfigurer() {
			@Override
			public void configure(EmbeddedWebServer server) {
				server.getServletContextHandler().addFilter(OpenSessionInViewFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
			}
		}.configure(embeddedWebServer);
	}

	@Override
	protected void onShuttingDown() {
	}
}
