package com.gamesmart.simplechat.enghine.core;

import com.gamesmart.simplechat.enghine.io.PlayerState;
import com.gamesmart.simplechat.enghine.io.Reply;
import com.gamesmart.simplechat.enghine.io.Request;

public class LoginHandler extends RequestHandler {

	public LoginHandler(LobbyManager lobby) {
		super(lobby);
	}

	@Override
	public Reply doRequest(Request request, PlayerState playerState) {
		lobby.login(playerState);
		return new Reply();
	}

}
