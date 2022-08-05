package com.gamesmart.simplechat.enghine.core;

import com.gamesmart.simplechat.enghine.vo.DataBaseVO;
import com.gamesmart.simplechat.enghine.vo.DataBaseVO.MongoVO.ServersVO;
import com.gamesmart.simplechat.enghine.vo.DataBaseVO.SqlVO;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBConnector
{
    private final Logger logger = Logger.getLogger(DBConnector.class);
    private DataBaseVO dataBaseVO;
    private BasicDataSource dataSource;
    private Mongo mongo;


    public DBConnector(DataBaseVO dataBaseVO)
    {
        this.dataBaseVO = dataBaseVO;
        dataSource = JdbcUtilsDBCP.getDataSource(dataBaseVO.getSql());
        mongo = MongoUtil.getMongo(dataBaseVO.getMongo());
    }

    public static DBConnector getInstance()
    {
        return App.getInstance().getDBController().getDefaultConnector();
    }

    public Connection getConnection()
    {
        try
        {
            return dataSource.getConnection();
        }
        catch (SQLException e)
        {
            logger.error("getConnection", e);
        }
        return null;
    }

    public void release(Connection conn, PreparedStatement ps, ResultSet rs)
    {
        JdbcUtilsDBCP.release(conn, ps, rs);
    }

    public void release(DB db){
        MongoUtil.release(db);
    }

    public DB getMongoDB()
    {
        return mongo.getDB(dataBaseVO.getMongo().getDbName());
    }

    //for test overwrite db
    public void setDataSource(BasicDataSource basicDataSource)
    {
        this.dataSource = basicDataSource;
    }

    static class JdbcUtilsDBCP
    {
        private static Logger logger = Logger.getLogger(JdbcUtilsDBCP.class);
        /**
         * In Java, the database connection pool is written to implement java.sql.DataSource interface.
         * Every database connection pool is implemented by DataSource interface.
         * The DBCP connection pool is a concrete implementation of the java.sql.DataSource interface.
         */
        private static String driver = "com.mysql.jdbc.Driver";
        private static int initSize = 100;//initialSize connection num
        private static int maxActive = 1000;//max connection num
        private static int maxIdle = 20;//Maximum idle connection
        private static int minIdle = 5;//Minimum idle connection
        private static int maxWait = 60000;//timeout waiting time in milliseconds of 6000 milliseconds /1000 equal to 60 seconds

        public static BasicDataSource getDataSource(SqlVO sqlVO)
        {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(sqlVO.getDriver());
            dataSource.setUrl(sqlVO.getUrl());
            dataSource.setUsername(sqlVO.getUser());
            dataSource.setPassword(sqlVO.getPass());
            dataSource.setInitialSize(initSize);
            dataSource.setMaxActive(maxActive);
            dataSource.setMaxIdle(maxIdle);
            dataSource.setMinIdle(minIdle);
            dataSource.setMaxWait(maxWait);
            //for test fix time_out bug
            dataSource.setTestWhileIdle(true);
            dataSource.setTimeBetweenEvictionRunsMillis(3600000);
            dataSource.setValidationQuery("select 1");
            dataSource.setNumTestsPerEvictionRun(maxActive);
            dataSource.setValidationQueryTimeout(60000);

            return dataSource;
        }

        /**
         * @param conn
         * @param ps
         * @param rs
         * @Method: release
         * @Description: release data source��
         * The resources released include the Connection database connection object,
         * the Statement object responsible for executing the SQL command,
         * and the ResultSet object that stores the query results.
         * @Anthor:michael
         */
        public static void release(Connection conn, PreparedStatement ps, ResultSet rs)
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (Exception e)
                {
                    logger.error("", e);
                }finally {
                    rs = null;
                }
            }
            if (ps != null)
            {
                try
                {
                    ps.close();
                }
                catch (Exception e)
                {
                    logger.error("", e);
                }finally {
                    ps = null;
                }
            }

            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (Exception e)
                {
                    logger.error("", e);
                }finally {
                    conn = null;
                }
            }
        }

    }

    static class MongoUtil
    {
        private static Logger logger = Logger.getLogger(MongoUtil.class);

        public static Mongo getMongo(DataBaseVO.MongoVO mongoVO)
        {
            try
            {
                int connectionsPerHost = mongoVO.getConnectionsPerHost();
//                int numberOfWrites = dataBaseVO.getMongo().getNumberOfWrites();
                List<ServersVO> serversVOs = mongoVO.getServers();
                List<ServerAddress> serverAddresss = new ArrayList<ServerAddress>();

                for (ServersVO serversVO: serversVOs)
                {
                    ServerAddress serverAddress = new ServerAddress(serversVO.getIp(), serversVO.getPort());
                    serverAddresss.add(serverAddress);
                }
                Mongo mongoClient = new Mongo(serverAddresss);
                mongoClient.getMongoOptions().setConnectionsPerHost(connectionsPerHost);
//                mongoClient.getMongoOptions().setW(numberOfWrites);
                return mongoClient;
            }
            catch (UnknownHostException e)
            {
                logger.error("mongo exception ", e);
            }

            return null;
        }

        public static void release(DB db) {
            if (db !=null){
                try {
                    db.requestDone();
                } catch (Exception e) {
                    logger.error("",e);
                } finally {
                    db = null;
                }
            }
        }
    }

}
