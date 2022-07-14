package com.gamesmart.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ChatClient{
	private static int userId;
	public static int getUserId() {return userId;}
	
	public static void init() {
		userId = (int)(new Random().nextDouble()*10000);
	}
	
	static {
		new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(() -> keepAlive(), 3, 5, TimeUnit.SECONDS);
	}
	
	private static String lastMsg;
	public static void sendMsg(String msg) {
		Socket socket = null;
		DataOutputStream out = null;
		DataInputStream in = null;
		try {
			socket = new Socket("3.220.82.17",1991);
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			if(!socket.isOutputShutdown()) {
				out.writeUTF(msg);
				out.writeUTF("exit");
			}
			//receive msg from server
			TimeUnit.SECONDS.sleep(1);
			String msgFrom = null;
			while(in.available()>0 && !"exit".equals(msgFrom)) {
				msgFrom = in.readUTF();
				if(!msgFrom.isEmpty() && !"exit".equals(msgFrom) && !msgFrom.contains(String.valueOf(userId)) 
						&& !msgFrom.contains("active") && !msgFrom.equals(lastMsg)) {
					System.out.println("client received:"+msgFrom);
					HomePage.resetTextArea(msgFrom);
					lastMsg = msgFrom;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	private static void keepAlive() {
		sendMsg("userId_"+userId+"_[active]");
	}
}
