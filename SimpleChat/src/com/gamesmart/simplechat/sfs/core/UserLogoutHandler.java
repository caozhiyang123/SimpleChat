package com.gamesmart.simplechat.sfs.core;

import com.gamesmart.simplechat.enghine.core.App;
import com.gamesmart.simplechat.enghine.io.Request;
import com.gamesmart.simplechat.enghine.io.RequestVariable;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class UserLogoutHandler extends BaseServerEventHandler {

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		User user = (User)event.getParameter(SFSEventParam.USER);
		Long userId = Long.valueOf(user.getName());
		
		Request request = new Request();
		request.setCmd(RequestVariable.LOGOUT);
		request.setUserId(userId);
		//App.getInstance().getSessionController().logout(request);
	}

}
