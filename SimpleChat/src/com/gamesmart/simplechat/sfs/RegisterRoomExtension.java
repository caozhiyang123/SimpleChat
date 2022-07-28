package com.gamesmart.simplechat.sfs;

import com.gamesmart.simplechat.sfs.core.PublicMessageHandler;
import com.gamesmart.simplechat.sfs.core.UserJoinRoomHandler;
import com.gamesmart.simplechat.sfs.core.UserLogoutHandler;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.SFSExtension;

public class RegisterRoomExtension extends SFSExtension{
	private String ON_PUBLIC_MESSAGE = "on_public_message";
	
	@Override
	public void init() {
		addEventHandler(SFSEventType.USER_DISCONNECT, UserLogoutHandler.class);
        addEventHandler(SFSEventType.USER_LEAVE_ROOM, UserLogoutHandler.class);
        addEventHandler(SFSEventType.USER_JOIN_ROOM, UserJoinRoomHandler.class);
        addEventHandler(SFSEventType.PUBLIC_MESSAGE, PublicMessageHandler.class);
	}
}