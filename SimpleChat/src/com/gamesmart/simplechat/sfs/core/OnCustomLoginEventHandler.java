package com.gamesmart.simplechat.sfs.core;

import org.apache.log4j.Logger;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class OnCustomLoginEventHandler extends BaseServerEventHandler{
	private Logger logger = Logger.getLogger(OnCustomLoginEventHandler.class);

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		try {
			String userName = event.getParameter(SFSEventParam.LOGIN_NAME).toString();
			String password = event.getParameter(SFSEventParam.LOGIN_PASSWORD).toString();
			logger.info(String.format(" - - - - login userName:%s,password:%s",userName,password));
			
			ISFSObject params = (ISFSObject) event.getParameter(SFSEventParam.LOGIN_IN_DATA);
	        if (params != null && params.containsKey("name")){
	        	String name = params.getUtfString("name");
	        	logger.info(String.format(" - - - - login data:%s",name));
	        }
		} catch (Exception e) {
			logger.error("",e);
		}
	}

}
