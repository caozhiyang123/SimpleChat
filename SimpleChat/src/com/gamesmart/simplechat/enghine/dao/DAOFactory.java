package com.gamesmart.simplechat.enghine.dao;

public class DAOFactory {

	public static PlayerDAO getPlayerDAO() {return new PlayerDAO();}
	public static DatabaseDAO getDataBaseDAO(){	return new DatabaseDAO(); }
}
