package com.gamesmart.simplechat.sfs.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.SFSUser;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class UserJoinRoomHandler extends BaseServerEventHandler {
	Logger logger = Logger.getLogger(UserJoinRoomHandler.class);

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		try {
			SFSUser user = (SFSUser)event.getParameter(SFSEventParam.USER);
			//set user variable
			List<UserVariable> userVars = new ArrayList<UserVariable>();
			userVars.add(new SFSUserVariable("joined_room",true,false,false));
			getApi().setUserVariables(user, userVars, true, true);
		} catch (Exception e) {
			logger.error("",e);
		}
	}
}
