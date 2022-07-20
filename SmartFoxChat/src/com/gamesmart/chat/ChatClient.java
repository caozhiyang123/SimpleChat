package com.gamesmart.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.gamesmart.chat.vo.MessageVO;

public class ChatClient{
	private static int userId;
	public static int getUserId() {return userId;}
	private static ConcurrentLinkedQueue<MessageVO> messageQueue = new ConcurrentLinkedQueue<MessageVO>();
	
	public static void init() {
		userId = (int)(new Random().nextDouble()*10000);
	}
	
	static {
		new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(() -> keepAlive(), 1, 1000, TimeUnit.MILLISECONDS);
		//Request queue
		new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(new SendMsgQueue(10), 1, 1, TimeUnit.SECONDS);
	}
	
	public static void addMessageVO(MessageVO messageVO) {
		messageQueue.add(messageVO);
	}
	
	public static class SendMsgQueue implements Runnable{

		private int maxInsertRows = 10;
		
		public SendMsgQueue(int maxInsertRows) {
			this.maxInsertRows = maxInsertRows;
		}

		@Override
		public void run() {
			List<MessageVO> msgList = new ArrayList<MessageVO>();
			MessageVO messageVO = messageQueue.poll();
			while(messageVO != null) {
				msgList.add(messageVO);
				messageVO = messageQueue.poll();
				if(msgList.size() >= maxInsertRows) {
					sendMsg(msgList);
					msgList.clear();
				}
			}
			sendMsg(msgList);
		}
	}

	private static void sendMsg(List<MessageVO> msgList) {
		for (MessageVO messageVO : msgList) {
			sendMsg(messageVO.getMsg());
//			System.out.println(" = = = send msg : "+messageVO.getMsg());
		}
	}
	
	private static String lastMsg;
	public static void sendMsg(String msg) {
		Socket socket = null;
		DataOutputStream out = null;
		DataInputStream in = null;
		try {
			synchronized("send Msg") {
				socket = new Socket("3.220.82.17",1991);
//				socket = new Socket("127.0.0.1",1991);
				socket.setKeepAlive(true);
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
	//					System.out.println("client received:"+msgFrom);
						HomePage.resetTextArea(msgFrom);
						lastMsg = msgFrom;
					}
				}
				boolean res = "exit".equals(msgFrom)?true:false;
				if(!res) {
//					System.out.println("send msg:"+msg+",result:"+res);
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

	/*
	 * 5430_msg_[active]_msg
	 */
	private static void keepAlive() {
		String msg = userId+"_msg_[active]_msg";
//		addMessageVO(new MessageVO(userId,msg,true));
		sendMsg(msg);
	}
}
