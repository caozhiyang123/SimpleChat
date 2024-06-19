package com.gamesmart.simplechat.sfs.manager;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.gamesmart.simplechat.enghine.util.GameUtil;
import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.entities.SFSRoomRemoveMode;
import com.smartfoxserver.v2.entities.SFSRoomSettings;
import com.smartfoxserver.v2.entities.match.MatchExpression;
import com.smartfoxserver.v2.entities.match.NumberMatch;
import com.smartfoxserver.v2.entities.variables.RoomVariable;
import com.smartfoxserver.v2.entities.variables.SFSRoomVariable;
import com.smartfoxserver.v2.game.CreateSFSGameSettings;

public class RoomManager {
	private Logger logger = Logger.getLogger(RoomManager.class);
	
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
	
	public List<CreateSFSGameSettings> createStaticGameRoom(){
		List<CreateSFSGameSettings> rooms =new ArrayList<>();
		CreateSFSGameSettings gameSettings = new CreateSFSGameSettings();
		gameSettings.setName(getRoomName());
		gameSettings.setGroupId("chat");
		gameSettings.setUseWordsFilter(true);
		gameSettings.setMaxVariablesAllowed(30);
		gameSettings.setAllowOwnerOnlyInvitation(true);
		gameSettings.setExtension(new CreateRoomSettings.RoomExtensionSettings("SimpleChatExtension","com.gamesmart.simplechat.sfs.RegisterRoomExtension"));
		gameSettings.setMaxUsers(100);
		gameSettings.setGame(true);//flag set true,will allow spectator
		gameSettings.setMaxSpectators(30);
		gameSettings.setGamePublic(true);
		//gameSettings.setPassword("123456");
		gameSettings.setHidden(false);
		gameSettings.setLeaveLastJoinedRoom(true);
		gameSettings.setMinPlayersToStartGame(2);
		gameSettings.setNotifyGameStartedViaRoomVariable(true);
		gameSettings.setPlayerMatchExpression(new MatchExpression("level", NumberMatch.GREATER_THAN_OR_EQUAL_TO, 1));
		//Set the advanced settings of the Room. 
		//These flags allow to configure which events the Room will broadcast and which operations are allowed in the Room itself.
		gameSettings.setDynamic(false);
		//gameSettings.setAutoRemoveMode(SFSRoomRemoveMode.NEVER_REMOVE);
		gameSettings.setRoomSettings(EnumSet.of(SFSRoomSettings.PUBLIC_MESSAGES,SFSRoomSettings.USER_ENTER_EVENT));
		//set room variables
		List<RoomVariable> roomVariables=new ArrayList<>();
		roomVariables.add(new SFSRoomVariable("star","1"));
		roomVariables.add(new SFSRoomVariable("gameState","idle"));
		gameSettings.setRoomVariables(roomVariables);
		//Provide a list of Rooms (e.g. all the Rooms from a specific Group in the Zone) 
		//that the system will use to search more players to join in a private Game. 
		//The players will be matched against the Player Match Expression provided
		//gameSettings.setSearchableRooms(null);
		gameSettings.setSpectatorMatchExpression(new MatchExpression("level", NumberMatch.GREATER_THAN_OR_EQUAL_TO, 10));
		rooms.add(gameSettings);
		logger.info(" - - - is public:"+gameSettings.isGamePublic());
		return rooms;
	}
	
	public List<CreateSFSGameSettings> createGameRoom(){
		List<CreateSFSGameSettings> rooms =new ArrayList<>();
		CreateSFSGameSettings gameSettings = new CreateSFSGameSettings();
		gameSettings.setUseWordsFilter(true);
		gameSettings.setMaxVariablesAllowed(30);
		gameSettings.setMaxSpectators(30);
		gameSettings.setAllowOwnerOnlyInvitation(true);
		gameSettings.setAutoRemoveMode(SFSRoomRemoveMode.WHEN_EMPTY);
		gameSettings.setDynamic(true);
		gameSettings.setExtension(new CreateRoomSettings.RoomExtensionSettings("SimpleChatExtension","com.gamesmart.simplechat.sfs.RegisterRoomExtension"));
		gameSettings.setGame(true);//flag set true,will allow spectator
		gameSettings.setGamePublic(true);
		gameSettings.setGroupId("chat");
		gameSettings.setHidden(false);
		gameSettings.setLeaveLastJoinedRoom(true);
		gameSettings.setMaxUsers(100);
		gameSettings.setMinPlayersToStartGame(2);
		gameSettings.setName(getRoomName());
		gameSettings.setNotifyGameStartedViaRoomVariable(true);
		gameSettings.setPassword("123456");
		gameSettings.setPlayerMatchExpression(new MatchExpression("level", NumberMatch.GREATER_THAN_OR_EQUAL_TO, 1));
		//Set the advanced settings of the Room. 
		//These flags allow to configure which events the Room will broadcast and which operations are allowed in the Room itself.
		gameSettings.setRoomSettings(EnumSet.of(SFSRoomSettings.PUBLIC_MESSAGES,SFSRoomSettings.USER_ENTER_EVENT));
		//set room variables
		List<RoomVariable> roomVariables=new ArrayList<>();
		roomVariables.add(new SFSRoomVariable("star","1"));
		roomVariables.add(new SFSRoomVariable("gameState","idle"));
		gameSettings.setRoomVariables(roomVariables);
		//Provide a list of Rooms (e.g. all the Rooms from a specific Group in the Zone) 
		//that the system will use to search more players to join in a private Game. 
		//The players will be matched against the Player Match Expression provided
		//gameSettings.setSearchableRooms(null);
		gameSettings.setSpectatorMatchExpression(new MatchExpression("level", NumberMatch.GREATER_THAN_OR_EQUAL_TO, 1));
		rooms.add(gameSettings);
		return rooms;
	}
	
	/**
     * chatRoom_36085947722552
     * ...
     * ...
    * */
    private String getRoomName() {
        StringBuffer sb = new StringBuffer("chatRoom");
        sb.append("_");
        sb.append(GameUtil.createSessionId());
        return sb.toString();
    }
}
