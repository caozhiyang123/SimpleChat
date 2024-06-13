package com.gamesmart.simplechat.enghine.io;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.gamesmart.simplechat.enghine.core.LobbyManager;

public class SessionController {
	Logger logger = Logger.getLogger(SessionController.class);
	
	private Map<Long,Session> sessions = new ConcurrentHashMap<>();
	
	public Reply login(Request request) {
		Long userId = request.getUserId();
		if(sessions.get(userId) != null) {
			sessions.remove(userId);
		}
		
		Session session = new Session();
		sessions.put(userId, session);
		logger.debug("= = login,userId:"+userId+",session:"+session);
		
		PlayerState playerState = LobbyManager.getInstance().createPlayerState(userId);
		session.setPlayerState(playerState);
		return session.login(request);
	}

	public Reply doRequest(Request request) {
		Session session = sessions.get(request.getUserId());
		logger.debug("= = doRequest,userId:"+request.getUserId()+",session:"+sessions.toString());
		Reply reply = null;
		if(session == null) {
			reply = new Reply();
			reply.setError(Reply.Error.userNotLogin);
			return reply;
		}
		
		reply = session.doRequest(request);
		return reply;
	}
	
	public void logout(Request request) {
		Session session = sessions.get(request.getUserId());
		logger.debug("= = user logout,userId:"+request.getUserId()+",session:"+sessions.toString());
		if(session == null) {
			logger.error(String.format(" - - - user not login - - -,user:%s",request.getUserId()));
			return;
		}
		
		session.doRequest(request);
		sessions.remove(request.getUserId());
	}
}