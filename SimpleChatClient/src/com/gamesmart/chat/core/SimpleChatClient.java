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
	public void sendMsg(String msg) {
		sfs.send(new PublicMessageRequest(msg, new SFSObject(), sfs.getLastJoinedRoom()));
	}

	@Override
	public void appendMsg(String msg) {
		HomePage.getInstance().appendMsg(msg);
	}
}
