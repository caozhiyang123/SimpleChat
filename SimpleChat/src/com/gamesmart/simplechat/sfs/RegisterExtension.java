package com.gamesmart.simplechat.sfs;

import java.util.List;

import org.apache.log4j.Logger;

import com.gamesmart.simplechat.enghine.core.App;
import com.gamesmart.simplechat.enghine.core.LobbyManager;
import com.gamesmart.simplechat.sfs.core.JoinZoneEventHandler;
import com.gamesmart.simplechat.sfs.core.OnCustomLoginEventHandler;
import com.gamesmart.simplechat.sfs.core.PublicMessageHandler;
import com.gamesmart.simplechat.sfs.core.RoomCreatedHandler;
import com.gamesmart.simplechat.sfs.core.RoomRemovedHandler;
import com.gamesmart.simplechat.sfs.core.UserLogoutHandler;
import com.gamesmart.simplechat.sfs.listener.LobbyListener;
import com.gamesmart.simplechat.sfs.manager.RoomManager;
import com.smartfoxserver.v2.annotations.Instantiation;
import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.extensions.SFSExtension;
import com.smartfoxserver.v2.game.CreateSFSGameSettings;

@Instantiation(Instantiation.InstantiationMode.SINGLE_INSTANCE)
public class RegisterExtension extends SFSExtension{
	Logger logger = Logger.getLogger(RegisterExtension.class);
	
	private static final String ROOM_CREATED = "room_created";

	@Override
	public void init() {
		App.getInstance();
		LobbyManager.getInstance().setLobbyListener(new LobbyListener(this));
		
		addRequestHandler(ROOM_CREATED, RoomCreatedHandler.class);

		//<isCustomLogin>true</isCustomLogin>
        //The Zone must be configured with the customLogin attribute set to true.
        addEventHandler(SFSEventType.USER_LOGIN, OnCustomLoginEventHandler.class);
		addEventHandler(SFSEventType.USER_JOIN_ZONE, JoinZoneEventHandler.class);
		addEventHandler(SFSEventType.USER_LOGOUT, UserLogoutHandler.class);
		addEventHandler(SFSEventType.USER_DISCONNECT, UserLogoutHandler.class);
		addEventHandler(SFSEventType.ROOM_REMOVED, RoomRemovedHandler.class);
		addEventHandler(SFSEventType.PUBLIC_MESSAGE, PublicMessageHandler.class);
		//addEventHandler(SFSEventType.GAME_INVITATION_SUCCESS,InvitataionHandler.class);
		
		createRoom();
	}

	/*
	 * create room dynamicly
	 **/
	private void createRoom(){
		try {
			RoomManager roomManager = RoomManager.getInstance();
			List<CreateSFSGameSettings> roomSettings = roomManager.createStaticGameRoom();
			for (int i = 0; i < roomSettings.size(); i++) {
				//owner null ,owner the server
				Room game = getGameApi().createGame(getParentZone(), roomSettings.get(i), null,false,false);
			}
		} catch (Exception e) {
			logger.error("create room error:",e);
		}
	}
}
