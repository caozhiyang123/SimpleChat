package com.gamesmart.simplechat.sfs.core;

import org.apache.log4j.Logger;

import com.gamesmart.simplechat.enghine.core.App;
import com.gamesmart.simplechat.enghine.io.Request;
import com.gamesmart.simplechat.enghine.io.RequestVariable;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class RoomRemovedHandler extends BaseServerEventHandler {
	Logger logger = Logger.getLogger(RoomRemovedHandler.class);
	
	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		logger.debug(" = = room removed = = ");
	}

}
