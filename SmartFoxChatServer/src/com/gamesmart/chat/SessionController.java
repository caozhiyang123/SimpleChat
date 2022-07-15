package com.gamesmart.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SessionController {
	private static ServerSocket server = null;
	private static Socket accept = null;
	private static DataInputStream in = null;
	private static DataOutputStream out = null;
	
	private static String msg;
	
	public static void main(String[] args) {
		try {
			server = new ServerSocket(1991);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new ScheduledThreadPoolExecutor(1) .scheduleAtFixedRate(()-> start1(), 1, 100,TimeUnit.MILLISECONDS);
	}

	public static void start1() {
		try {
			accept = server.accept();
			in = new DataInputStream(accept.getInputStream());
			out = new DataOutputStream(accept.getOutputStream());
			
			StringBuffer sendMsg = new StringBuffer();
			if(msg != null) {
				sendMsg.append("from[");
				sendMsg.append(msg);
				sendMsg.append("]from,");
			}
//			System.out.println("from msg:"+sendMsg.toString());
			String sourceMsg = null;
			while(in.available()>0 && !"exit".equals(sourceMsg)) {
				sourceMsg = in.readUTF();
//				System.out.println("server received:"+sourceMsg);
				if(!sourceMsg.contains("active") && !"exit".equals(sourceMsg)) {
					msg = sourceMsg;
				}if(!"exit".equals(sourceMsg)){
					out.writeUTF(sendMsg.toString());
				}else if("exit".equals(sourceMsg)){
					out.writeUTF(sourceMsg);
				}
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
}
