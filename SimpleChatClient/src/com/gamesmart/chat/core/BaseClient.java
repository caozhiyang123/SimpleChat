package com.gamesmart.chat.core;

import com.gamesmart.chat.vo.PlayerState;

import sfs2x.client.SmartFox;
import sfs2x.client.core.SFSBuddyEvent;
import sfs2x.client.core.SFSEvent;

abstract class BaseClient {
	public SmartFox sfs;
	protected PlayerState playerState;
	
	abstract boolean sendMsg(String msg,long sendTo);
	abstract void appendMsg(String msg,String alias,long sendFrom,long sendTo);

	public void connect(SmartFox sfs,String ip,int port) {
		this.sfs = sfs;
		EventListener listener = EventListener.getInstance();
		listener.init(sfs,playerState);
		
		sfs.addEventListener(SFSEvent.CONNECTION, listener);
		sfs.addEventListener(SFSEvent.CONNECTION_LOST, listener);
		sfs.addEventListener(SFSEvent.LOGIN, listener);
		sfs.addEventListener(SFSEvent.LOGIN_ERROR, listener);
		sfs.addEventListener(SFSEvent.LOGOUT, listener);
		sfs.addEventListener(SFSEvent.ROOM_JOIN, listener);
		sfs.addEventListener(SFSEvent.ROOM_JOIN_ERROR, listener);
		sfs.addEventListener(SFSEvent.EXTENSION_RESPONSE, listener);
		sfs.addEventListener(SFSEvent.ROOM_VARIABLES_UPDATE, listener);
		sfs.addEventListener(SFSEvent.USER_VARIABLES_UPDATE, listener);
		sfs.addEventListener(SFSEvent.USER_ENTER_ROOM, listener);
		sfs.addEventListener(SFSEvent.USER_EXIT_ROOM, listener);
		
		sfs.addEventListener(SFSEvent.CONNECTION_ATTEMPT_HTTP, listener);
		sfs.addEventListener(SFSEvent.CONNECTION_RETRY, listener);
		sfs.addEventListener(SFSEvent.CONNECTION_RESUME, listener);
		sfs.addEventListener(SFSEvent.PUBLIC_MESSAGE, listener);
		
		// Add buddy-related event listeners during the SmartFox instance setup
		sfs.addEventListener(SFSBuddyEvent.BUDDY_LIST_INIT, listener);
		sfs.addEventListener(SFSBuddyEvent.BUDDY_ONLINE_STATE_UPDATE, listener);
		sfs.addEventListener(SFSBuddyEvent.BUDDY_MESSAGE, listener);
		sfs.addEventListener(SFSBuddyEvent.BUDDY_VARIABLES_UPDATE, listener);
		sfs.addEventListener(SFSBuddyEvent.BUDDY_ADD, listener);
		sfs.addEventListener(SFSBuddyEvent.BUDDY_REMOVE, listener);
		sfs.addEventListener(SFSBuddyEvent.BUDDY_BLOCK, listener);
		sfs.addEventListener(SFSBuddyEvent.BUDDY_ERROR, listener);
		
		// game type room invitation api
		sfs.addEventListener(SFSEvent.INVITATION, listener);
		sfs.addEventListener(SFSEvent.INVITATION_REPLY, listener);
		sfs.addEventListener(SFSEvent.INVITATION_REPLY_ERROR, listener);
		
		sfs.setDebug(true);
		sfs.connect(ip, port);
	}
}
