package com.gamesmart.chat.core;

import java.util.List;

import com.gamesmart.chat.page.HomePage;
import com.gamesmart.chat.vo.PlayerState;
import com.smartfoxserver.v2.entities.data.SFSObject;

import sfs2x.client.entities.Buddy;
import sfs2x.client.requests.PublicMessageRequest;

public class SimpleChatClient extends BaseClient{
	private static SimpleChatClient simpleChatClient = null;
	private SimpleChatClient() {}
	
	public void init(PlayerState playerState) {
		this.playerState = playerState;
	}
	
	public PlayerState getPlayerState() {
		return playerState;
	}

	public static SimpleChatClient getInstance() {
		if(simpleChatClient == null) {
			synchronized (SimpleChatClient.class) {
				if(simpleChatClient == null) {
					simpleChatClient = new SimpleChatClient();
				}
			}
		}
		return simpleChatClient;
	}
	
	@Override
	public boolean sendMsg(String msg,long sendTo) {
		return EventListener.getInstance().sendMsg(msg,sendTo);
	}

	@Override
	public void appendMsg(String msg, String alias, long sendFrom,long sendTo) {
		HomePage.getInstance().appendMsg(msg,alias,sendFrom,sendTo);
	}
	
	public void appendBuddyMsg(String msg, String alias, long sendFrom,long sendTo) {
		HomePage.getInstance().appendBuddyMsg(msg,alias,sendFrom,sendTo);
	}

	public void updateAlias(String alias) {
		EventListener.getInstance().updateAlias(alias);
	}

	public void addBuddy(String buddyName) {
		EventListener.getInstance().addBuddy(buddyName);
	}

	public void updateBuddyList(List<Buddy> buddyList) {
		HomePage.getInstance().updateBuddyList(buddyList);
	}

	public void removeBuddy(String buddyName) {
		EventListener.getInstance().removeBuddy(buddyName);;
	}
	
	public void sendBuddyMessage(String buddyName, String message) {
		EventListener.getInstance().sendBuddyMessage(buddyName,message);
	}
}
