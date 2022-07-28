package com.gamesmart.simplechat.enghine.core;

import com.gamesmart.simplechat.enghine.controller.DBController;
import com.gamesmart.simplechat.enghine.io.SessionController;

public class Application {
	public static String CONFIG_DIR;
	private static Application application = null;
	private SessionController sessionController;
	private DBController dbController;
	private static boolean IS_SFS = true;//set false for local test
	
	static
    {
        if (IS_SFS)
        {
            CONFIG_DIR = "extensions/SimpleChatExtension/config/";
        }
        else
        {
            CONFIG_DIR = "config/";
        }
    }
	
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
		dbController = new DBController();
	}
	
	public SessionController getSessionController() {return sessionController;}

	public DBController getDBController() {
		return dbController;
	}
	
}
