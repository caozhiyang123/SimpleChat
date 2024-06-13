package com.gamesmart.simplechat.enghine.core;

import com.gamesmart.simplechat.enghine.io.PlayerState;
import com.gamesmart.simplechat.enghine.io.Reply;
import com.gamesmart.simplechat.enghine.io.Request;

public class LogoutHandler extends RequestHandler {

	public LogoutHandler(LobbyManager lobby) {
		super(lobby);
	}

	@Override
	public Reply doRequest(Request request, PlayerState playerState) {
		lobby.logout(playerState);
		return new Reply();
	}

}