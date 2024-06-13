package com.gamesmart.simplechat.enghine.core;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.gamesmart.simplechat.enghine.io.PlayerState;
import com.gamesmart.simplechat.enghine.io.Reply;
import com.gamesmart.simplechat.enghine.io.Request;
import com.gamesmart.simplechat.enghine.io.RequestVariable;
import com.gamesmart.simplechat.sfs.listener.LobbyListener;

public class LobbyManager implements Ichat{
	private Logger logger = Logger.getLogger(LobbyManager.class);
	
	private Map<Long,PlayerState> users = new TreeMap<>();
	private LobbyListener lobbyListener;
	private ScheduledFuture<?> scheduleAtFixedRate;
	private Map<String, RequestHandler> handlers = new ConcurrentHashMap();
	
	private static LobbyManager chatManager = null;
	private LobbyManager() {
		handlers.put(RequestVariable.LOGIN, new LoginHandler(this));
		handlers.put(RequestVariable.LOGOUT, new LogoutHandler(this));
		handlers.put(RequestVariable.ON_PUBLIC_MESSAGE, new OnPubcliMessageHandler(this));
		
		scheduleAtFixedRate = new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(()->match(), 3, 10, TimeUnit.SECONDS);
	}
	
	private synchronized void match() {
		//lobbyListener.match();
	}

	public static LobbyManager getInstance() {
		if(chatManager == null) {
			synchronized(LobbyManager.class) {
				if(chatManager == null) {
					chatManager = new LobbyManager();
				}
			}
		}
		return chatManager;
	}
	
	public PlayerState createPlayerState(long userId) {
		return new PlayerState(userId);
	}
	
	public void setLobbyListener(LobbyListener lobbyListener) {
		this.lobbyListener = lobbyListener;
	}

	@Override
	public void login(PlayerState playerState) {
		logger.info(" - - - user login - - - "+playerState.getUserId());
		users.put(playerState.getUserId(), playerState);
		//get available room
		lobbyListener.match(playerState);
	}
	
	@Override
	public Reply doRequest(Request request, PlayerState playerState) {
		RequestHandler handler = handlers.get(request.getCmd());
		if(handler == null) {
			Reply reply = new Reply();
            reply.setError(Reply.Error.handlerNotExit);
            return reply;
		}
		return handler.doRequest(request, playerState);
	}
	
	@Override
	public void logout(PlayerState playerState) {
		logger.info(" - - - user removed - - - "+playerState.getUserId());
		users.remove(playerState.getUserId());
	}
	
	public void onShutDown() {
		if(scheduleAtFixedRate != null) {
			scheduleAtFixedRate.cancel(true);
		}
	}
}
