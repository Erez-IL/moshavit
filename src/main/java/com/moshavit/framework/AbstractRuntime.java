/*
 User: Ophir
 Date: 06/12/11
 Time: 16:27
 */
package com.moshavit.framework;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractRuntime {

	private final Logger log = LoggerFactory.getLogger(AbstractRuntime.class);

	private final StopWatch uptime = new StopWatch();
	private boolean shouldRun = true;

	protected AbstractRuntime() {
		uptime.start();
	}

	public abstract void onStartup();

	protected abstract void onShutdown();

	public void start() {
		try {
			onStartup();
		} catch (Exception e) {
			log.error("Bootstrap failed: " + e.getMessage(), e);
			stop();
			// exit with an error:
			System.exit(1);
		}
		log.info("=== {} Started (in {}) ===", getClass().getSimpleName(), uptime);
		busyWait();
	}

	private synchronized void busyWait() {
		while (shouldRun) {
			try {
				wait();
				log.info("Wait was Awake");
				//noinspection UseOfSystemOutOrSystemErr
				System.out.println("Wait was awake");
			} catch (InterruptedException e) {
				// No OP
			}
		}
	}

	public void stop() {
		log.info("Shutting down (was up for {})...", uptime);
		shouldRun = false;
		try {
			onShutdown();
		} catch (Exception e) {
			log.error("Failed executing shutdown procedures", e);
		}
	}

	public void shutdown() {
		log.info("Shutdown called...");
		System.exit(0);
	}

	public StopWatch getUptime() {
		return uptime;
	}
}
