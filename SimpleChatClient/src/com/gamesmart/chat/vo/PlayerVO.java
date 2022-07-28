package com.gamesmart.chat.vo;

public class PlayerVO {
	private long userId;
	private String pass;//MD5
	private String aliasName;
	
	public PlayerVO(long userId, String pass) {
		this.userId = userId;
		this.pass = pass;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setAlias(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getAlias() {
		return this.aliasName;
	}

}
