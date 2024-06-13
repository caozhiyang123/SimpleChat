package com.gamesmart.simplechat.sfs;

import org.apache.log4j.Logger;

import com.gamesmart.simplechat.sfs.core.UserJoinRoomHandler;
import com.gamesmart.simplechat.sfs.core.UserLogoutHandler;
import com.smartfoxserver.v2.annotations.Instantiation;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import com.smartfoxserver.v2.extensions.SFSExtension;

@Instantiation(Instantiation.InstantiationMode.SINGLE_INSTANCE)
public class RegisterRoomExtension extends SFSExtension{
	private Logger logger = Logger.getLogger(RegisterRoomExtension.class);
	
	@Override
	public void init() {
		addEventHandler(SFSEventType.USER_JOIN_ROOM, UserJoinRoomHandler.class);
		addEventHandler(SFSEventType.USER_DISCONNECT, UserLogoutHandler.class);
        addEventHandler(SFSEventType.USER_LEAVE_ROOM, UserLogoutHandler.class);
        addEventHandler(SFSEventType.SERVER_READY, new BaseServerEventHandler()
        {
            @Override
            public void handleServerEvent(ISFSEvent event) throws SFSException
            {
                initRoom();
            }
        });
	}
	
	private void initRoom() {
		try {
			//TODO
		}catch (Exception e){
            logger.error("init room error :",e);
        }
	}
}