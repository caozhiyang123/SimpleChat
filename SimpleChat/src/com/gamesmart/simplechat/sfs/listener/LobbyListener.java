package com.gamesmart.simplechat.sfs.listener;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gamesmart.simplechat.enghine.io.PlayerState;
import com.gamesmart.simplechat.enghine.listener.IListener;
import com.gamesmart.simplechat.enghine.util.StringUtils;
import com.gamesmart.simplechat.sfs.manager.RoomManager;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.entities.match.BoolMatch;
import com.smartfoxserver.v2.entities.match.MatchExpression;
import com.smartfoxserver.v2.entities.match.NumberMatch;
import com.smartfoxserver.v2.entities.match.RoomProperties;
import com.smartfoxserver.v2.entities.match.StringMatch;
import com.smartfoxserver.v2.entities.variables.SFSUserVariable;
import com.smartfoxserver.v2.entities.variables.UserVariable;
import com.smartfoxserver.v2.extensions.SFSExtension;
import com.smartfoxserver.v2.game.CreateSFSGameSettings;

public class LobbyListener implements IListener{
	private Logger logger = Logger.getLogger(LobbyListener.class);
	
	private final int matchLimit = 2;
	private SFSExtension parent;

	public LobbyListener(SFSExtension parent) {
		this.parent = parent;
	}


	@Override
	public void match() {
		logger.info("- - - - matching - - - - ");
		List<User> matchedUsers = parent.getApi().findUsers(parent.getParentZone().getUserList(),
				new MatchExpression("user_level",NumberMatch.GREATER_THAN_OR_EQUAL_TO,1).and("joined_room", BoolMatch.EQUALS, false), matchLimit);
		if(matchedUsers.size() == matchLimit) {
			//no available room and create new one
			Room game = createRoom();
			//create room successfully
			//broadcast to matched users
			//SFSObject returnObj = new SFSObject();
			//returnObj.putUtfString("roomName",game.getName());
			//parent.getApi().sendExtensionResponse("matching", returnObj, matchedUsers, null, true);
			//only private game can use invitation event and listen invitation SUCCESS/FAILE event
			//parent.getGameApi().sendJoinRoomInvitation(game, null, matchedUsers, 60,false,true,returnObj);
			List<UserVariable> list = new ArrayList<UserVariable>();
			list.add(new SFSUserVariable("invitation_room", game.getName(),false,true));
			list.add(new SFSUserVariable("room_pass", game.getPassword(),false,true));
			for (User user: matchedUsers) {
				parent.getApi().setUserVariables(user,list,true,false);
			}
		}
	}
	
	private Room createRoom(){
		try {
			RoomManager roomManager = RoomManager.getInstance();
			List<CreateSFSGameSettings> roomSettings = roomManager.createGameRoom();
			//owner null ,owner the server
			return parent.getGameApi().createGame(parent.getParentZone(), roomSettings.get(0), null,false,false);
		} catch (Exception e) {
			logger.error("create room error:",e);
		}
		return null;
	}

	@Override
	public void match(PlayerState playerState) {
		//existed available room
		logger.info("- - - - matching - - - -");
		List<Room> availableRooms = parent.getApi().findRooms(parent.getParentZone().getRoomListFromGroup("chat"), 
				new MatchExpression(RoomProperties.IS_GAME, BoolMatch.EQUALS, true).
				and(RoomProperties.HAS_FREE_PLAYER_SLOTS, BoolMatch.EQUALS, true).
				and(RoomProperties.IS_PRIVATE, BoolMatch.EQUALS, false).
				and("gameState", StringMatch.EQUALS, "idle"), 1);
		if(availableRooms.size() > 0) {
			Room game = availableRooms.get(0);
			logger.info("- - - - matching - - - - room:"+game.getName());
			List<UserVariable> list = new ArrayList<UserVariable>();
			list.add(new SFSUserVariable("invitation_room", game.getName(),false,true));
			list.add(new SFSUserVariable("room_pass", game.getPassword(),false,true));
			list.add(new SFSUserVariable("level_limit", 100L,false,true));
			User user = parent.getParentZone().getUserByName(String.valueOf(playerState.getUserId()));
			parent.getApi().setUserVariables(user,list,true,false);
		}
	}
}
