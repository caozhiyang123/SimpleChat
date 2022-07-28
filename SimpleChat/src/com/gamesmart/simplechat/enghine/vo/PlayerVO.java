package com.gamesmart.simplechat.enghine.vo;

public class PlayerVO {
	private long userId;
	private String alias;
	
	public PlayerVO(long userId, String alias) {
		this.userId = userId;
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}
}
