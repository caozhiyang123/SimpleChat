package com.gamesmart.simplechat.sfs.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gamesmart.simplechat.enghine.core.Application;
import com.gamesmart.simplechat.enghine.io.Reply;
import com.gamesmart.simplechat.enghine.io.Request;
import com.gamesmart.simplechat.enghine.io.RequestVariable;
import com.gamesmart.simplechat.enghine.vo.PlayerVO;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class UserJoinRoomHandler extends BaseServerEventHandler {
	Logger logger = Logger.getLogger(UserJoinRoomHandler.class);

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		logger.info("join room");
		User user = (User)event.getParameter(SFSEventParam.USER);
		Long userId = Long.valueOf(user.getName());
		
		Request request = new Request();
		request.setCmd(RequestVariable.LOGIN);
		request.setUserId(userId);
		Reply reply = Application.getInstance().getSessionController().login(request);
		if(reply.getError() == Reply.Error.none) {
			PlayerVO playerVO = reply.getSession().getPlayerState().getPlayerVO();
			List<UserVariable> userVariables = new ArrayList<UserVariable>();
			userVariables.add(new SFSUserVariable("user_alias_name", playerVO.getAlias(),false,false));
			getApi().setUserVariables(user, userVariables,true,true);
			logger.info("set userVariables,user_alias_name:"+playerVO.getAlias());
		}else {
			
		}
	}
}
