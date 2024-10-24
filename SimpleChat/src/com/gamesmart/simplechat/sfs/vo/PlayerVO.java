package com.gamesmart.simplechat.sfs.vo;

import com.smartfoxserver.v2.protocol.serialization.SerializableSFSType;

public class PlayerVO implements SerializableSFSType{
	private long userId;
	private String pass;//MD5
	private String alias;
	private String userInfo;
	private long coins;
	
	public PlayerVO() {
		
	}
	
	public PlayerVO(long userId, String pass) {
		super();
		this.userId = userId;
		this.pass = pass;
	}

	public PlayerVO(long userId, String alias, long coins) {
		this.userId = userId;
		this.alias = alias;
		this.coins = coins;
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public long getCoins() {
		return coins;
	}

	public void setCoins(long coins) {
		this.coins = coins;
	}

	@Override
	public String toString() {
		return "PlayerVO [userId=" + userId + ", pass=" + pass + ", alias=" + alias + ", userInfo=" + userInfo
				+ ", coins=" + coins + "]";
	}

}
