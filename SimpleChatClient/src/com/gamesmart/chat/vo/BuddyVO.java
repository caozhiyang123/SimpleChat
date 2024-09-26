package com.gamesmart.chat.vo;

import javax.swing.JButton;

public class BuddyVO {
	private String buddyName;
	private long buddyId;
	private boolean isOnline;
	private boolean isBuddy;//false means it is temp
	private JButton button;
	
	public BuddyVO(String buddyName, long buddyId, boolean isOnline, boolean isBuddy,JButton button) {
		super();
		this.buddyName = buddyName;
		this.buddyId = buddyId;
		this.isOnline = isOnline;
		this.isBuddy = isBuddy;
		this.button = button;
	}
	public String getBuddyName() {
		return buddyName;
	}
	public void setBuddyName(String buddyName) {
		this.buddyName = buddyName;
	}
	public long getBuddyId() {
		return buddyId;
	}
	public void setBuddyId(long buddyId) {
		this.buddyId = buddyId;
	}
	public boolean isOnline() {
		return isOnline;
	}
	public boolean isBuddy() {
		return isBuddy;
	}
	public void setBuddy(boolean isBuddy) {
		this.isBuddy = isBuddy;
	}
	public JButton getButton() {
		return button;
	}
	public void setButton(JButton button) {
		this.button = button;
	}
}
