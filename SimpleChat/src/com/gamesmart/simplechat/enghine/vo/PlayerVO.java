package com.gamesmart.simplechat.enghine.vo;

@SuppressWarnings("all")
public class PlayerVO {
	private long userId;
	private String alias;
	private long coins;
	private long facebookID;
	private String googleID;
	private String platFormSignIn;
	private String netWorkSignin;
	private int level;
	private long xp;
	private long xpToday;
	private double savings;
	private int freeSpins;
	private int loyaltyLevel;
	private long chips;
	private String pic;
	private boolean isPurchasedWithPuzzle;
	private int clubId;
	
	public PlayerVO(long userId, String alias) {
		this.userId = userId;
		this.alias = alias;
	}
	public long getUserId() {
		return userId;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public void setBalance(long coins) {
		this.coins = coins;
	}
	public void setFacebookID(long faceBookID) {
		this.facebookID = faceBookID;
	}
	public void setGoogleID(String googleID) {
		this.googleID = googleID;
	}
	public void setPlatFormSignIn(String platFormSignIn) {
		this.platFormSignIn = platFormSignIn;
	}
	public void setNetWorkSignin(String netWorkSignin) {
		this.netWorkSignin = netWorkSignin;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setXp(long xp) {
		this.xp = xp;
	}
	public void setXpToday(long xpToday) {
		this.xpToday = xpToday;
	}
	public void setSavings(double savings) {
		this.savings = savings;
	}
	public void setFreeSpins(int freeSpins) {
		this.freeSpins = freeSpins;
	}
	public void setLoyaltyLevel(int loyaltyLevel) {
		this.loyaltyLevel = loyaltyLevel;
	}
	public void setHardCurrency(long chips) {
		this.chips = chips;
	}
	public void setPic(String pic) {this.pic = pic;}
	public void setIsPurchasedWithPuzzle(boolean isPurchasedWithPuzzle) {
		this.isPurchasedWithPuzzle = isPurchasedWithPuzzle;
	}
	public void setClubId(int clubId) {
		this.clubId = clubId;
	}
	@Override
	public String toString() {
		return "[userId=" + userId+ "\n"
				+ ", alias=" + alias + "\n"
				+ ", coins=" + coins 
				+ ", setFacebookID=" + facebookID + "\n"
				+ ", setGoogleID=" + googleID + "\n"
				+ ", platFormSignIn=" + platFormSignIn+ "\n"
				+ ", netWorkSignin=" + netWorkSignin + "\n"
				+ ", level=" + level + "\n"
				+ ", xp=" + xp + "\n"
				+ ", xpToday=" + xpToday+ "\n"
				+ ", savings=" + savings + "\n"
				+ ", freeSpins=" + freeSpins + "\n"
				+ ", loyaltyLevel=" + loyaltyLevel + "\n"
				+ ", chips=" + chips + "\n"
				+ ", pic=" + pic + "\n"
				+ ", isPurchasedWithPuzzle=" + isPurchasedWithPuzzle + "\n"
				+ ", clubId=" + clubId	+ "]";
	}
}
