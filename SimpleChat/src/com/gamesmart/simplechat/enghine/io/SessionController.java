package com.gamesmart.simplechat.enghine.io;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.gamesmart.simplechat.enghine.core.ChatManager;

public class SessionController {
	private Map<Long,Session> sessions = new ConcurrentHashMap<>();
	
	public Reply login(Request request) {
		Long userId = request.getUserId();
		if(sessions.get(userId) == null) {
			Session session = new Session();
			sessions.put(userId, session);
		}
		Session session = sessions.get(userId);
		PlayerState playerState = ChatManager.getInstance().createPlayerState(userId);
		session.setPlayerState(playerState);
		return session.login(request);
	}

	public void logout(Request request) {
		sessions.remove(request.getUserId());
	}
}
