package com.gamesmart.simplechat.sfs.core;

import java.util.List;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class PublicMessageHandler extends BaseServerEventHandler {

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
//		User user = (User)event.getParameter(SFSEventParam.USER);
//		Long userId = Long.valueOf(user.getName());
		
//		Request request = new Request();
//		request.setCmd(RequestVariable.CHAT);
//		request.setUserId(userId);
//		Map<String, Object> params = new HashMap<String,Object>();
//		params.put(RequestVariable.CHAT, event.getParameter(SFSEventParam.MESSAGE));
//		request.setParams(params);
		//Application.getInstance().getSessionController().doRequest(request);
		//broadcast to all users in the room
		List<User> users = getParentExtension().getParentRoom().getUserList();
		SFSObject object = new SFSObject();
		object.putUtfString("msg", event.getParameter(SFSEventParam.MESSAGE).toString());
		getParentExtension().send("on_public_message", object, users);
	}

}
