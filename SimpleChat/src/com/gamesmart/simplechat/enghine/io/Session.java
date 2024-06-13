package com.gamesmart.simplechat.enghine.io;

import com.gamesmart.simplechat.enghine.core.LobbyManager;
import com.gamesmart.simplechat.enghine.util.ChatUtil;
import com.gamesmart.simplechat.enghine.vo.PlayerVO;

public class Session {
	private PlayerState playerState;
	
	public Reply login(Request request) {
		Reply reply = new Reply();
		//PlayerVO playerVO = DAOFactory.getPlayerDAO().findPlayerVO(request.getUserId());
		PlayerVO playerVO = new PlayerVO(request.getUserId(),ChatUtil.getName(),ChatUtil.getLevel());
		playerState.setPlayerVO(playerVO);
		if(playerVO == null) {
			reply.setError(Reply.Error.userNotExist);
		}
		LobbyManager.getInstance().doRequest(request,playerState);
		reply.setSession(this);
		return reply;
	}

	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}

	public PlayerState getPlayerState() {
		return playerState;
	}

	public Reply doRequest(Request request) {
		Reply reply = new Reply();
		LobbyManager.getInstance().doRequest(request,playerState);
		reply.setSession(this);
		return reply;
	}
}
