package com.gamesmart.chat.vo;

import javax.swing.JButton;

public class BuddyVO {
	private String buddyName;
	private long buddyId;
	private String state;
	private boolean isBuddy;//false means it is temp
	private JButton button;
	
	public BuddyVO(String buddyName, long buddyId, String state, boolean isBuddy,JButton button) {
		super();
		this.buddyName = buddyName;
		this.buddyId = buddyId;
		this.state = state;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
