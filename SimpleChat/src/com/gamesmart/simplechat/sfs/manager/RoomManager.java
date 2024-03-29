package com.gamesmart.simplechat.sfs.manager;

import java.util.ArrayList;
import java.util.List;

import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.entities.SFSRoomRemoveMode;

public class RoomManager {
	private static RoomManager roomManager;
	private RoomManager() {}
	
	public static RoomManager getInstance() {
		if(roomManager == null) {
			synchronized(RoomManager.class) {
				if(roomManager == null) {
					roomManager = new RoomManager();
				}
			}
		}
		return roomManager;
	}
	
	public List<CreateRoomSettings> creatRoom() {
		List<CreateRoomSettings> rooms =new ArrayList<>();
		CreateRoomSettings settings = new CreateRoomSettings();
		settings.setName("chatRoom");
		settings.setMaxVariablesAllowed(30);
		settings.setGame(true);
		settings.setUseWordsFilter(true);
		settings.setExtension(new CreateRoomSettings.RoomExtensionSettings("SimpleChatExtension","com.gamesmart.simplechat.sfs.RegisterRoomExtension"));
		settings.setDynamic(true);
		settings.setAutoRemoveMode(SFSRoomRemoveMode.NEVER_REMOVE);
		settings.setMaxUsers(100);
        rooms.add(settings);
        return rooms;
	}
}
