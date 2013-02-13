/*
 User: Ophir
 Date: 25/12/12
 Time: 23:43
 */
package com.moshavit;

import com.moshavit.framework.SpringWebContainer;

public class ServerStarter extends SpringWebContainer {

	public static void main(String[] args) {
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
