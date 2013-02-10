/*
 User: Ophir
 Date: 25/12/12
 Time: 23:43
 */
package com.moshavit;

import com.moshavit.framework.EmbeddedWebServer;
import com.moshavit.framework.SpringWebContainer;

public class ServerStarter extends SpringWebContainer {

	public static void main(String[] args) {
		System.setProperty(EmbeddedWebServer.PORT_PROPERTY, Integer.toString(8081));
		ServerStarter serverStarter = new ServerStarter();
		serverStarter.start();
	}

	@Override
	protected void onStartingUp() {
	}

	@Override
	protected void onShuttingDown() {
	}
}
