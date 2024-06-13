package com.gamesmart.simplechat.enghine.listener;

import com.gamesmart.simplechat.enghine.io.PlayerState;

public interface IListener {
	public void match();
	public void match(PlayerState playerState);
}
