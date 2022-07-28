package com.gamesmart.chat.vo;

public class PlayerVO {
	private String userId;
	private String pass;//MD5
	private String aliasName;
	
	public PlayerVO(String userId, String pass) {
		this.userId = userId;
		this.pass = pass;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
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
