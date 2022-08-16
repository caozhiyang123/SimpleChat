package com.gamesmart.chat.page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.AudioFormat;
import javax.media.format.RGBFormat;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

class VideoPage extends JFrame implements Runnable{
	private static Player player = null;
	private static Player player2 = null;
	private CaptureDeviceInfo device = null;
	private CaptureDeviceInfo device2 = null;
	private MediaLocator locator = null;
	private Image p_w_picpath;
	private Buffer buffer = null;
	private BufferToImage b2i = null;
	String usbCamera = "vfw:logitech usb video camera:0";
	String defaultCamera = "vfw:Microsoft WDM Image Capture (Win32):0";
	Component comV,comVC,comA;
	JPanel p1,p2,p3,p4;
	JLabel label=new JLabel("Friend's ＩＰ:");
	JLabel label2=new JLabel(new ImageIcon("/config/upload.png"));
	Thread thread1,thread2;
	VideoPage(){
		super("VIDEO IO");
		setBounds(150,100,500,500);
		p1=new JPanel(new GridLayout(1,2));
		p2=new JPanel(new GridLayout(2,1));
		p3=new JPanel(new BorderLayout());
		p4=new JPanel(new BorderLayout());
		p1.add(new TxtChat());
		p1.add(p2);
		p2.add(p3);
		p2.add(p4);
		p3.add("North",new JLabel("VIDEO IO"));
		p3.add(label2,"Center");
		add(label,"North");
		add(p1,"Center");
		try{
			jbInit();
			speaker();
		}catch(Exception e){
			e.printStackTrace();
		}
		thread1=new Thread(this);
		thread2=new Thread(this);
		thread1.start();
		thread2.start();
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					System.exit(0);
				}});
			setVisible(true);
			validate();
		}
		private void jbInit() throws Exception{
			//TODO
			Vector deviceList = CaptureDeviceManager.getDeviceList(new RGBFormat());
			device = CaptureDeviceManager.getDevice(defaultCamera);
			locator = device.getLocator();
			try
			{
				Manager.setHint(Manager.LIGHTWEIGHT_RENDERER,new Boolean(true));
				player = Manager.createRealizedPlayer(locator);
				player.start();
				if((comV = player.getVisualComponent()) != null)
				{
					p4.add(comV,"Center");
				}
				if((comVC=player.getControlPanelComponent())!=null){
					p4.add(comVC,"South");
				}
			}catch (Exception e){
			 e.printStackTrace();
		    }
			setBounds(200,100,600, 550);
			setVisible(true);
			int new_w= p4.getWidth();
			int new_h= p4.getHeight();
			MediaTracker mt = new MediaTracker(this.p4);
			try
			{
				mt.addImage(p_w_picpath, 0);
				mt.waitForID(0);
			}catch (Exception e)
			{
			e.printStackTrace();
			}
			BufferedImage buffImg = new BufferedImage(new_w, new_h,BufferedImage.TYPE_INT_RGB);
			Graphics g = buffImg.createGraphics();
			g.drawImage(p_w_picpath, 0, 0, new_w, new_h, this.p4);
			g.dispose();
		}
		private void speaker() throws Exception{
			Vector deviceList = CaptureDeviceManager.getDeviceList(new AudioFormat(AudioFormat.LINEAR,44100,16,2));
			if(deviceList.size()>0)
			{
				device2 = (CaptureDeviceInfo)deviceList.firstElement();
			}
			else
			{
				System.out.println("找不到音频设备！");
			}
			try
			{
				player2 = Manager.createRealizedPlayer(device2.getLocator());
				player2.start();
				if((comA = player2.getControlPanelComponent())!=null){
					p3.add(comA,"South");
				}
			}catch(Exception e){e.printStackTrace();}
		}
		public void run(){
			DatagramPacket pack=null;
			DatagramSocket maildata=null;
			byte data[]=new byte[320*240];
			try
			{
				pack=new DatagramPacket(data,data.length);
				maildata=new DatagramSocket(999);
			}catch(Exception e){
				System.out.println(e.toString());
			}
			while(true){
				if(Thread.currentThread()==thread1){
					if(maildata==null){
						break;
					}else{
						try
						{
							maildata.receive(pack);
							ByteArrayInputStream input=new ByteArrayInputStream(data);
							Image message=ImageIO.read(input);
							label2.setIcon(new ImageIcon(message));
							label.setText("对方ＩＰ："+pack.getAddress()+
									" 端口："+pack.getPort());
						}catch(Exception e){
							System.out.println("接收图像数据失败！");
						}
					}
				}else if(Thread.currentThread()==thread2){
					try{
						FrameGrabbingControl fgc = (FrameGrabbingControl) player.getControl("javax.media.control.FrameGrabbingControl");
						javax.media.Buffer grabFrame = fgc.grabFrame();
						buffer = fgc.grabFrame();
						b2i = new BufferToImage( (VideoFormat) buffer.getFormat());
						p_w_picpath = b2i.createImage(buffer);
						BufferedImage bi = (BufferedImage) createImage(p_w_picpath.getWidth(null),
						p_w_picpath.getHeight(null));
						Graphics2D g2 = bi.createGraphics();
						g2.drawImage(p_w_picpath, null, null);
						ByteArrayOutputStream output=new ByteArrayOutputStream();
						JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder( output );
						JPEGEncodeParam jpeg = encoder.getDefaultJPEGEncodeParam(bi);
						jpeg.setQuality(0.5f,false);
						encoder.setJPEGEncodeParam(jpeg);
						encoder.encode(bi);
						output.close();
						InetAddress address=InetAddress.getByName("localhost");
						DatagramPacket datapack1=new DatagramPacket(output.toByteArray(),output.size(),address,555);
						DatagramSocket maildata1=new DatagramSocket();
						maildata1.send(datapack1);
						Thread.sleep(400);
					}catch(Exception e){
						System.out.println("视频发送失败！");
					}
			}
		}
	}
	public static void main(String args[]){new VideoPage();}
}

class TxtChat extends JPanel implements ActionListener,Runnable{
		JPanel p1,p2,p3,p4;
		JLabel jpic;
		JTextArea txt1,txt2;
		JButton btn1,btn2;
		JScrollPane scroll,scroll2;
		Thread thread;
		boolean boo1=false,boo2=false;
			TxtChat(){
				setLayout(new BorderLayout());
				txt1=new JTextArea();
				txt2=new JTextArea();
				p1=new JPanel(new GridLayout(2,1));
				p2=new JPanel();
				p3=new JPanel(new BorderLayout());
				jpic=new JLabel(new ImageIcon("/config/upload.png"));
				btn1=new JButton("电子文档");
				btn2=new JButton("发送信息");
				scroll=new JScrollPane(txt1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scroll2=new JScrollPane(txt2,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				p1.add(scroll);
				p1.add(p3);
				p2.add(btn1);
				p2.add(btn2);
				p3.add(jpic,"North");
				p3.add(scroll2,"Center");
				add(p1,"Center");
				add(p2,"South");
				setVisible(true);
				Font f=new Font("",Font.PLAIN,18);
				txt1.setFont(f);
				txt1.setForeground(Color.red);
				txt2.setFont(f);
				txt2.setForeground(Color.blue);
				btn1.setBackground(Color.cyan);
				btn2.setBackground(Color.yellow);
				btn1.addActionListener(this);
				btn2.addActionListener(this);
				thread=new Thread(this);
				thread.start();
			}
	
			public void actionPerformed(ActionEvent e)
			{
				if(e.getSource()==btn2)
				{
					byte buffer[]=txt2.getText().trim().getBytes();
					try{
						InetAddress address=InetAddress.getByName("localhost");
						DatagramPacket data_pack=new DatagramPacket(buffer,buffer.length,address,888);
						DatagramSocket mail_data=new DatagramSocket();
						txt1.append("我说："+txt2.getText()+'\n');
						mail_data.send(data_pack);
						txt2.setText("");
					}catch(Exception e1){
						System.out.println("聊天信息发送失败！");
					}
				}
			}
			public void run(){
				DatagramPacket pack=null;
				DatagramSocket mail_data=null;
				byte data[]=new byte[8192];
				try
				{
					pack=new DatagramPacket(data,data.length);
					mail_data=new DatagramSocket(666);
				}
				catch(Exception e){ 
					e.printStackTrace();
				}
				while(true)
				{
					if(Thread.currentThread()==thread){
						if(mail_data==null){
							break;
						}else {
							try
							{
								mail_data.receive(pack);
								int length=pack.getLength();
								String message=new String(pack.getData(),0,length);
								txt1.append("某某说："+message+'\n');
							}catch(Exception e){
								System.out.println("接收数据失败！");
							}
						}
					}
				}
			}
}