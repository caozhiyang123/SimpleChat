package com.gamesmart.simplechat.enghine.io;

import com.gamesmart.simplechat.enghine.dao.DAOFactory;
import com.gamesmart.simplechat.enghine.vo.PlayerVO;

public class Session {
	private PlayerState playerState;
	
	public Reply login(Request request) {
		Reply reply = new Reply();
		//PlayerVO playerVO = DAOFactory.getPlayerDAO().findPlayerVO(request.getUserId());
		PlayerVO playerVO = new PlayerVO(request.getUserId(),String.valueOf(request.getUserId()));
		playerState.setPlayerVO(playerVO);
		if(playerVO == null) {
			reply.setError(Reply.Error.userNotExist);
		}
		reply.setSession(this);
		return reply;
	}

	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}

	public PlayerState getPlayerState() {
		return playerState;
	}
}
