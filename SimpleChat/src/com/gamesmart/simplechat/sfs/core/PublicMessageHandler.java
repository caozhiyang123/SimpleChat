package com.gamesmart.simplechat.sfs.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class PublicMessageHandler extends BaseServerEventHandler {
	Logger logger = Logger.getLogger(PublicMessageHandler.class);
	
	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		User user = (User)event.getParameter(SFSEventParam.USER);
		Long userId = Long.valueOf(user.getName());
		List<User> users = new ArrayList<>(getParentExtension().getParentZone().getUserList());
		SFSObject object = new SFSObject();
		object.putLong("send_id", userId);
		object.putUtfString("msg", event.getParameter(SFSEventParam.MESSAGE).toString());
		this.send("on_public_message", object, users);
		logger.info("= = = on_public_message,users:"+users.toString()+",params:"+object.toJson());
	}
}