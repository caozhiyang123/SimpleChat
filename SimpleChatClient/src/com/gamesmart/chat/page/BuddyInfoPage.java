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

public class BuddyInfoPage extends JFrame {
	public BuddyInfoPage(String buddyName, long userId,String state,boolean isBuddy){
		this.setUndecorated(true);
        this.setResizable(false);
        this.getRootPane().setWindowDecorationStyle(JRootPane.WARNING_DIALOG);
        com.sun.awt.AWTUtilities.setWindowOpacity(this, 0.86f);
		this.setTitle("SimpleChat Warning");
		this.setPreferredSize(new Dimension(300, 200));
		this.setSize(new Dimension(400, 400));
		this.setLocationRelativeTo(null);
		this.getContentPane().add(createPanel(buddyName,userId,state,isBuddy));
		this.pack();
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowevent)
            {
            	dispose();
            }
        });
	}
	
	private Component createPanel(String buddyName, long userId,String state,boolean isBuddy) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6,2));
		
		//- - - -  - - - - -  - 1 - - - -  -- - - -  -
		JPanel firstRowPanel = new JPanel(new BorderLayout());
		firstRowPanel.setBackground(Color.GREEN);
		firstRowPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		panel.add(firstRowPanel);
		
		JLabel aliasField = new JLabel("Name");
		aliasField.setFont(new Font(null, Font.CENTER_BASELINE, 20));
		firstRowPanel.add(aliasField,BorderLayout.WEST);
		
		JTextField aliasTextField = new JTextField(buddyName);
		aliasTextField.setFont(new Font(null, Font.CENTER_BASELINE, 18));
		aliasTextField.setEnabled(false);
		firstRowPanel.add(aliasTextField,BorderLayout.CENTER);
		//- - - -  - - - - -  - 1 - - - -  -- - - -  -
		
		//- - - -  - - - - -  - 2 - - - -  -- - - -  -
		JPanel secondPanel = new JPanel(new BorderLayout());
		secondPanel.setBackground(Color.GREEN);
		secondPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		panel.add(secondPanel);
		
		JLabel stateLabel = new JLabel("State");
		stateLabel.setFont(new Font(null, Font.CENTER_BASELINE, 20));
		secondPanel.add(stateLabel,BorderLayout.WEST);
		
		JTextField stateText = new JTextField("online");
		stateText.setFont(new Font(null, Font.CENTER_BASELINE, 18));
		stateText.setEnabled(false);
		secondPanel.add(stateText,BorderLayout.CENTER);
		//- - - -  - - - - -  - 2 - - - -  -- - - -  -
		
		//- - - -  - - - - -  - 3 - - - -  -- - - -  -
		JPanel thirdPanel = new JPanel(new BorderLayout());
		thirdPanel.setBackground(Color.GREEN);
		thirdPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		panel.add(thirdPanel);
		
		JLabel thirdPanelLabel = new JLabel("Temp");
		thirdPanelLabel.setFont(new Font(null,Font.CENTER_BASELINE,20));
		thirdPanel.add(thirdPanelLabel, BorderLayout.WEST);
		
		JTextField tempText = new JTextField(isBuddy?"NO":"YES");
		tempText.setFont(new Font(null, Font.CENTER_BASELINE, 18));
		tempText.setEnabled(false);
		thirdPanel.add(tempText,BorderLayout.CENTER);
		//- - - -  - - - - -  - 3 - - - -  -- - - -  -
		
		//- - - -  - - - - -  - 4 - - - -  -- - - -  -
		  JPanel fouthPanel = new JPanel(); 
		  fouthPanel.setLayout(new BorderLayout());
		  panel.add(fouthPanel);
		  
		  JButton addBuddy = new JButton("Add Buddy");
		  addBuddy.setEnabled(isBuddy?false:true);
		  addBuddy.addActionListener(new ActionListener() {
			  @Override 
			  public void actionPerformed(ActionEvent e) {
				  HomePage.getInstance().addBuddy(String.valueOf(userId));
				  setVisible(false);
			  }
		  });
		  fouthPanel.add(addBuddy,BorderLayout.CENTER);
		//- - - -  - - - - -  - 4 - - - -  -- - - -  -
		
		//- - - -  - - - - -  - 5 - - - -  -- - - -  -
		JPanel fifthPanel = new JPanel();
		fifthPanel.setLayout(new BorderLayout());
		panel.add(fifthPanel);
		
		JButton removeBuddy = new JButton("Remove Buddy");
		removeBuddy.setEnabled(isBuddy?true:false);
		removeBuddy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HomePage.getInstance().removeBuddy(String.valueOf(userId));
				setVisible(false);
			}
		});
		fifthPanel.add(removeBuddy,BorderLayout.CENTER);
		//- - - -  - - - - -  - 5 - - - -  -- - - -  -
		
		//- - - -  - - - - -  - 6 - - - -  -- - - -  -
		JPanel sixthPanel = new JPanel();
		sixthPanel.setLayout(new BorderLayout());
		panel.add(sixthPanel);
		
		JButton blockBuddy = new JButton("Block Buddy");
		blockBuddy.setEnabled(isBuddy?true:false);
		blockBuddy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				HomePage.getInstance().blockBuddy(String.valueOf(userId));
				setVisible(false);
			}
		});
		sixthPanel.add(blockBuddy,BorderLayout.CENTER);
		//- - - -  - - - - -  - 6 - - - -  -- - - -  -
		
		return panel;
	}
	
}
