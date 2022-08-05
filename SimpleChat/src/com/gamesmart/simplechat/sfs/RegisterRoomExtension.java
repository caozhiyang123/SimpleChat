package com.gamesmart.simplechat.sfs;

import com.gamesmart.simplechat.sfs.core.UserJoinRoomHandler;
import com.gamesmart.simplechat.sfs.core.UserLogoutHandler;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class RegisterRoomExtension extends SFSExtension{
	
	@Override
	public void init() {
		addEventHandler(SFSEventType.USER_JOIN_ROOM, UserJoinRoomHandler.class);
		addEventHandler(SFSEventType.USER_DISCONNECT, UserLogoutHandler.class);
        addEventHandler(SFSEventType.USER_LEAVE_ROOM, UserLogoutHandler.class);
	}
}