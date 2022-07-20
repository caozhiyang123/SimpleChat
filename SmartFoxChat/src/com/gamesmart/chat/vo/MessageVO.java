package com.gamesmart.chat.vo;

public class MessageVO {
	private int id;
	private long fromUserId;
	private String msg;
	private boolean isSent;
	
	public MessageVO(long fromUserId, String msg, boolean isSent) {
		this.fromUserId = fromUserId;
		this.msg = msg;
		this.isSent = isSent;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(long fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isSent() {
		return isSent;
	}
	public void setIsSent(boolean isSent) {
		this.isSent = isSent;
	}
}
