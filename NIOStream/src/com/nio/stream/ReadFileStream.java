package com.nio.stream;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadFileStream {
	public static void main(String[] args) {
		try {
			RandomAccessFile file = new RandomAccessFile("config/test.txt", "rw");
			FileChannel channel = file.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			//read file
			buffer.clear();
			int bytesRead = channel.read(buffer);
			if(bytesRead == -1) {
				System.out.println(" - - - read over - - - -");
			}else {
				buffer.flip();
				StringBuffer lineBuffer = new StringBuffer();
				while(buffer.hasRemaining()) {
					char c = (char)buffer.get();
					lineBuffer.append(c);
					if("\r".equals(String.valueOf(c)) || System.lineSeparator().equals(String.valueOf(c))) {
						System.out.print(lineBuffer.toString());
						lineBuffer = new StringBuffer();
					}
				}
				System.out.println(lineBuffer.toString());
			}
			
			buffer.clear();
			channel.close();
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
