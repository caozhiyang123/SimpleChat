package com.gamesmart.simplechat.sfs.core;

import org.apache.log4j.Logger;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class UserJoinRoomHandler extends BaseServerEventHandler {
	Logger logger = Logger.getLogger(UserJoinRoomHandler.class);

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		
	}
}
