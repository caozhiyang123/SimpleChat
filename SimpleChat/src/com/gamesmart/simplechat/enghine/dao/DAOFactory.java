package com.gamesmart.simplechat.enghine.dao;

public class DAOFactory {

	public static PlayerDAO getPlayerDAO() {
		return new PlayerDAO();
	}
}
