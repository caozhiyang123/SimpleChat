package com.gamesmart.chat.page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

public class UserInfoSettingPage extends JFrame {
	private JTextField aliasTextField;
	private JButton updateButton;
	private static UserInfoSettingPage userInfoSettingPage = null;
	private UserInfoSettingPage() {}
	private static String alias;
	private static long userId;
	
	public static UserInfoSettingPage getInstance(String alias, long userId) {
		if(userInfoSettingPage == null) {
			synchronized(UserInfoSettingPage.class) {
				if(userInfoSettingPage == null) {
					userInfoSettingPage = new UserInfoSettingPage();
					userInfoSettingPage.alias = alias;
					userInfoSettingPage.userId = userId;
					userInfoSettingPage.init();
				}
			}
		}
		userInfoSettingPage.setVisible(true);
		return userInfoSettingPage;
	}
	
	
	private void init() {
		this.setUndecorated(true);
        this.setResizable(false);
        this.getRootPane().setWindowDecorationStyle(JRootPane.WARNING_DIALOG);
        com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.86f);
		this.setTitle("SimpleChat Warning");
		this.setPreferredSize(new Dimension(300, 200));
		this.setSize(new Dimension(400, 400));
		this.setLocationRelativeTo(null);
		this.getContentPane().add(createPanel());
		this.pack();
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowevent)
            {
            	userInfoSettingPage.dispose();
            }
        });
	}

	private Component createPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,2));
		
		JPanel firstRowPanel = new JPanel(new BorderLayout());
		firstRowPanel.setBackground(Color.GREEN);
		firstRowPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		panel.add(firstRowPanel);
		
		JLabel aliasField = new JLabel("NAME");
		aliasField.setFont(new Font(null, Font.CENTER_BASELINE, 20));
		firstRowPanel.add(aliasField,BorderLayout.WEST);
		
		aliasTextField = new JTextField(alias);
		aliasTextField.setFont(new Font(null, Font.CENTER_BASELINE, 18));
		firstRowPanel.add(aliasTextField,BorderLayout.CENTER);
		
		JPanel secondPanel = new JPanel(new BorderLayout());
		secondPanel.setBackground(Color.GREEN);
		secondPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		panel.add(secondPanel);
		
		JLabel secondPanelLabel = new JLabel("");
		secondPanelLabel.setFont(new Font(null, Font.CENTER_BASELINE, 20));
		secondPanel.add(secondPanelLabel,BorderLayout.WEST);
		
		JTextField secondPanelTextField = new JTextField();
		secondPanelTextField.setFont(new Font(null, Font.CENTER_BASELINE, 18));
		secondPanel.add(secondPanelTextField,BorderLayout.CENTER);
		
		panel.add(new JPanel(new BorderLayout()));
		
		JPanel fouthPanel = new JPanel();
		fouthPanel.setLayout(new BorderLayout());
		panel.add(fouthPanel);
		
		JButton updateButton = new JButton("update");
		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String alias = aliasTextField.getText();
				HomePage.getInstance().updateUserAlias(alias,UserInfoSettingPage.userId);
				userInfoSettingPage.setVisible(false);
			}
			
		});
		fouthPanel.add(updateButton,BorderLayout.CENTER);
		return panel;
	}
}
