package com.gamesmart.simplechat.sfs;

import java.util.List;

import org.apache.log4j.Logger;

import com.gamesmart.simplechat.enghine.core.App;
import com.gamesmart.simplechat.sfs.core.JoinZoneEventHandler;
import com.gamesmart.simplechat.sfs.core.PublicMessageHandler;
import com.gamesmart.simplechat.sfs.core.RoomCreatedHandler;
import com.gamesmart.simplechat.sfs.core.RoomRemovedHandler;
import com.gamesmart.simplechat.sfs.core.UserLogoutHandler;
import com.gamesmart.simplechat.sfs.manager.RoomManager;
import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class RegisterExtension extends SFSExtension{
	Logger logger = Logger.getLogger(RegisterExtension.class);
	
	private static final String ROOM_CREATED = "room_created";

	@Override
	public void init() {
		App.getInstance();
		addRequestHandler(ROOM_CREATED, RoomCreatedHandler.class);

		//addEventHandler(SFSEventType.USER_JOIN_ZONE, JoinZoneEventHandler.class);
		//custom login
		addEventHandler(SFSEventType.USER_LOGIN,JoinZoneEventHandler.class);
		addEventHandler(SFSEventType.USER_LOGOUT, UserLogoutHandler.class);
		addEventHandler(SFSEventType.USER_DISCONNECT, UserLogoutHandler.class);
		addEventHandler(SFSEventType.ROOM_REMOVED, RoomRemovedHandler.class);
		addEventHandler(SFSEventType.PUBLIC_MESSAGE, PublicMessageHandler.class);
		
		createRoom();
	}

	private void createRoom(){
		try {
			RoomManager roomManager = RoomManager.getInstance();
			List<CreateRoomSettings> roomSettings = roomManager.creatRoom();
			for (int i = 0; i < roomSettings.size(); i++) {
				getParentZone().createRoom(roomSettings.get(i));
			}
		} catch (Exception e) {
			logger.error("create room error:",e);
		}
	}
}
