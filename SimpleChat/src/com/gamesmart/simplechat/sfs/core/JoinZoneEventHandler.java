package com.gamesmart.simplechat.sfs.core;

import org.apache.log4j.Logger;

import com.smartfoxserver.bitswarm.sessions.Session;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import com.smartfoxserver.v2.extensions.SFSExtension;
import com.smartfoxserver.v2.entities.User;

public class JoinZoneEventHandler extends BaseServerEventHandler{
	Logger logger = Logger.getLogger(JoinZoneEventHandler.class);

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		/*
		 * Long userId =
		 * Long.valueOf(event.getParameter(SFSEventParam.LOGIN_NAME).toString()); String
		 * pass = event.getParameter(SFSEventParam.LOGIN_PASSWORD).toString(); SFSObject
		 * object = new SFSObject(); if(String.valueOf(userId).equals(pass)) {
		 * object.putBool("result", true); }else { object.putBool("result", false); }
		 * logger.info("userId:"+userId+",pass:"+pass);
		 */
	}
}
