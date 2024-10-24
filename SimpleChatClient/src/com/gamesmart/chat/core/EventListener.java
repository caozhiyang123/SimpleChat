package com.gamesmart.chat.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.gamesmart.chat.io.EventVariable;
import com.gamesmart.chat.io.Request;
import com.gamesmart.chat.page.HomePage;
import com.gamesmart.chat.page.LoginPage;
import com.gamesmart.chat.vo.PlayerState;
import com.gamesmart.chat.vo.PlayerVO;
import com.gamesmart.chat.vo.UserVO;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;

import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSBuddyEvent;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.entities.Buddy;
import sfs2x.client.entities.SFSBuddy;
import sfs2x.client.entities.SFSRoom;
import sfs2x.client.entities.SFSUser;
import sfs2x.client.entities.User;
import sfs2x.client.entities.variables.SFSBuddyVariable;
import sfs2x.client.entities.variables.UserVariable;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.LoginRequest;
import sfs2x.client.requests.PublicMessageRequest;
import sfs2x.client.requests.buddylist.AddBuddyRequest;
import sfs2x.client.requests.buddylist.BuddyMessageRequest;
import sfs2x.client.requests.buddylist.InitBuddyListRequest;
import sfs2x.client.requests.buddylist.RemoveBuddyRequest;
import sfs2x.client.requests.buddylist.SetBuddyVariablesRequest;

public class EventListener implements IEventListener {
	Logger logger = Logger.getLogger(EventListener.class);
	private SmartFox sfs;
	private User sfsUser;
	private PlayerState playerState;
	private static EventListener listener;

	private EventListener() {
	}

	public static EventListener getInstance() {
		if (listener == null) {
			synchronized (EventListener.class) {
				if (listener == null) {
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
		handleBuddyMessage(event);
		
		getBaseEventResponse(event);

		if (event.getType().equals(SFSBuddyEvent.BUDDY_VARIABLES_UPDATE)) {
			Map<String, Object> arguments = event.getArguments();
			Boolean isItMe = (Boolean) arguments.get("isItMe");
			List<String> changedVars = (List) arguments.get("changedVars");
			if (isItMe) {
				System.out.println("I've updated the following Buddy Variables:");
				for (String key : changedVars) {
					System.out.println(key + "-->" + sfs.getBuddyManager().getMyVariable(key).getValue());
				}
			} else {
				String buddyName = ((Buddy) arguments.get("buddy")).getName();
				System.out.println("My buddy " + buddyName + " updated the following Buddy Variables:");

				for (String key : changedVars) {
					System.out.println(
							key + "-->" + sfs.getBuddyManager().getBuddyByName(buddyName).getVariable(key).getValue());
				}
			}
			System.out.println(arguments);
		}

		if (event.getType().equals(SFSEvent.ROOM_VARIABLES_UPDATE)) {

		}

		if (event.getType().equals(SFSEvent.USER_VARIABLES_UPDATE)) {
			SFSUser user = (SFSUser) event.getArguments().get("user");
			if (sfsUser == null && user.isItMe()) {
				sfsUser = user;
			}
			List changedVars = (List) event.getArguments().get("changedVars");
			if (user.isItMe()) {
				updateUserVariable(event, changedVars);
				System.out.println("update updateUserVariable");
			} else {
				String aliasName = user.getVariable(EventVariable.USER_ALIAS_NAME).getStringValue();
				long userId = (long) (double) user.getVariable(EventVariable.USER_ID).getDoubleValue();
				HomePage.getInstance().createUserButton(aliasName, userId);
				System.out.println("userId:" + userId + ",aliasName:" + aliasName);
			}
		}

		if (event.getType().equals(SFSEvent.EXTENSION_RESPONSE)) {
			String cmd = String.valueOf(event.getArguments().get("cmd"));
			ISFSObject responseParams = (SFSObject) event.getArguments().get("params");
			getCmdResponse(cmd, responseParams);
		}
	}

	private void handleBuddyMessage(BaseEvent evt) {
		if (evt.getType().equals(SFSBuddyEvent.BUDDY_MESSAGE)) {
			Map<String, Object> arguments = evt.getArguments();
			/**
			 * {data=null, isItMe=false, message=hello......, buddy=[Buddy: 111, id: 4]}
			 */
			boolean isItMe = (Boolean)arguments.get("isItMe");
			String msg = arguments.get("message").toString();
			if(isItMe) {
				System.out.println("I said:" + msg);
			}else {
				Buddy sender = (Buddy)arguments.get("buddy");
				String name = String.format("Guest%s", sender.getName());
				if(sender.getVariable("alias") != null) {
					name = sender.getVariable("alias").getStringValue();
				}
				SimpleChatClient.getInstance().appendBuddyMsg(msg, name, Long.parseLong(sender.getName()), Long.parseLong(sfsUser.getName()));
				
				System.out.println("My buddy " + sender.getName() + " said:" + msg);
			}
		}
		 // As messages are forwarded to the sender too,
        // I have to check if I am the sender
			/*
			 * if(evt.getArguments().containsKey("buddy")) { boolean isItMe =
			 * (Boolean)evt.getArguments().get("isItMe"); Buddy sender =
			 * (Buddy)evt.getArguments().get("buddy"); if (isItMe)
			 * System.out.println("I said:" + evt.getArguments().get("message")); else
			 * System.out.println("My buddy " + sender.getName() + " said:" +
			 * evt.getArguments().get("message")); }
			 */
	}

	protected void updateUserVariable(BaseEvent event, List changedVars) {
		if (changedVars.contains(EventVariable.USER_ALIAS_NAME)) {
			String aliasName = getUserVariable(EventVariable.USER_ALIAS_NAME).getStringValue();
			long sendFrom = (long) (double) getUserVariable(EventVariable.USER_ID).getDoubleValue();
			String userInfo = getUserVariable(EventVariable.USER_INFO).getStringValue();
			playerState.getPlayerVO().setAlias(aliasName);
			playerState.getPlayerVO().setUserInfo(userInfo);

			HomePage.getInstance().appendMsg(userInfo, aliasName, sendFrom, 0);
			HomePage.getInstance().createUserButton(
					SimpleChatClient.getInstance().getPlayerState().getPlayerVO().getAlias(), sendFrom);
		}
		
		if (changedVars.contains(EventVariable.INVITATION_ROOM)) {
			String roomName = getUserVariable(EventVariable.INVITATION_ROOM).getStringValue();
			String roomPass = getUserVariable(EventVariable.ROOM_PASS) == null ? null
					: getUserVariable(EventVariable.ROOM_PASS).getStringValue();
			Double levelLimit = getUserVariable("level_limit").getDoubleValue();
			logger.info(String.format("- - - - invitation join room - - - ,room:%s,pass:%s", roomName, roomPass));
			sfs.send(new JoinRoomRequest(roomName, roomPass));// as user
		}
		
		if (changedVars.contains("name")) {
			String name = getUserVariable("name").getStringValue();
			boolean joinedRoom = getUserVariable("joined_room").getBoolValue();
			String pic = getUserVariable("pic").getStringValue();
			logger.info(String.format("- - - - user joined room,variable updated,name:%s,joinedRoom:%s,pic:%s", name,
					joinedRoom, pic));
		}
		
		if (changedVars.contains("player")) {
			PlayerVO playerVO = (PlayerVO)getUserVariable("player").getSFSObjectValue().getClass("PlayerVO");
		}
	}

	protected UserVariable getUserVariable(String variableName) {
		return sfsUser.getVariable(variableName);
	}

	private void getCmdResponse(String cmd, ISFSObject responseParams) {
		if (EventVariable.MATCHING.equals(cmd)) {
			// join room
			String roomName = responseParams.getUtfString("roomName");
			// sfs.send(new JoinRoomRequest(roomName, "123546"));// as user
			// logger.info(String.format(" - - - user:%s,joined
			// room:%s",playerState.getPlayerVO().getUserId(),roomName));
			// sfs.send(new JoinRoomRequest(roomName, "123546",-1,true));//as spectator
		} else if (EventVariable.ON_PUBLIC_MESSAGE.equals(cmd)) {
			long sendFrom = responseParams.getLong("send_id");
			if (playerState.getPlayerVO().getUserId() != sendFrom) {
				String msg = responseParams.getUtfString("msg").trim();
				String alias = responseParams.getUtfString("alias").trim();
				long sendTo = responseParams.getLong("send_to");
				SimpleChatClient.getInstance().appendMsg(msg, alias, sendFrom, sendTo);
				new Thread(() -> verifyUserButton(sendFrom, alias)).start();
			}
		} else if (EventVariable.ON_JOIN_ZONE.equals(cmd)) {
			Boolean res = responseParams.getBool("result");
			if (!res) {
				String error = responseParams.getUtfString("error");
				System.out.println(error);
				LoginPage.getInstance().loginFailed(error);
			} else {
				// TODO do some init ,change button state
				LoginPage.getInstance().loginSuccessed();
			}
		}

		if (EventVariable.ERROR.equals(cmd)) {
			String msg = responseParams.getUtfString("msg").trim();
			System.out.println(msg);
		}
	}

	private void verifyUserButton(long sendId, String alias) {
		HomePage.getInstance().verifyUserButton(sendId, alias);
	}

	private void getBaseEventResponse(BaseEvent event) {
		if (event.getType().equals(SFSEvent.CONNECTION)) {
			if ((boolean) event.getArguments().get("success")) {
				// join lobby zone
				logger.debug("CONNECT SUFFESSFULLY");
				// sfs.send(new
				// LoginRequest(String.valueOf(playerState.getPlayerVO().getUserId()), "",
				// Request.CHAT_ZONE));
				SFSObject params = new SFSObject();
				params.putUtfString("name", "123");
				sfs.send(new LoginRequest(String.valueOf(playerState.getPlayerVO().getUserId()), "", Request.CHAT_ZONE,
						params));
			} else {
				logger.debug("connection failed");
			}
		} else if (event.getType().equals(SFSEvent.CONNECTION_ATTEMPT_HTTP)) {
			logger.debug("CONNECT FAILED");
		} else if (event.getType().equals(SFSEvent.CONNECTION_RETRY)) {
			logger.debug("CONNECTION_RETRY");
		} else if (event.getType().equals(SFSEvent.CONNECTION_LOST)) {
			System.out.println("CONNECTION_LOST");
		}

		if (event.getType().equals(SFSEvent.LOGIN)) {
			sfsUser = (User) event.getArguments().get("user");
			logger.debug("LOGIN SUCCESSFULLY");
			// update buddy nick name of self
			//initBuddyList();
			//updateBuddyVariable("alias");
			LoginPage.getInstance().loginSuccessed();
			 //sfs.send(new JoinRoomRequest(Request.CHAT_ROOM));
		} else if (event.getType().equals(SFSEvent.LOGIN_ERROR)) {
			logger.debug("LOGIN ERROR");
		}

		/**
		 * This event <code>SFSEvent.USER_ENTER_ROOM</code> is global event,if serve
		 * open this feature, then every player's join room event will be caught.
		 */
		if (event.getType().equals(SFSEvent.USER_ENTER_ROOM)) {
			// {user=[User: 2, Id: 7, isMe: false], room=[Room: chatRoom_820213068276603,
			// Id: 0, GroupId: chat]}
			logger.info(String.format("- - - -USER ENTER ROOM,user id:%s - - - - ", ""));
			User enteredUser = (User) event.getArguments().get("user");
			if (!enteredUser.isItMe()) {
				int userId = Integer.parseInt(enteredUser.getName());
				// int id = sfsUser.getId();
				// int playerId = sfsUser.getPlayerId();
				String name = enteredUser.getVariable("name").getStringValue();
				String pic = enteredUser.getVariable("pic").getStringValue();
				logger.info(String.format("player:%s,joined room,pic:%s,userId:%s", name, pic, userId));
			}
		}

		if (event.getType().equals(SFSEvent.USER_EXIT_ROOM)) {
			// {user=[User: 2, Id: 1, isMe: false], room=[Room: chatRoom_942924845989176,
			// Id: 0, GroupId: chat]}
			User exitedUser = (User) event.getArguments().get("user");
			if (!exitedUser.isItMe()) {
				int userId = Integer.parseInt(exitedUser.getName());
				logger.info(String.format("- - - -USER EXIT ROOM,user id:%s - - - - ", userId));
			}
		}

		if (event.getType().equals(SFSEvent.ROOM_JOIN)) {
			// Room room = (Room)event.getArguments().get("room");
			// sfsUser.setVariable(new SFSUserVariable("joined_room", true));
			// User user = (User)event.getArguments().get("user");
			// open publish message page
			//initBuddyList();
			updateBuddyList();
			logger.debug(String.format("- - - - ROOM JOIN SUCCESSFULLY,self"));
		} else if (event.getType().equals(SFSEvent.ROOM_JOIN_ERROR)) {
			logger.debug("ROOM JOIN FAILED");
			LoginPage.getInstance().loginFailed("账号或密码错误，也可能是网络错误，请重试");
		}

		if (event.getType().equals(SFSEvent.PUBLIC_MESSAGE)) {
			SFSUser user = (SFSUser) event.getArguments().get("sender");
			SFSRoom room = (SFSRoom) event.getArguments().get("room");
			if (!user.isItMe()) {
				String message = event.getArguments().get("message").toString();
				logger.info(String.format("- - -msg send from:%s,msg:%s", user.getName(), message));
			}
		}

		if (event.getType().equals(SFSEvent.LOGOUT) || event.getType().equals(SFSEvent.USER_EXIT_ROOM)) {
			System.out.println("LOGOUT or USER_EXIT_ROOM");
		}
		// evens below is about buddy
		if (event.getType().equals(SFSBuddyEvent.BUDDY_LIST_INIT)) {
			Map<String, Object> arguments = event.getArguments();
			// myVariables
			// buddyList
			List<SFSBuddy> buddyList = (List<SFSBuddy>) arguments.get("buddyList");
			System.out.println(arguments);
			updateBuddyList();
		}
		if (event.getType().equals(SFSBuddyEvent.BUDDY_ONLINE_STATE_UPDATE)) {
			Map<String, Object> arguments = event.getArguments();
			System.out.println(arguments);
		}
		if (event.getType().equals(SFSBuddyEvent.BUDDY_ADD)) {
			Map<String, Object> arguments = event.getArguments();
			SFSBuddy buddy = (SFSBuddy) arguments.get("buddy");
			logger.info(String.format("buddy add successfully,buddy nick name:%s,buddy name:%s,buddy state:%s",
					buddy.getNickName(), buddy.getName(), buddy.getState()));
			updateBuddyList();
		} 
		if (event.getType().equals(SFSBuddyEvent.BUDDY_REMOVE)) {
			Map<String, Object> arguments = event.getArguments();
			// {buddy=[Buddy: 111, id: 23]}
			System.out.println(arguments);
			updateBuddyList();
		}
		if (event.getType().equals(SFSBuddyEvent.BUDDY_BLOCK)) {
			Map<String, Object> arguments = event.getArguments();
			System.out.println(arguments);
		}
		if (event.getType().equals(SFSBuddyEvent.BUDDY_ERROR)) {
			Map<String, Object> arguments = event.getArguments();
			logger.info(String.format("buddy add failed,message:%s", arguments.get("errorMessage")));
		}

	}

	private void updateBuddyVariable(String key) {
		// Set my Buddy Variables
		List vars = new ArrayList();
		// Create a persistent Buddy Variable containing my mood message
		vars.add(new SFSBuddyVariable(SFSBuddyVariable.OFFLINE_PREFIX + key, playerState.getPlayerVO().getAlias()));
		sfs.send(new SetBuddyVariablesRequest(vars));
	}

	public boolean sendMsg(String msg, long sendTo) {
		boolean res = false;
		try {
			if (sfs.isConnected()) {
				SFSObject sfsObject = new SFSObject();
//				sfsObject.putClass("player", new PlayerVO());
				sfsObject.putLong("send_to", sendTo);
				sfs.send(new PublicMessageRequest(msg, sfsObject, null));
				res = true;
			} else {
				logger.error("sfs is disconnected");
			}
			return res;
		} catch (Exception e) {
			logger.error("send msg error:", e);
		}
		return res;
	}

	public void createBatchJob() {
		new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(new RefreshUserListEvent(), 5, 10, TimeUnit.SECONDS);
	}

	class RefreshUserListEvent implements Runnable {
		@Override
		public void run() {
			List<User> userList = sfs.getUserManager().getUserList();
			List<UserVO> joinedUsers = new ArrayList<UserVO>();
			if (userList != null) {
				for (User user : userList) {
					long userId = Long.valueOf(user.getName());
					String alias = user.getVariable(EventVariable.USER_ALIAS_NAME).getStringValue();
					UserVO userVO = new UserVO(userId, alias);
					joinedUsers.add(userVO);
				}
			}
			HomePage.getInstance().updateUserList(joinedUsers);
			System.out.println(joinedUsers.toString());
		}
	}

	public void updateAlias(String alias) {
		// TODO

	}

	private void initBuddyList() {
		sfs.send(new InitBuddyListRequest());
	}

	/*
	 * * open buddy list system in zone file <buddyList active="true">
	 * 
	 * BuddyList request error BuddyList is not inited. Please send an
	 * InitBuddyRequest first. Can't add buddy while off-line
	 */
	public void addBuddy(String buddyName) {
		sfs.send(new AddBuddyRequest(buddyName));
	}

	private void updateBuddyList() {
		List<Buddy> buddyList = sfs.getBuddyManager().getBuddyList();
		SimpleChatClient.getInstance().updateBuddyList(buddyList);
	}

	public void removeBuddy(String buddyName) {
		sfs.send(new RemoveBuddyRequest(buddyName));
	}

	public void sendBuddyMessage(String buddyName, String message) {
		/*
		 * Buddy friend = null; List<Buddy> buddyList =
		 * sfs.getBuddyManager().getBuddyList(); for (Buddy buddy : buddyList) { if
		 * (buddy.getName().equals(buddyName)) { friend = buddy; break; } }
		 */
		Buddy friend = sfs.getBuddyManager().getBuddyByName(buddyName);
		sfs.send(new BuddyMessageRequest(message, friend));
	}
}
