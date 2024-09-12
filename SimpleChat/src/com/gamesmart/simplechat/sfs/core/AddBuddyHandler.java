package com.gamesmart.simplechat.sfs.core;

import org.apache.log4j.Logger;

import com.smartfoxserver.v2.buddylist.SFSBuddy;
import com.smartfoxserver.v2.buddylist.SFSBuddyEventParam;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.entities.SFSUser;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;

/**
 * add one buddy to self buddy list 
 */
public class AddBuddyHandler extends BaseServerEventHandler {
	private Logger logger =  Logger.getLogger(AddBuddyHandler.class);

	@Override
	public void handleServerEvent(ISFSEvent event) throws SFSException {
		try{
			SFSUser user = (SFSUser)event.getParameter(SFSEventParam.USER);
			SFSBuddy buddy = (SFSBuddy)event.getParameter(SFSBuddyEventParam.BUDDY);
			logger.info(String.format("add buddy,owner:%s,buddy:%s", user.getName(),buddy.getName()));
			getParentExtension().getBuddyApi().addBuddy(user,buddy.getName(),false,true,true);
		}catch(Exception e){
			//when buddy list is full or buddy was already added
			logger.error("",e);
		}
	}

}
