package com.gamesmart.simplechat.sfs.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gamesmart.simplechat.enghine.core.App;
import com.gamesmart.simplechat.enghine.io.Reply;
import com.gamesmart.simplechat.enghine.io.Request;
import com.gamesmart.simplechat.enghine.io.SessionController;
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
		Request request = new Request();
		request.setUserId(userId);
		request.setCmd(Request.ON_PUBLIC_MESSAGE);
		Reply reply = App.getInstance().getSessionController().doRequest(request);
		SFSObject returnObject = new SFSObject();
		if(reply.getError() == Reply.Error.none) {
			List<User> users = new ArrayList<>(getParentExtension().getParentZone().getUserList());
			returnObject.putLong("send_id", userId);
			String alias = reply.getSession().getPlayerState().getPlayerVO().getAlias();
			returnObject.putUtfString("alias", alias);
			returnObject.putUtfString("msg", event.getParameter(SFSEventParam.MESSAGE).toString());
			this.send("on_public_message", returnObject, users);
		}else {
			returnObject.putUtfString("msg", reply.getError().toString());
			this.send("error", returnObject, user);
		}
		logger.debug("= = public msg = = =");
	}
}