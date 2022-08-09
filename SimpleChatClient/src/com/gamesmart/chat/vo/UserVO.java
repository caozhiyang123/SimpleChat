package com.gamesmart.chat.vo;

public class UserVO {
	private long userId;
	private String alias;
	public UserVO(long userId, String alias) {
		super();
		this.userId = userId;
		this.alias = alias;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	@Override
	public String toString() {
		return "UserVO [userId=" + userId + ", alias=" + alias + "]";
	}
}
