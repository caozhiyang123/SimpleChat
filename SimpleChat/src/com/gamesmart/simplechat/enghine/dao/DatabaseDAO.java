package com.gamesmart.simplechat.enghine.dao;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.gamesmart.simplechat.enghine.core.ConfigConnector;
import com.gamesmart.simplechat.enghine.vo.DataBaseVO;
import com.gamesmart.simplechat.enghine.vo.DataBaseVO.MongoVO;
import com.gamesmart.simplechat.enghine.vo.DataBaseVO.MongoVO.ServersVO;
import com.gamesmart.simplechat.enghine.vo.DataBaseVO.SqlVO;

import java.util.ArrayList;
import java.util.List;


public class DatabaseDAO extends BaseDAO
{
	public DataBaseVO getDataBaseVO()
    {
        JSONObject jsonObject = ConfigConnector.getInstance().parseToJson("server.json");

        JSONObject databaseObj = (JSONObject)jsonObject.get("database");
        DataBaseVO dataBaseVO = getDateBaseVO(databaseObj);

		return dataBaseVO;
	}

	public List<DataBaseVO> getDateBaseVOs()
    {
        JSONObject jsonObject = ConfigConnector.getInstance().parseToJson("server.json");

        List<DataBaseVO> dataBaseVOs = new ArrayList<>();
        JSONArray dbs = (JSONArray)jsonObject.get("database");
        for (Object databaseObj: dbs)
        {
            DataBaseVO dataBaseVO = getDateBaseVO((JSONObject) databaseObj);
            dataBaseVOs.add(dataBaseVO);
        }

        return dataBaseVOs;
    }

	private DataBaseVO getDateBaseVO(JSONObject jsonObject)
    {
        JSONObject sqlObj = (JSONObject)jsonObject.get("sql");
        JSONObject mongoObj = (JSONObject)jsonObject.get("mongo");

        DataBaseVO dataBaseVO = new DataBaseVO();
        SqlVO sqlVO = new DataBaseVO.SqlVO();
        MongoVO mongoVO = new DataBaseVO.MongoVO();

        sqlVO.setDriver(sqlObj.getOrDefault("driver","com.mysql.jdbc.Driver").toString());
        sqlVO.setUrl(sqlObj.get("url").toString());
        sqlVO.setUser(sqlObj.get("user").toString());
        sqlVO.setPass(sqlObj.get("pass").toString());
        dataBaseVO.setSql(sqlVO);

        mongoVO.setConnectionsPerHost(Integer.parseInt(mongoObj.get("connectionsPerHost").toString()));
        mongoVO.setNumberOfWrites(Integer.parseInt(mongoObj.get("numberOfWrites").toString()));
        mongoVO.setDbName(mongoObj.get("dbName").toString());
        JSONArray serversArray = (JSONArray)mongoObj.get("servers");
        ArrayList<ServersVO> serversVOs = new ArrayList<ServersVO>();
        for(Object server: serversArray)
        {
            JSONObject object = (JSONObject)server;
            ServersVO serversVO = new ServersVO();
            serversVO.setIp(object.get("ip").toString());
            serversVO.setPort(Integer.parseInt(object.get("port").toString()));
            serversVOs.add(serversVO);
        }
        mongoVO.setServers(serversVOs);
        dataBaseVO.setMongo(mongoVO);
        return dataBaseVO;
    }
}