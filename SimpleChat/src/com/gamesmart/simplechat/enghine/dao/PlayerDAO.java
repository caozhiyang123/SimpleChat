package com.gamesmart.simplechat.enghine.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.gamesmart.simplechat.enghine.core.Application;
import com.gamesmart.simplechat.enghine.core.DBConnector;
import com.gamesmart.simplechat.enghine.util.ChatUtil;
import com.gamesmart.simplechat.enghine.vo.PlayerVO;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

@SuppressWarnings("all")
public class PlayerDAO extends DBbaseDAO{
	private Logger logger = Logger.getLogger(PlayerDAO.class);
	
	private final static  String TABLE_NAME = "users";
	private final static  String _ID = "_id";
	private final static  String PLAYER_ID = "id";
	private static final String FIRST_NAME = "first_name";
	private final static  String FACEBOOK_ID = "facebook_id";
	private final static  String GOOGLE_ID = "google_id";
	private final static  String PLATFORM_SIGNIN = "platform_signin";
	private final static  String NETWORK_SIGNIN = "network_signin";
	private final static  String LEVEL = "level";
	private final static  String ACTUAL_LEVEL = "actual_level";
	private final static  String XP = "xp";
	private final static  String COINS = "coins";
	private final static  String SAVINGS = "savings";//Mongo
	private final static  String SAVING = "saving";//MySql
	private final static  String FREE_SPINS = "free_spins";
	private final static  String DOUBLE_XP_EXPIRED_TIME = "double_xp_expired_time";
	private final static String LOYALTY_LEVEL = "loyalty_level";
	private final static String HARD_CURRENCY = "chips";
	private final static String TIME_CREATED = "time_created";
	private final static String PIC = "pic";
	private final static String IS_PURCHASED_WITH_PUZZLE = "is_purchased_with_puzzle ";
	private final static String CLUB_ID = "club_id";
	
	public PlayerVO findPlayerVO(long userId) {
		return findFromMongo(userId);
	}
	
	private PlayerVO findFromMongo(long userId){
    	PlayerVO playerVO = new PlayerVO(userId,ChatUtil.getName());
		DB db = null;
        try
        {
            db = DBConnector.getInstance().getMongoDB();
            DBCollection dbCollection = db.getCollection(TABLE_NAME);
            BasicDBObject playerObject = new BasicDBObject(PLAYER_ID, String.valueOf(userId));
			BasicDBObject filterObj = new BasicDBObject();
			filterObj.append(_ID,0);
			filterObj.append(COINS,1);
			filterObj.append(FACEBOOK_ID,1);
			filterObj.append(GOOGLE_ID,1);
			filterObj.append(PLATFORM_SIGNIN,1);
			filterObj.append(NETWORK_SIGNIN,1);
			filterObj.append(ACTUAL_LEVEL,1);
			filterObj.append(LEVEL,1);
			filterObj.append(XP,1);
			filterObj.append("accumulated_today_xp",1);
			filterObj.append(SAVINGS,1);
			filterObj.append(FREE_SPINS,1);
			filterObj.append(LOYALTY_LEVEL,1);
			filterObj.append(DOUBLE_XP_EXPIRED_TIME,1);
			filterObj.append(HARD_CURRENCY,1);
			filterObj.append(PIC,1);
			filterObj.append(IS_PURCHASED_WITH_PUZZLE,1);
			filterObj.append(CLUB_ID,1);
			DBObject playerObj = dbCollection.findOne(playerObject,filterObj);
            if (playerObj == null)
            {
                logger.error("Missing User In Mongo,playerID:"+userId);
                return null;
            }

            playerVO.setBalance(getLong(playerObj, COINS));
            playerVO.setFacebookID(getLong(playerObj, FACEBOOK_ID));
            playerVO.setGoogleID(getString(playerObj, GOOGLE_ID));
            playerVO.setPlatFormSignIn(getString(playerObj, PLATFORM_SIGNIN));
            playerVO.setNetWorkSignin(getString(playerObj,NETWORK_SIGNIN));
            int level = getInt(playerObj, ACTUAL_LEVEL);
            if (level == 0)
			{
				level = getInt(playerObj, LEVEL);
			}
            playerVO.setLevel(level);
            playerVO.setXp(getLong(playerObj, XP));
            playerVO.setXpToday(getLong(playerObj, "accumulated_today_xp"));
            playerVO.setSavings(getDouble(playerObj, SAVINGS));
            playerVO.setFreeSpins(getInt(playerObj, FREE_SPINS));
			playerVO.setLoyaltyLevel(getInt(playerObj, LOYALTY_LEVEL));
            playerVO.setHardCurrency(getLong(playerObj, HARD_CURRENCY));
            playerVO.setPic(getString(playerObj,PIC));
            playerVO.setIsPurchasedWithPuzzle(getBoolean(playerObj,IS_PURCHASED_WITH_PUZZLE));
			playerVO.setClubId(getInt(playerObj,CLUB_ID));
			return playerVO;
        }
        catch (Exception e)
        {
            logger.error("findFromMongo, playerID: " + userId, e);
        }finally {
        	DBConnector.getInstance().release(db);
		}
        return null;
    }
	
	public String getFirstName(long playerID)
	{
		Connection conn = null;
		PreparedStatement pstQuery = null;
		ResultSet rst = null;
		try
		{
			conn = DBConnector.getInstance().getConnection();
			String sql = String.format("select %s from %s where %s = ?",FIRST_NAME,TABLE_NAME,PLAYER_ID);
			pstQuery = conn.prepareStatement(sql);
			pstQuery.setLong(1, Long.valueOf(playerID));
			rst = pstQuery.executeQuery();
			if (rst != null)
			{
				while (rst.next())
				{
					return rst.getString(FIRST_NAME);
				}
			}
		} catch (SQLException e)
		{
			logger.error("", e);
		}finally{
			DBConnector.getInstance().release(conn, pstQuery, rst);
		}
		return null;
	}
}
