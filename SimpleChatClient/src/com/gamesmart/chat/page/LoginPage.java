package com.gamesmart.chat.page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.PropertyConfigurator;

import com.gamesmart.chat.core.SimpleChatClient;
import com.gamesmart.chat.io.Request;
import com.gamesmart.chat.util.SimpleChatUtil;
import com.gamesmart.chat.vo.PlayerState;

import sfs2x.client.SmartFox;

public class LoginPage extends JFrame {
    private static LoginPage loginPage;
    private JTextField userNameField;
    private String userName;
    private JPasswordField passField;
    private char[] pass;
    private boolean inVisiablePass;
    private JLabel usernameWarningLabel;
    private JLabel passWarningLabel;
    private JLabel loginLabel;
    private JButton loginButton;

    private LoginPage() {
        createLoginPage();
    }
    
    public static LoginPage getInstance() {
    	if(loginPage == null) {
    		synchronized(LoginPage.class) {
    			if(loginPage == null) {
    				loginPage = new LoginPage();
    			}
    		}
    	}
    	return loginPage;
    }
    
    public static void main(String[] args) {
    	//init log sys
    	System.setProperty("WORKDIR", "log");
    	PropertyConfigurator.configure("config/log4j.properties");
    	
    	getInstance();
    }

    private void createLoginPage() {
        this.setUndecorated(true);
        this.setResizable(false);
        this.getRootPane().setWindowDecorationStyle(JRootPane.WARNING_DIALOG);
        com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.86f);
        this.setTitle("");
        this.setPreferredSize(new Dimension(400, 400));
        this.setSize(new Dimension(400, 400));
        this.setLocationRelativeTo(null);
        this.getContentPane().add(createLoginPanel());
        this.pack();
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowevent) {
            	loginPage.dispose();
            }
        });

        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                com.sun.awt.AWTUtilities.setWindowOpacity(loginPage, 0.86f);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                com.sun.awt.AWTUtilities.setWindowOpacity(loginPage, 0.86f);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub

            }
        });
    }

    private Component createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1));

        JPanel firstRow = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = Color.GRAY;
                Color color2 = Color.WHITE;
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        firstRow.setLayout(new GridLayout(1, 1));
        firstRow.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        panel.add(firstRow);
        JLabel title = new JLabel();
        title.setText("Simple Chat");
        title.setFont(new Font(null, Font.CENTER_BASELINE, 30));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(Color.WHITE);
        firstRow.add(title);

        JPanel secondRow = new JPanel();
        secondRow.setLayout(new BorderLayout());
        secondRow.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        secondRow.setBackground(Color.GRAY);
        panel.add(secondRow);

        JLabel nameLeftLabel = new JLabel(" (¬_¬ )   ");
        secondRow.add(nameLeftLabel, BorderLayout.WEST);
        JLabel nameRightLabel = new JLabel("   @163.com    ");
        nameRightLabel.setForeground(Color.WHITE);
        secondRow.add(nameRightLabel, BorderLayout.EAST);

        userNameField = new JTextField();
        userNameField.setFont(new Font(null, Font.CENTER_BASELINE, 20));
        userNameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                userName = userNameField.getText();
                verifyUserName();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                userName = userNameField.getText();
                verifyUserName();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        secondRow.add(userNameField, BorderLayout.CENTER);

        JPanel FirstWarningRow = new JPanel();
        FirstWarningRow.setLayout(new BorderLayout());
        FirstWarningRow.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
        FirstWarningRow.setBackground(Color.WHITE);
        usernameWarningLabel = new JLabel();//WARNING<>
        usernameWarningLabel.setFont(new Font(null, Font.CENTER_BASELINE, 15));
        usernameWarningLabel.setForeground(Color.RED);
        FirstWarningRow.add(usernameWarningLabel);
        panel.add(FirstWarningRow);

        JPanel thirdRow = new JPanel();
        thirdRow.setLayout(new BorderLayout());
        thirdRow.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        thirdRow.setBackground(Color.GRAY);
        panel.add(thirdRow);

        JLabel passLeftLabel = new JLabel(" (⊙_⊙;)");
        thirdRow.add(passLeftLabel, BorderLayout.WEST);

        passField = new JPasswordField();
        passField.setFont(new Font(null, Font.CENTER_BASELINE, 20));
        passField.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                pass = passField.getPassword();
                verifyPassword();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                pass = passField.getPassword();
                verifyPassword();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        thirdRow.add(passField, BorderLayout.CENTER);

        JButton passRightButton = new JButton("(✿◡‿◡)");
        passRightButton.setBackground(Color.WHITE);
        passRightButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if ("(✿◡‿◡)".equals(passRightButton.getText())) {
                    passRightButton.setText(" (⊙_⊙;) ");
                    inVisiablePass = true;
                } else {
                    passRightButton.setText("(✿◡‿◡)");
                    inVisiablePass = false;
                }
            }

        });
        thirdRow.add(passRightButton, BorderLayout.EAST);


        JPanel fourthRow = new JPanel();
        fourthRow.setLayout(new BorderLayout());
        fourthRow.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        fourthRow.setBackground(Color.WHITE);
        panel.add(fourthRow);

        JButton forgetPassLabel = new JButton("忘记密码");
        forgetPassLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
        forgetPassLabel.setBackground(Color.WHITE);
        fourthRow.add(forgetPassLabel, BorderLayout.EAST);


        passWarningLabel = new JLabel();//WARNING<>
        passWarningLabel.setFont(new Font(null, Font.CENTER_BASELINE, 15));
        passWarningLabel.setForeground(Color.RED);
        fourthRow.add(passWarningLabel, BorderLayout.WEST);

        JPanel fifthRow = new JPanel();
        fifthRow.setLayout(new BorderLayout());
        fifthRow.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        fifthRow.setBackground(Color.GRAY);
        panel.add(fifthRow);

        loginButton = new JButton("登  录");
        loginButton.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.WHITE));
        loginButton.setFont(new Font(null, Font.CENTER_BASELINE, 30));
        loginButton.setHorizontalAlignment(JLabel.CENTER);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.BLUE);
        loginButton.addKeyListener(new KeyListener() {
			
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
				loginButton.setEnabled(false);
				
			}
		});
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userName == null && pass == null) {
                    usernameWarningLabel.setText("非法警告< 账号不能为空 >");
                    passWarningLabel.setText("非法警告< 密码不能为空 >");
                    return;
                }
                //use regex to check if it is digit
                if (userName == null || !userName.matches("-?\\d+(\\.\\d+)?")) {
                    usernameWarningLabel.setText("非法警告< 账号不能为非数字 >");
                    passWarningLabel.setText("");
                    return;
                }

                if (pass == null) {
                    usernameWarningLabel.setText("");
                    passWarningLabel.setText("非法警告< 密码不能为空 >");
                    return;
                }

                StringBuilder passSb = new StringBuilder();
                for (int i = 0; i < pass.length; i++) {
                    passSb.append(pass[i]);
                }

                if(!verifyUserName() || !verifyPassword()){
                    return;
                }
                userName = userName.trim();
                String password = passSb.toString().trim();
                if (userName.isEmpty() && password.isEmpty()) {
                    usernameWarningLabel.setText("非法警告< 账号不能为空 >");
                    passWarningLabel.setText("非法警告< 密码不能为空 >");
                    return;
                }
                if (userName.isEmpty()) {
                    usernameWarningLabel.setText("非法警告< 账号不能为空 >");
                    passWarningLabel.setText("");
                    return;
                }
                if (password.isEmpty()) {
                    usernameWarningLabel.setText("");
                    passWarningLabel.setText("非法警告< 密码不能为空 >");
                    return;
                }

                usernameWarningLabel.setText("");
                passWarningLabel.setText("");
                System.out.println("userName:" + userName + " ,pass:" + passSb.toString());
                connectSfs(Long.valueOf(userName),passSb.toString());
            }
        });
        fifthRow.add(loginButton, BorderLayout.CENTER);
        
        JPanel sixth = new JPanel();
        sixth.setLayout(new BorderLayout());
        sixth.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        sixth.setBackground(Color.WHITE);
        panel.add(sixth);
        
        loginLabel = new JLabel();
        loginLabel.setFont(new Font(null,Font.CENTER_BASELINE,15));
        loginLabel.setForeground(Color.RED);
        sixth.add(loginLabel, BorderLayout.CENTER);
        /*
         * passWarningLabel = new JLabel();//WARNING<>
        passWarningLabel.setFont(new Font(null, Font.CENTER_BASELINE, 15));
        passWarningLabel.setForeground(Color.RED);
        fourthRow.add(passWarningLabel, BorderLayout.WEST);
         */

        return panel;
    }
    
    private void connectSfs(long userName, String pass) {
    	System.out.print("- - - -connect to sfs- - - -");
    	PlayerState playerState = SimpleChatUtil.createPlayerState(userName,pass);
    	SimpleChatClient client = SimpleChatClient.getInstance();
    	client.init(playerState);
    	client.connect(new SmartFox(), Request.IP, Request.PORT);
    }
    
    public void loginSuccessed() {
    	loginPage.dispose();
    	
    	//open chat page
    	HomePage.getInstance();
    }
    
    public void loginFailed(String error) {
        loginLabel.setText(error);
    }

    private boolean verifyPassword() {
        for (char ele : pass) {
            if (ele == ' ') {
                passWarningLabel.setText("非法警告< 密码不能含有空格 >");
                return false;
            }
        }
        passWarningLabel.setText("");
        return true;
    }

    private boolean verifyUserName() {
        char[] nameChar = userName.toCharArray();
        for (char ele : nameChar) {
            if (ele == ' ') {
                usernameWarningLabel.setText("非法警告< 账号不能含有空格 >");
                return false;
            }
        }
        usernameWarningLabel.setText("");
        return true;
    }
}
