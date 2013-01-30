/*
 User: Ophir
 Date: 25/12/12
 Time: 23:43
 */
package com.moshavit;

import com.moshavit.framework.SpringWebContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerStarter extends SpringWebContainer {

	private static final Logger log = LoggerFactory.getLogger(ServerStarter.class);

	public static void main(String[] args) {
		new ServerStarter().start();
	}

	@Override
	protected void onStartingUp() {
	}

	@Override
	protected void onShuttingDown() {
	}

	@Override
	protected int getDefaultHttpPort() {
		return 8081;
	}
}
