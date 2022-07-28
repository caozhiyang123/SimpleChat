package com.gamesmart.simplechat.enghine.io;

import com.gamesmart.simplechat.enghine.vo.PlayerVO;

public class PlayerState {
	private long userId;
	private PlayerVO playerVO;
	
	public PlayerState(long userId) {
		this.userId = userId;
	}
	public long getUserId() {
		return userId;
	}
	
	public PlayerVO getPlayerVO() {
		return playerVO;
	}
	public void setPlayerVO(PlayerVO playerVO) {
		this.playerVO = playerVO;
	}
	
}
