package com.nio.stream;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WriteFileStream {
	private static List<ByteBuffer> buffers = new ArrayList<ByteBuffer>();
	
	public static void main(String[] args) {
		try {
			RandomAccessFile file = new RandomAccessFile("config/test.txt", "rw");
			FileChannel channel = file.getChannel();
			//ByteBuffer buffer = ByteBuffer.allocate(1024*100);
			
			new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(new NIOWrite(channel), 0, 5, TimeUnit.SECONDS);
			
			//buffer.clear();
			for(int i=0;i<100;i++) {
				int p = i;
				new Thread(()->putBuffer(p)).start();
			}
			
			
			//channel.close();
			//file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void putBuffer(int i) {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		buffer.put(System.lineSeparator().getBytes());
		buffer.put((i+"- - - - hello,i'm Michael.").getBytes());
		buffer.flip();
		buffers.add(buffer);
	}

	private static class NIOWrite extends Thread{
		private FileChannel channel;
		
		public NIOWrite( FileChannel channel) {
			this.channel = channel;
		}

		public void run(){
			ByteBuffer mergedBuffer = ByteBuffer.allocate(1024 * 100);  
			for(int i =0;i<buffers.size();i++) {
				ByteBuffer buffer = buffers.remove(i);
				i--;
				while (buffer.hasRemaining()) {  
					mergedBuffer.put(buffer.get()); // 将数据合并到mergedBuffer中  
				}  
			} 
				
			try {
				mergedBuffer.flip();
				channel.position(channel.size());
				while(mergedBuffer.hasRemaining()) {
					channel.write(mergedBuffer);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
