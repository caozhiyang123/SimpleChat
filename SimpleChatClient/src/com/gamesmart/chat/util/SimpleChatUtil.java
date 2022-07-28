package com.gamesmart.chat.util;

import com.gamesmart.chat.vo.PlayerState;
import com.gamesmart.chat.vo.PlayerVO;

public class SimpleChatUtil {
	
	public static PlayerState createPlayerState(String userName,String pass) {
		PlayerState playerState = new PlayerState();
		playerState.setPlayerVO(new PlayerVO(userName,pass));
		return playerState;
	}
}
