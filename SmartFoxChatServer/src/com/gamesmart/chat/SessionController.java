package com.gamesmart.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.gamesmart.chat.vo.MessageVO;

public class SessionController {
	private static ServerSocket server = null;
	private static Socket accept = null;
	private static DataInputStream in = null;
	private static DataOutputStream out = null;
	
	private static Map<Long,List<MessageVO>> msg;
	
	public static void main(String[] args) {		
		try {
			msg = new ConcurrentHashMap<>();
			server = new ServerSocket(1991);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new ScheduledThreadPoolExecutor(1) .scheduleAtFixedRate(()-> start(), 1, 50,TimeUnit.MILLISECONDS);
	}

	public static void start() {
		try {
			accept = server.accept();
			in = new DataInputStream(accept.getInputStream());
			out = new DataOutputStream(accept.getOutputStream());
			
//			System.out.println("from msg:"+sendMsg.toString());
			String sourceMsg = null;
			while(in.available()>0 && !"exit".equals(sourceMsg)) {
				sourceMsg = in.readUTF();
//				System.out.println("server received:"+sourceMsg);
				long sourceUserId = 0L;
				if(!sourceMsg.contains("active") && !"exit".equals(sourceMsg)) {
					sourceUserId = Long.valueOf(sourceMsg.substring(0, sourceMsg.indexOf("_msg_")));
					String msgFrom = sourceMsg;
					long userIdFrom = sourceUserId;
					setMsg(msgFrom, userIdFrom);
				}
				
				if("exit".equals(sourceMsg)){
					out.writeUTF(sourceMsg);
				}else {
					sourceUserId = Long.valueOf(sourceMsg.substring(0, sourceMsg.indexOf("_msg_")));
					List<MessageVO> msgByUser = getMsg(sourceUserId);
					for (MessageVO messageVO : msgByUser) {
						if(!messageVO.isSent()) {
							out.writeUTF(messageVO.getMsg());
							messageVO.setIsSent(true);
							break;
						}
					}
				}
			}
			
			if("exit".equals(sourceMsg)) {
				out.writeUTF(sourceMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			/*
			 * try { out.close(); } catch (Exception e) { e.printStackTrace(); } try {
			 * in.close(); } catch (Exception e) { e.printStackTrace(); } try {
			 * accept.close(); } catch (Exception e) { e.printStackTrace(); } try {
			 * server.close(); } catch (IOException e) { e.printStackTrace(); }
			 */
		}
	}

	private static void setMsg(String msgFrom, long userIdFrom) {
		List<MessageVO> msgByUser = getMsg(userIdFrom);
		for (Map.Entry<Long, List<MessageVO>> map: msg.entrySet()) {
			Long key = map.getKey();
			//append self msg
			//append to friends' msg
			map.getValue().add(new MessageVO(userIdFrom,msgFrom,userIdFrom == (long)key));
		}
	}
	
	private static List<MessageVO> getMsg(Long userId) {
		List<MessageVO> msgByUser = msg.getOrDefault(userId, Collections.synchronizedList(new ArrayList<>()));
		msg.put(userId, msgByUser);
		return msgByUser;
	}
}
