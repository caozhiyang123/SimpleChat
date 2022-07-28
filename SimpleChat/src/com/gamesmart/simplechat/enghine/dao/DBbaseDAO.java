package com.gamesmart.simplechat.enghine.dao;

import org.apache.log4j.Logger;

import com.mongodb.DBObject;

public class DBbaseDAO {
	private static Logger logger = Logger.getLogger(DBbaseDAO.class);
	
	public static long getLong(DBObject dbObject, String key) throws Exception
    {
        long result = 0;
        Object value = dbObject.get(key);
        if (value instanceof Integer)
        {
            result = (int)value;
        }
        else if(value instanceof Long)
        {
            result = (long)value;
        }
        else if (value instanceof Double)
        {
            result = (long)((double)value);
        }
        else if (value instanceof String)
        {
            result = Long.parseLong(value.toString());
        }
        else if (value == null)
        {
//            logger.warn("key: " + key + " value is null!");
        }
        else
        {
            logger.error("key: " + key + ", value is: " + value);
            throw new Exception("can't find proper type!");
        }

        return result;
    }

    public static int getInt(DBObject dbObject, String key) throws Exception
    {
        int result = 0;
        Object value = dbObject.get(key);
        if (value instanceof Integer)
        {
            result = (int)value;
        }
        else if(value instanceof Long)
        {
            result = Math.toIntExact((long)value);
        }
        else if (value instanceof Double)
        {
            result = (int)((double)value);
        }
        else if (value instanceof String)
        {
            result = Integer.parseInt(value.toString());
        }
        else if (value == null)
        {
//            logger.warn("key: " + key + " value is null!");
        }
        else
        {
            logger.error("key: " + key + ", value is: " + value);
            throw new Exception("can't find proper type!");
        }

        return result;
    }

    public static double getDouble(DBObject dbObject, String key) throws Exception
    {
        double result = 0;
        Object value = dbObject.get(key);
        if (value instanceof Integer)
        {
            result = (int)value;
        }
        else if(value instanceof Long)
        {
            result = (long)value;
        }
        else if (value instanceof Double)
        {
            result = (double)value;
        }
        else if (value instanceof String)
        {
            result = Double.parseDouble(value.toString());
        }
        else if (value == null)
        {
//            logger.warn("key: " + key + " value is null!");
        }
        else
        {
            logger.error("key: " + key + ", value is: " + value);
            throw new Exception("can't find proper type!");
        }

        return result;
    }

    public static String getString(DBObject dbObject, String key)
    {
        String result = "";
        Object value = dbObject.get(key);
        if(value != null)
        {
            result = value.toString();
        }
        return result;
    }

    public static boolean getBoolean(DBObject dbObject, String key){
        boolean result = false;
        Object value = dbObject.get(key);
        if (value != null){
            result = Boolean.valueOf(value.toString());
        }
        return result;
    }
}
