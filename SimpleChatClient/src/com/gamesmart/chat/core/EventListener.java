package com.gamesmart.chat.core;

import java.util.List;

import org.apache.log4j.Logger;

import com.gamesmart.chat.io.EventVariable;
import com.gamesmart.chat.io.Request;
import com.gamesmart.chat.page.HomePage;
import com.gamesmart.chat.page.LoginPage;
import com.gamesmart.chat.vo.PlayerState;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;

import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.entities.SFSUser;
import sfs2x.client.entities.User;
import sfs2x.client.entities.variables.UserVariable;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.LoginRequest;
import sfs2x.client.requests.PublicMessageRequest;

public class EventListener implements IEventListener{
	Logger logger = Logger.getLogger(EventListener.class);
	private SmartFox sfs;
	private User sfsUser;
	private PlayerState playerState;
	private static EventListener listener;
	
	private EventListener() {}
	
	public static EventListener getInstance() {
		if(listener == null) {
			synchronized(EventListener.class) {
				if(listener == null) {
					listener = new EventListener();
				}
			}
		}
		return listener;
	}
	
	public void init(SmartFox sfs, PlayerState playerState) {
		this.sfs = sfs;
		this.playerState = playerState;
	}

	@Override
	public void dispatch(BaseEvent event) throws SFSException {
		getBaseEventResponse(event);
		
		if (event.getType().equals(SFSEvent.USER_VARIABLES_UPDATE)) {
            SFSUser user= (SFSUser) event.getArguments().get("user");
            if (sfsUser == null&&user.isItMe()) {
                sfsUser =user;
            }
            if (user.isItMe()){
                List changedVars = (List) event.getArguments().get("changedVars");
                updateUserVariable(event, changedVars);
            }
        }
		
		if(event.getType().equals(SFSEvent.EXTENSION_RESPONSE)) {
			String cmd = String.valueOf(event.getArguments().get("cmd"));
            ISFSObject responseParams = (SFSObject) event.getArguments().get("params");
			getCmdResponse(cmd,responseParams);
		}
	}

	protected void updateUserVariable(BaseEvent event, List changedVars) {
		if (changedVars.contains(EventVariable.USER_ALIAS_NAME)){
			String aliasName = getUserVariable(EventVariable.USER_ALIAS_NAME).getStringValue();
			String userInfo = getUserVariable(EventVariable.USER_INFO).getStringValue();
			playerState.getPlayerVO().setAlias(aliasName);
			playerState.getPlayerVO().setInfo(userInfo);
			
			HomePage.getInstance().appendMsg(userInfo);
		}
	}
	
	protected UserVariable getUserVariable(String variableName) {
        return sfsUser.getVariable(variableName);
    }
	
	private void getCmdResponse(String cmd, ISFSObject responseParams) {
		if(EventVariable.ON_PUBLIC_MESSAGE.equals(cmd)) {
			long sendId = responseParams.getLong("send_id");
			if(playerState.getPlayerVO().getUserId() != sendId) {
				String msg = responseParams.getUtfString("msg").trim();
				SimpleChatClient.getInstance().appendMsg(msg);
			}
		}
	}

	private void getBaseEventResponse(BaseEvent event) {
		if(event.getType().equals(SFSEvent.CONNECTION)) {
			if((boolean)event.getArguments().get("success")) {
				//join lobby zone
				logger.debug("CONNECT SUFFESSFULLY");
				SFSObject sfsObject = new SFSObject();
				sfsObject.putUtfString("pass", playerState.getPlayerVO().getPass());
				
				System.out.println(playerState.getPlayerVO().getPass());
				
				sfs.send(new LoginRequest(
						String.valueOf(playerState.getPlayerVO().getUserId()),
						playerState.getPlayerVO().getPass(),
						Request.CHAT_ZONE,
						sfsObject));
			}else {
				logger.debug("connection failed");
			}
		}else if(event.getType().equals(SFSEvent.CONNECTION_ATTEMPT_HTTP)) {
			logger.debug("CONNECT FAILED");
		}
		
		if (event.getType().equals(SFSEvent.LOGIN)) {
			sfsUser = (User) event.getArguments().get("user");
			
			logger.debug("LOGIN SUCCESSFULLY");
			
			sfs.send(new JoinRoomRequest(Request.CHAT_ROOM));
		}else if(event.getType().equals(SFSEvent.LOGIN_ERROR)) {
			logger.debug("LOGIN ERROR");
		}
		
		if (event.getType().equals(SFSEvent.ROOM_JOIN)) {
			//TODO do some init ,change button state
			LoginPage.getInstance().loginSuccessed();
			//open publish message page
			logger.debug("ROOM JOIN SUCCESSFULLY");
		}else if (event.getType().equals(SFSEvent.ROOM_JOIN_ERROR)) {
			logger.debug("ROOM JOIN FAILED");
			LoginPage.getInstance().loginFailed();
		}
	}

	public void sendMsg(String msg) {
		sfs.send(new PublicMessageRequest(msg, new SFSObject(), sfs.getLastJoinedRoom()));
	}
	
}
