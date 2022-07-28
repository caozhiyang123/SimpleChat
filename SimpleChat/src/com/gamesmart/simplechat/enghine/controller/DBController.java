package com.gamesmart.simplechat.enghine.controller;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import com.gamesmart.simplechat.enghine.core.DBConnector;
import com.gamesmart.simplechat.enghine.dao.DAOFactory;
import com.gamesmart.simplechat.enghine.vo.DataBaseVO;

public class DBController {
    private static Logger logger = Logger.getLogger(DBController.class);
    BasicDataSource testDataSource;
    private DBConnector defaultConnector;

	public DBController()
	{
        DataBaseVO dataBaseVO = DAOFactory.getDataBaseDAO().getDataBaseVO();
        defaultConnector = new DBConnector(dataBaseVO);
        DataBaseVO.SqlVO sqlVO = dataBaseVO.getSql();
        String url = sqlVO.getUrl();
        String username = sqlVO.getUser();
        String password = sqlVO.getPass();
        testDataSource = createDataSource(url, username, password);
        defaultConnector.setDataSource(testDataSource);
	}

	private BasicDataSource createDataSource(String url, String username, String password)
    {
        String driver = "com.mysql.jdbc.Driver";
        int initSize = 100;//initialSize connection num
        int maxActive = 1000;//max connection num
        int maxIdle = 20 ;//Maximum idle connection
        int minIdle = 5;//Minimum idle connection
        int maxWait = 60000;//timeout waiting time in milliseconds of 6000 milliseconds /1000 equal to 60 seconds
        try{
            //Create a dataSource
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setInitialSize(initSize);
            dataSource.setMaxActive(maxActive);
            dataSource.setMaxIdle(maxIdle);
            dataSource.setMinIdle(minIdle);
            dataSource.setMaxWait(maxWait);
            return dataSource;
        }catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    public DBConnector getDefaultConnector()
    {
        return defaultConnector;
    }
}