package com.gamesmart.simplechat.sfs.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gamesmart.simplechat.enghine.core.App;
import com.gamesmart.simplechat.enghine.io.Request;
import com.gamesmart.simplechat.enghine.io.RequestVariable;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class UserLogoutHandler extends BaseServerEventHandler {
	private Logger logger = Logger.getLogger(UserLogoutHandler.class);

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		try {
			User user = (User)event.getParameter(SFSEventParam.USER);
			//reset user variable
			List<UserVariable> userVars = new ArrayList<UserVariable>();
			userVars.add(new SFSUserVariable("joined_room",false,false,false));
			getApi().setUserVariables(user, userVars, true, true);
			
			Long userId = Long.valueOf(user.getName());
			Request request = new Request();
			request.setCmd(RequestVariable.LOGOUT);
			request.setUserId(userId);
			App.getInstance().getSessionController().logout(request);
		} catch (Exception e) {
			logger.error("",e);
		}
	}

}
