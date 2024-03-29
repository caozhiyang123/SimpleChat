package com.gamesmart.chat.core;

import com.gamesmart.chat.page.HomePage;
import com.gamesmart.chat.vo.PlayerState;
import com.smartfoxserver.v2.entities.data.SFSObject;

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

	public void updateAlias(String alias) {
		EventListener.getInstance().updateAlias(alias);
	}
}
