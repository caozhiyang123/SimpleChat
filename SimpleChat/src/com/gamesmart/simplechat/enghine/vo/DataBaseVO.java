package com.gamesmart.simplechat.enghine.vo;

import java.util.List;

public class DataBaseVO
{
    private SqlVO sql;
    private MongoVO mongo;

    public SqlVO getSql()
    {
        return sql;
    }

    public void setSql(SqlVO sql)
    {
        this.sql = sql;
    }

    public MongoVO getMongo()
    {
        return mongo;
    }

    public void setMongo(MongoVO mongo)
    {
        this.mongo = mongo;
    }

    public static class SqlVO
        {
            private String driver;
            private String url;
            private String user;
            private String pass;

            public String getDriver() {return driver;}
            public void setDriver(String driver) {this.driver = driver;}

            public String getUrl()
            {
                return url;
            }

            public void setUrl(String url)
            {
                this.url = url;
            }

            public String getUser()
            {
                return user;
            }

            public void setUser(String user)
            {
                this.user = user;
            }

            public String getPass()
            {
                return pass;
            }

            public void setPass(String pass)
            {
                this.pass = pass;
            }

        }

        public static class MongoVO
        {
            private String dbName;
            private int numberOfWrites;
            private int connectionsPerHost;
            private List<ServersVO> servers;

            public int getNumberOfWrites()
            {
                return numberOfWrites;
            }

            public void setNumberOfWrites(int numberOfWrites)
            {
                this.numberOfWrites = numberOfWrites;
            }

            public int getConnectionsPerHost()
            {
                return connectionsPerHost;
            }

            public void setConnectionsPerHost(int connectionsPerHost)
            {
                this.connectionsPerHost = connectionsPerHost;
            }

            public List<ServersVO> getServers()
            {
                return servers;
            }

            public void setServers(List<ServersVO> servers)
            {
                this.servers = servers;
            }

            public String getDbName()
            {
                return dbName;
            }

            public void setDbName(String dbName)
            {
                this.dbName = dbName;
            }

            public static class ServersVO
            {
                private String ip;
                private int port;


                public String getIp()
                {
                    return ip;
                }

                public void setIp(String ip)
                {
                    this.ip = ip;
                }

                public int getPort()
                {
                    return port;
                }

                public void setPort(int port)
                {
                    this.port = port;
                }

            }
        }
}
