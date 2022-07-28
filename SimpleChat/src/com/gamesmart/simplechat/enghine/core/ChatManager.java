package com.gamesmart.simplechat.enghine.core;

import com.gamesmart.simplechat.enghine.io.PlayerState;

public class ChatManager implements Ichat{
	private static ChatManager chatManager = null;
	private ChatManager() {}
	
	public static ChatManager getInstance() {
		if(chatManager == null) {
			synchronized(ChatManager.class) {
				if(chatManager == null) {
					chatManager = new ChatManager();
				}
			}
		}
		return chatManager;
	}
	
	public PlayerState createPlayerState(long userId) {
		return new PlayerState(userId);
	}
}
