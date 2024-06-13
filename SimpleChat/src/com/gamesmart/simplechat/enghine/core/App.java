package com.gamesmart.simplechat.enghine.core;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.gamesmart.simplechat.enghine.controller.DBController;
import com.gamesmart.simplechat.enghine.io.SessionController;

public class App {
	private static Logger logger = Logger.getLogger(App.class);
	
	public static String CONFIG_DIR;
	private static App application;
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
	
	private App() {
		if (!IS_SFS)
        {
			//init log sys
			System.setProperty("WORKDIR", "log");
			PropertyConfigurator.configure("config/log4j.properties");
        }
	}
	
	public static App getInstance() {
		if(application == null) {
			synchronized(App.class) {
				if(application == null) {
					application = new App();
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
