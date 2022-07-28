package com.gamesmart.simplechat.enghine.core;

import com.gamesmart.simplechat.enghine.io.SessionController;

public class Application {
	private static Application application = null;
	public SessionController sessionController;
	
	private Application() {}
	
	public static Application getInstance() {
		if(application == null) {
			synchronized(Application.class) {
				if(application == null) {
					application = new Application();
					application.init();
				}
			}
		}
		return application;
	}
	
	private void init() {
		sessionController = new SessionController();
	}
	
	public SessionController getSessionController() {return sessionController;}
	
}
