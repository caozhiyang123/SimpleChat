package com.gamesmart.chat.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.gamesmart.chat.io.EventVariable;
import com.gamesmart.chat.io.Request;
import com.gamesmart.chat.page.HomePage;
import com.gamesmart.chat.page.LoginPage;
import com.gamesmart.chat.vo.PlayerState;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;

import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.entities.SFSUser;
import sfs2x.client.entities.User;
import sfs2x.client.entities.variables.UserVariable;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.LoginRequest;
import sfs2x.client.requests.PublicMessageRequest;

public class EventListener implements IEventListener{
	Logger logger = Logger.getLogger(EventListener.class);
	private SmartFox sfs;
	private User sfsUser;
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
		if (event.getType().equals(SFSEvent.USER_VARIABLES_UPDATE)) {
            SFSUser user= (SFSUser) event.getArguments().get("user");
            if (sfsUser == null&&user.isItMe()) {
                sfsUser =user;
            }
            List changedVars = (List) event.getArguments().get("changedVars");
            if (user.isItMe()){
                updateUserVariable(event, changedVars);
            }else if (changedVars.contains(EventVariable.USER_ALIAS_NAME)){
        		String aliasName = user.getVariable(EventVariable.USER_ALIAS_NAME).getStringValue();
        		long userId = (long)(double)user.getVariable(EventVariable.USER_ID).getDoubleValue();
        		HomePage.getInstance().createUserButton(aliasName,userId);
            }
        }
		
		if(event.getType().equals(SFSEvent.EXTENSION_RESPONSE)) {
			String cmd = String.valueOf(event.getArguments().get("cmd"));
            ISFSObject responseParams = (SFSObject) event.getArguments().get("params");
			getCmdResponse(cmd,responseParams);
		}
		
		getBaseEventResponse(event);
	}

	protected void updateUserVariable(BaseEvent event, List changedVars) {
		if (changedVars.contains(EventVariable.USER_ALIAS_NAME)){
			String aliasName = getUserVariable(EventVariable.USER_ALIAS_NAME).getStringValue();
			long userId = (long)(double)getUserVariable(EventVariable.USER_ID).getDoubleValue();
			String userInfo = getUserVariable(EventVariable.USER_INFO).getStringValue();
			playerState.getPlayerVO().setAlias(aliasName);
			playerState.getPlayerVO().setInfo(userInfo);
			
			HomePage.getInstance().appendMsg(userInfo);
			HomePage.getInstance().createUserButton(SimpleChatClient.getInstance().getPlayerState().getPlayerVO().getAlias(),userId);
		}
	}
	
	protected UserVariable getUserVariable(String variableName) {
        return sfsUser.getVariable(variableName);
    }
	
	private void getCmdResponse(String cmd, ISFSObject responseParams) {
		if(EventVariable.ON_PUBLIC_MESSAGE.equals(cmd)) {
			long sendId = responseParams.getLong("send_id");
			if(playerState.getPlayerVO().getUserId() != sendId) {
				String msg = responseParams.getUtfString("msg").trim();
				String alias = responseParams.getUtfString("alias").trim();
				SimpleChatClient.getInstance().appendMsg(msg);
				new Thread(()->verifyUserButton(sendId,alias)).start();
			}
		}else if(EventVariable.ON_JOIN_ZONE.equals(cmd)) {
			Boolean res = responseParams.getBool("result");
			if(!res) {
				String error = responseParams.getUtfString("error");
				System.out.println(error);
				LoginPage.getInstance().loginFailed(error);
			}else {
				//TODO do some init ,change button state
				LoginPage.getInstance().loginSuccessed();
			}
		}
		
		if(EventVariable.ERROR.equals(cmd)) {
			String msg = responseParams.getUtfString("msg").trim();
			System.out.println(msg);
		}
	}

	private void verifyUserButton(long sendId, String alias) {
		HomePage.getInstance().verifyUserButton(sendId,alias);
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
						String.valueOf(playerState.getPlayerVO().getUserId()),"",Request.CHAT_ZONE));
			}else {
				logger.debug("connection failed");
			}
		}else if(event.getType().equals(SFSEvent.CONNECTION_ATTEMPT_HTTP)) {
			logger.debug("CONNECT FAILED");
		}else if(event.getType().equals(SFSEvent.CONNECTION_RETRY)) {
			logger.debug("CONNECTION_RETRY");
		}else if(event.getType().equals(SFSEvent.CONNECTION_LOST)) {
			System.out.println("CONNECTION_LOST");
		}
		
		if (event.getType().equals(SFSEvent.LOGIN)) {
			sfsUser = (User) event.getArguments().get("user");
			
			logger.debug("LOGIN SUCCESSFULLY");
			
			sfs.send(new JoinRoomRequest(Request.CHAT_ROOM));
		}else if(event.getType().equals(SFSEvent.LOGIN_ERROR)) {
			logger.debug("LOGIN ERROR");
		}
		
		if (event.getType().equals(SFSEvent.ROOM_JOIN)) {
			//open publish message page
			logger.debug("ROOM JOIN SUCCESSFULLY");
		}else if (event.getType().equals(SFSEvent.ROOM_JOIN_ERROR)) {
			logger.debug("ROOM JOIN FAILED");
			LoginPage.getInstance().loginFailed("账号或密码错误，也可能是网络错误，请重试");
		}
		
		if(event.getType().equals(SFSEvent.LOGOUT) 
				|| event.getType().equals(SFSEvent.USER_EXIT_ROOM)) {
			System.out.println("LOGOUT or USER_EXIT_ROOM");
		}
	}

	public boolean sendMsg(String msg) {
		boolean res = false;
		try {
			//TODO
			if(sfs.isConnected()) {
				sfs.send(new PublicMessageRequest(msg, new SFSObject(), sfs.getLastJoinedRoom()));
				res = true;
			}else {
				logger.error("sfs is disconnected");
			}
			return res;
		} catch (Exception e) {
			logger.error("send msg error:", e);
		}
		return res;
	}

	public void createBatchJob() {
		new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(new UserListListener(), 30, 10, TimeUnit.SECONDS);
	}
	
	class UserListListener implements Runnable{

		@Override
		public void run() {
			List<User> userList = sfs.getUserManager().getUserList();
			List<Long> joinedUsers = new ArrayList<Long>();
			if(userList != null) {
				for (User user : userList) {
					long userId = Long.valueOf(user.getName());
					joinedUsers.add(userId);
				}
			}
			HomePage.getInstance().updateUserList(joinedUsers);
			System.out.println(joinedUsers.toString());
		}
		
	}
	
}
