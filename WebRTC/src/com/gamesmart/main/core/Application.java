package com.gamesmart.main.core;

public class Application {
	private static Application application;
	private void Application() {}
	public static Application getInstance() {
		if(application == null) {
			synchronized(Application.class) {
				if(application == null) {
					application = new Application();
				}
			}
		}
		return application;
	}
}
