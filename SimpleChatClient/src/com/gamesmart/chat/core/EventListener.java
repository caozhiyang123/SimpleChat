package com.gamesmart.chat.core;

import org.apache.log4j.Logger;

import com.gamesmart.chat.io.EventVariable;
import com.gamesmart.chat.io.Request;
import com.gamesmart.chat.page.LoginPage;
import com.gamesmart.chat.vo.PlayerState;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;

import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.LoginRequest;

public class EventListener implements IEventListener{
	Logger logger = Logger.getLogger(EventListener.class);
	private SmartFox sfs;
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
		
		if(event.getType().equals(SFSEvent.EXTENSION_RESPONSE)) {
			String cmd = String.valueOf(event.getArguments().get("cmd"));
            ISFSObject responseParams = (SFSObject) event.getArguments().get("params");
			getCmdResponse(cmd,responseParams);
		}
	}

	private void getCmdResponse(String cmd, ISFSObject responseParams) {
		if(EventVariable.ON_LOGIN.equals(cmd)) {
			
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
						Request.LOBBY_ZONE,
						sfsObject));
			}else {
				logger.debug("connection failed");
			}
		}else if(event.getType().equals(SFSEvent.CONNECTION_ATTEMPT_HTTP)) {
			logger.debug("CONNECT FAILED");
		}
		
		if (event.getType().equals(SFSEvent.LOGIN)) {
			logger.debug("LOGIN SUCCESSFULLY");
			sfs.send(new JoinRoomRequest(Request.LOBBY_ROOM));
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

	
}
