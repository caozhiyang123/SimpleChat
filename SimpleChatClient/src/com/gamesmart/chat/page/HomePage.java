package com.gamesmart.chat.page;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.log4j.Logger;

import com.gamesmart.chat.core.EventListener;
import com.gamesmart.chat.core.SimpleChatClient;
import com.gamesmart.chat.vo.PlayerState;

public class HomePage extends JFrame{
	private static Logger logger = Logger.getLogger(HomePage.class);
	
	private static JPanel subPanelb_a_1_north;
	private static JPanel subPanelb_a_1_center;
	private static JTextPane chatArea;
	private static JTextArea chatInputArea;
	private static JButton sendMsgButton;
	private static HomePage homePage = null;
	private static JScrollPane subPanelb_a_1_scroll;
	private static JPanel subPanela_b;//lobby users
	private static JButton selfButton;
	
	private Map<Long,JButton> joinedUsers = new HashMap<Long,JButton>();
	
	private HomePage() {
		createHomePage();
		EventListener.getInstance().createBatchJob();
	}
	public static HomePage getInstance() {
		if(homePage == null) {
			synchronized (HomePage.class) {
				if(homePage == null) {
					homePage = new HomePage();
				}
			}
		}
		return homePage;
	}
	
	private void createHomePage() {
		/*
		 * this.setUndecorated(true);
		 * this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		 * com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.85f);
		 */
		this.setTitle("SimpleChat");
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
		
		JPanel subPanela_a = new JPanel() {
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = Color.RED;
                Color color2 = Color.GREEN;
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
		};
		subPanela_a.setBorder(BorderFactory.createTitledBorder("groups"));
		subPanela_a_1.add(subPanela_a);
		
		subPanela_b = new JPanel() {
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = Color.RED;
                Color color2 = Color.GREEN;
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
		};
		subPanela_b.setBorder(BorderFactory.createTitledBorder("lobby"));
		FlowLayout flowLayout = new FlowLayout(10);
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		subPanela_b.setLayout(flowLayout);
		subPanela_a_1.add(subPanela_b);
		
		JPanel subPanela_a_2 = new JPanel();
		subPanela_a_2.setLayout(new GridLayout(1,3));
		subPanela_a_2.setBackground(Color.YELLOW);
		subPanela.add(subPanela_a_2);
		
		JPanel subPanela_c = new JPanel() {
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = Color.RED;
                Color color2 = Color.GREEN;
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
		};
		subPanela_c.setBorder(BorderFactory.createTitledBorder("default"));
		subPanela_a_2.add(subPanela_c);
	  
		JPanel subPanela_d = new JPanel() {
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = Color.RED;
                Color color2 = Color.GREEN;
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
		};
		subPanela_d.setBorder(BorderFactory.createTitledBorder("default"));
		subPanela_a_2.add(subPanela_d);
		
		JPanel subPanela_e = new JPanel() {
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = Color.RED;
                Color color2 = Color.GREEN;
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
		};
		subPanela_e.setBorder(BorderFactory.createTitledBorder("default"));
		subPanela_a_2.add(subPanela_e);
		
		panel.add(subPanela);
		
		JPanel subPanelb = new JPanel();
		subPanelb.setLayout(new GridLayout(2,1));
		subPanelb.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
		subPanelb.setBackground(Color.YELLOW);
		
		JPanel subPanelb_a_1 = new JPanel();
		subPanelb_a_1.setBorder(BorderFactory.createLoweredBevelBorder());
		subPanelb_a_1.setLayout(new BorderLayout());
		
		subPanelb_a_1_north = new JPanel();
		subPanelb_a_1_north.setBackground(Color.WHITE);
		subPanelb_a_1_north.setPreferredSize(new Dimension(0,30));
		subPanelb_a_1.add(subPanelb_a_1_north,BorderLayout.NORTH);
		
		JPanel subPanelb_a_1_west = new JPanel();
		subPanelb_a_1_west.setBackground(Color.WHITE);
		subPanelb_a_1_west.setPreferredSize(new Dimension(30,0));
		subPanelb_a_1.add(subPanelb_a_1_west,BorderLayout.WEST);
		
		subPanelb_a_1_center = new JPanel();
		subPanelb_a_1.add(subPanelb_a_1_center,BorderLayout.CENTER);
		subPanelb_a_1_center.setLayout(new GridLayout(1,1));
		
		chatArea = new JTextPane() {
			/*
			 * @Override protected void paintComponent(Graphics g) {
			 * super.paintComponent(g); Graphics2D g2d = (Graphics2D) g;
			 * g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
			 * RenderingHints.VALUE_RENDER_QUALITY); int w = getWidth(); int h =
			 * getHeight(); Color color1 = Color.GRAY; Color color2 = Color.WHITE;
			 * GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
			 * g2d.setPaint(gp); g2d.fillRect(0, 0, w, h); }
			 */
		};
		chatArea.setEditable(false);
		subPanelb_a_1_scroll = new JScrollPane();
		subPanelb_a_1_scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		subPanelb_a_1_scroll.setViewportView(chatArea);
//		subPanelb_a_1_scroll.add(new TestPanel1(),"testPanel1");
//		subPanelb_a_1_scroll.add(new TestPanel2(),"testPanel2");
		subPanelb_a_1_center.add(subPanelb_a_1_scroll);
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
					String msg = chatInputArea.getText().trim();
					boolean res = sendMsg(msg);
					if(!res) {
						new WarningFrame();
						return;
					}
					try {
						if(msg == null || msg.length() == 0 || msg.length()>=100) {return;}
						resetTextArea(selfButton.getText(),msg,getRightStyle(),getRightBlackStyle());
						chatArea.selectAll();
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
		
		JPanel subPanelb_b_2 = new JPanel() {
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = Color.RED;
                Color color2 = Color.GREEN;
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
		};
		subPanelb_b_2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		sendMsgButton = new JButton("send");
		sendMsgButton.setBorder(BorderFactory.createRaisedBevelBorder());
		sendMsgButton.setFont(new Font(null, Font.CENTER_BASELINE, 20));
		sendMsgButton.setForeground(Color.WHITE);
		sendMsgButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = chatInputArea.getText().trim();
				if(msg == null || msg.length() == 0 || msg.length()>=100) {return;}
				try {
					boolean res = sendMsg(msg);
					if(!res) {
						new WarningFrame();
						return;
					}
					resetTextArea(selfButton.getText(),msg,getRightStyle(),getRightBlackStyle()); 
					chatArea.selectAll();
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
		subPanelb_b_2.setBackground(Color.WHITE);
		subPanelb_a_2.add(subPanelb_b_2);
		
		subPanelb.add(subPanelb_a_2);
		
		panel.add(subPanelb);
		
		return panel;
	}

	private void createUser(String alias, long userId) {
		JButton userButton = new JButton();
		if(userId == SimpleChatClient.getInstance().getPlayerState().getPlayerVO().getUserId()) {
			selfButton = userButton; 
		}
		joinedUsers.put(userId,userButton);
		userButton.setHorizontalAlignment(SwingConstants.LEFT);
		userButton.setText(alias);
		userButton.setPreferredSize(new Dimension(217,30));
		userButton.setBorder(BorderFactory.createLoweredBevelBorder());
		userButton.setFont(new Font(null, Font.CENTER_BASELINE, 15));
		userButton.setBackground(Color.GRAY);
		userButton.setForeground(Color.WHITE);
		userButton.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getClickCount() == 2 && userId == SimpleChatClient.getInstance().getPlayerState().getPlayerVO().getUserId()) {
					UserInfoSettingPage.getInstance(selfButton.getText(),userId);
				}
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		subPanela_b.add(userButton);
		subPanela_b.updateUI();
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
		StyleConstants.setFontSize(right, 16);
		return right;
	}
	
	private static boolean sendMsg(String msg) {
		SimpleChatClient client = SimpleChatClient.getInstance();
		return client.sendMsg(msg);
	}
	
	public static void resetTextArea(String msg) {
		try {
			resetTextArea("永强",msg,getLeftStyle(),getRightBlackStyle());
			chatArea.selectAll();
			setMaxValueBar();
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	private static void setMaxValueBar() {
		JScrollBar scrollBar = subPanelb_a_1_scroll.getVerticalScrollBar();
		scrollBar.setValue(scrollBar.getMaximum());
	}
	
	public void appendMsg(String msg) {
		resetTextArea(msg);
	}
	
	//lobby users list
	public void createUserButton(String alias,long userId) {
		createUser(alias,userId);
	}

	public void updateUserAlias(String alias, long userId) {
		selfButton.setText(alias);
	}
	
	public void updateUserList(List<Long> joinedUsers) {
		//add new user button
		for (Long joinedUserId : joinedUsers) {
			boolean exist = false;
			for (Map.Entry<Long, JButton> map: this.joinedUsers.entrySet()) {
				Long userId = map.getKey();
				if((long)joinedUserId == (long)userId) {
					exist = true;
					break;
				}
			}
			if(exist) {
				continue;
			}else {
				createUserButton(String.valueOf(joinedUserId),joinedUserId);
			}
		}
		//remove disconnected user button
		for (Map.Entry<Long, JButton> map: this.joinedUsers.entrySet()) {
			Long userId = map.getKey();
			boolean exist = false;
			for (Long joinedUserId : joinedUsers) {
				if(userId == joinedUserId) {
					exist = true;
					break;
				}
			}
			if(!exist) {
				JButton value = map.getValue();
				value.getParent().remove(value);
			}
		}
	}
	
	public void verifyUserButton(long sendId, String alias) {
		
	}
}
