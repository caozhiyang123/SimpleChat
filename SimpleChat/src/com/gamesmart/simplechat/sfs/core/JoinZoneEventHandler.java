package com.gamesmart.simplechat.sfs.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gamesmart.simplechat.enghine.core.App;
import com.gamesmart.simplechat.enghine.io.Reply;
import com.gamesmart.simplechat.enghine.io.Request;
import com.gamesmart.simplechat.enghine.io.RequestVariable;
import com.gamesmart.simplechat.enghine.vo.PlayerVO;
import com.smartfoxserver.v2.buddylist.BuddyVariable;
import com.smartfoxserver.v2.buddylist.SFSBuddyVariable;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

public class JoinZoneEventHandler extends BaseServerEventHandler{
	Logger logger = Logger.getLogger(JoinZoneEventHandler.class);

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		try {
			User user = (User)event.getParameter(SFSEventParam.USER);
			Long userId = Long.valueOf(user.getName());
			logger.info("- - - - login user:"+userId+"- - - - ");
			Request request = new Request();
			request.setCmd(RequestVariable.LOGIN);
			request.setUserId(userId);
			Reply reply = App.getInstance().getSessionController().login(request);
			if(reply.getError() == Reply.Error.none) {
				PlayerVO playerVO = reply.getSession().getPlayerState().getPlayerVO();
				List<UserVariable> userVariables = new ArrayList<UserVariable>();
				//public variable
				userVariables.add(new SFSUserVariable("user_alias_name", playerVO.getAlias(),false,false));
				userVariables.add(new SFSUserVariable("user_id",playerVO.getUserId(),false,false));
				userVariables.add(new SFSUserVariable("joined_room", false,false,false));
				userVariables.add(new SFSUserVariable("name","Michael",false,false));
				userVariables.add(new SFSUserVariable("pic","http://pic",false,false));
				//private variable
				userVariables.add(new SFSUserVariable("user_info", playerVO.toString(),false,true));
				userVariables.add(new SFSUserVariable("coins", playerVO.getBalance(),false,true));
				userVariables.add(new SFSUserVariable("xp", playerVO.getXp(),false,true));
				userVariables.add(new SFSUserVariable("level", playerVO.getLevel(),false,true));
//				SFSObject playerObject = new SFSObject();
//				playerObject.putClass("PlayerVO", new com.gamesmart.simplechat.sfs.vo.PlayerVO(playerVO.getUserId(), playerVO.getAlias(), playerVO.getLevel()));
//				userVariables.add(new SFSUserVariable("player", playerObject,false,true));
				getApi().setUserVariables(user, userVariables,true,true);
				/**
				 * init buddy list
				 * @param user current user
				 * @param boolean fire server event BUDDY_LIST_INIT or not
				 * */
				getParentExtension().getBuddyApi().initBuddyList(user, false);
				//update buddy variable
				List<BuddyVariable> vars = new ArrayList<BuddyVariable>();
				vars.add(new SFSBuddyVariable("$alias", playerVO.getAlias()));
				getParentExtension().getBuddyApi().setBuddyVariables(user,vars,true,true);
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
}
