package com.gamesmart.simplechat.enghine.util;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameUtil {
    private static Logger logger = Logger.getLogger(GameUtil.class);
    private static int maxRoomSeed=100000;
    private static List<Long> sessionIdList= Collections.synchronizedList(new ArrayList());

    public static Long createSessionId(){
        try {
            //clear the list
            if(sessionIdList.size()>=maxRoomSeed){
                sessionIdList.clear();
            }
            String time=String.valueOf(System.currentTimeMillis());
            String lastEightTime=time.substring(time.length()-8);
            long randomNumber=Math.round(RandomLocalThread.get().nextDouble() * 10000000);

            String sessionId=lastEightTime+randomNumber;
            Long value = Long.valueOf(sessionId);
            while(sessionIdList.contains(value)){
                createSessionId();
            }
            sessionIdList.add(value);
            return value;
        } catch (NumberFormatException e) {
            logger.error("",e);
        }
        return 0L;
    }
}
