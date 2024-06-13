package com.gamesmart.simplechat.enghine.core;

import com.gamesmart.simplechat.enghine.io.PlayerState;
import com.gamesmart.simplechat.enghine.io.Reply;
import com.gamesmart.simplechat.enghine.io.Request;

public interface Ichat {
	public void login(PlayerState playerState);
	public void logout(PlayerState playerState);
	public Reply doRequest(Request request,PlayerState playerState);
}
