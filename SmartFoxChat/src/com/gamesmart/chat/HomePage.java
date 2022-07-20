package com.gamesmart.chat;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class HomePage extends JFrame{
	private static JTextPane chatArea;
	private static JTextArea chatInputArea;
	private static JButton sendMsgButton;
	
	private static JScrollPane subPanelb_a_1_scroll;
	
	public HomePage() {
		createHomePage();
	}
	
	public static void main(String[] args) {
		new HomePage();
		ChatClient.init();
	}

	private void createHomePage() {
		this.setTitle("GameSmartChat");
		this.setPreferredSize(new Dimension(950, 632));
		this.setSize(new Dimension(400, 400));
		this.setLocationRelativeTo(null);
		this.getContentPane().add(createChatPanel());
		this.pack();
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowevent)
            {
                System.exit(0);
            }
        });
	}

	private static Component createChatPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));
		
		JPanel subPanela = new JPanel();
		subPanela.setLayout(new GridLayout(2,1));
		subPanela.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
		subPanela.setBackground(Color.GRAY);
		
		JPanel subPanela_a_1 = new JPanel();
		subPanela_a_1.setLayout(new GridLayout(1,2));
		subPanela_a_1.setBackground(Color.YELLOW);
		subPanela.add(subPanela_a_1);
		
		JPanel subPanela_a = new JPanel();
		subPanela_a.setBorder(BorderFactory.createTitledBorder("groups"));
		subPanela_a_1.add(subPanela_a);
		
		JPanel subPanela_b = new JPanel();
		subPanela_b.setBorder(BorderFactory.createTitledBorder("friends"));
		subPanela_a_1.add(subPanela_b);
		
		JPanel subPanela_a_2 = new JPanel();
		subPanela_a_2.setLayout(new GridLayout(1,3));
		subPanela_a_2.setBackground(Color.YELLOW);
		subPanela.add(subPanela_a_2);
		
		JPanel subPanela_c = new JPanel();
		subPanela_c.setBorder(BorderFactory.createTitledBorder("default"));
		subPanela_a_2.add(subPanela_c);
	  
		JPanel subPanela_d = new JPanel();
		subPanela_d.setBorder(BorderFactory.createTitledBorder("default"));
		subPanela_a_2.add(subPanela_d);
		
		JPanel subPanela_e = new JPanel();
		subPanela_e.setBorder(BorderFactory.createTitledBorder("default"));
		subPanela_a_2.add(subPanela_e);
		
		panel.add(subPanela);
		
		JPanel subPanelb = new JPanel();
		subPanelb.setLayout(new GridLayout(2,1));
		subPanelb.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
		subPanelb.setBackground(Color.YELLOW);
		
		JPanel subPanelb_a_1 = new JPanel();
		subPanelb_a_1.setBorder(BorderFactory.createLoweredBevelBorder());
		subPanelb_a_1.setLayout(new GridLayout(1,1));
		chatArea = new JTextPane();
		chatArea.setEditable(false);
		subPanelb_a_1_scroll = new JScrollPane();
		subPanelb_a_1_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		subPanelb_a_1_scroll.setViewportView(chatArea);
		subPanelb_a_1.add(subPanelb_a_1_scroll);
		subPanelb.add(subPanelb_a_1);
		
		JPanel subPanelb_a_2 = new JPanel();
		subPanelb_a_2.setLayout(new GridLayout(3,1));
		
		JPanel subPanelb_b_1 = new JPanel();
		subPanelb_b_1.setLayout(new GridLayout(1,1));
		chatInputArea = new JTextArea("hello......");
		chatInputArea.setLineWrap(true);
		chatInputArea.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					chatInputArea.append("\n");
				}else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					try {
						String text = chatInputArea.getText().trim();
						if(text == null || text.length() == 0 || text.length()>=100) {return;}
						resetTextArea("晓萌",chatInputArea.getText(),getRightStyle(),getRightBlackStyle());
						chatArea.selectAll();
						sendMsg();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					chatArea.paintImmediately(chatArea.getBounds());
					chatInputArea.setText("");
					setMaxValueBar();
				}
			}
		});
		subPanelb_b_1.add(chatInputArea);
		subPanelb_b_1.setBorder(BorderFactory.createLoweredBevelBorder());
		subPanelb_a_2.add(subPanelb_b_1);
		
		JPanel subPanelb_b_2 = new JPanel();
		subPanelb_b_2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		sendMsgButton = new JButton("send");
		sendMsgButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = chatInputArea.getText().trim();
				if(text == null || text.length() == 0 || text.length()>=100) {return;}
				try {
					resetTextArea("晓萌",chatInputArea.getText(),getRightStyle(),getRightBlackStyle()); 
					chatArea.selectAll();
					sendMsg();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				chatArea.paintImmediately(chatArea.getBounds());
				chatInputArea.setText("");
				setMaxValueBar();
			}
		});
		sendMsgButton.setAlignmentX(SwingConstants.EAST);
		sendMsgButton.setBackground(Color.GRAY);
		subPanelb_b_2.add(sendMsgButton);
		subPanelb_b_2.setBackground(Color.GREEN);
		subPanelb_a_2.add(subPanelb_b_2);
		
		subPanelb.add(subPanelb_a_2);
		
		panel.add(subPanelb);
		
		return panel;
	}
	
	private static void resetTextArea(String name,String msg,SimpleAttributeSet style,SimpleAttributeSet style2) throws Exception {
		StyledDocument document = chatArea.getStyledDocument();
		document.insertString(document.getLength(),"\n"+"["+name+"]",style);
		document.setParagraphAttributes(document.getLength(), 1, style, false);
		document.insertString(document.getLength(),"\n"+getCurrentDateTime(),style);
		document.setParagraphAttributes(document.getLength(), 1, style, false);
		document.insertString(document.getLength(),"\n"+msg,style2);
		document.setParagraphAttributes(document.getLength(), 1, style, false);
	}
	
	private static String getCurrentDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
		return sdf.format(new Date());
	}
	
	private static SimpleAttributeSet getLeftStyle() {
		SimpleAttributeSet left = new SimpleAttributeSet(); 
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT); 
		StyleConstants.setForeground(left, Color.BLUE);
		StyleConstants.setFontSize(left, 10);
		return left;
	}
		
	private static SimpleAttributeSet getRightStyle() {
		SimpleAttributeSet right = new SimpleAttributeSet(); 
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT); 
		StyleConstants.setForeground(right, Color.BLUE);
		StyleConstants.setFontSize(right, 10);
		return right;
	}
	
	private static SimpleAttributeSet getRightBlackStyle() {
		SimpleAttributeSet right = new SimpleAttributeSet(); 
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT); 
		StyleConstants.setForeground(right, Color.BLACK);
		StyleConstants.setFontSize(right, 20);
		return right;
	}
	
	/*
	 * 5430_msg_[还行]_msg
	 */
	private static void sendMsg() {
		String msg = ChatClient.getUserId()+"_msg_["+chatInputArea.getText()+"]_msg";
		new Thread(()->ChatClient.sendMsg(msg)).start();
	}
	
	/*
	 * @param msg format "from[5430_msg_[还行]_msg,...,...]from,"
	 */
	public static void resetTextArea(String msg) {
		try {
			msg = msg.substring(msg.indexOf("msg_[")+5, msg.indexOf("]_msg"));
			resetTextArea("永强",msg,getLeftStyle(),getRightBlackStyle());
			chatArea.selectAll();
			setMaxValueBar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void setMaxValueBar() {
		JScrollBar scrollBar = subPanelb_a_1_scroll.getVerticalScrollBar();
		scrollBar.setValue(scrollBar.getMaximum());
	}
}
