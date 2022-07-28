package com.gamesmart.simplechat.enghine.dao;

import com.gamesmart.simplechat.enghine.util.ChatUtil;
import com.gamesmart.simplechat.enghine.vo.PlayerVO;

public class PlayerDAO {

	public PlayerVO findPlayerVO(Long userId) {
		return new PlayerVO(userId,ChatUtil.getName());
	}
}
