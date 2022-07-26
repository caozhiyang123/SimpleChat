package com.gamesmart.simplechat.sfs;

import com.gamesmart.simplechat.sfs.core.JoinZoneEventHandler;
import com.gamesmart.simplechat.sfs.core.RoomCreatedHandler;
import com.gamesmart.simplechat.sfs.core.RoomRemovedHandler;
import com.gamesmart.simplechat.sfs.core.UserLogoutHandler;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class RegisterExtension extends SFSExtension{
	private static final String ROOM_CREATED = "room_created";

	@Override
	public void init() {
		addRequestHandler(ROOM_CREATED, RoomCreatedHandler.class);

		//addEventHandler(SFSEventType.USER_JOIN_ZONE, JoinZoneEventHandler.class);
		addEventHandler(SFSEventType.USER_LOGIN,JoinZoneEventHandler.class);
		addEventHandler(SFSEventType.USER_LOGOUT, UserLogoutHandler.class);
		addEventHandler(SFSEventType.USER_DISCONNECT, UserLogoutHandler.class);
		addEventHandler(SFSEventType.ROOM_REMOVED, RoomRemovedHandler.class);
	}

}
