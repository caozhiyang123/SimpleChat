package com.gamesmart.chat.page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;

public class WarningFrame extends JFrame{

	public WarningFrame() {
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
                System.exit(0);
            }
        });
	}
	
	private Component createPanel() {
		JPanel panel = new JPanel() {
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
		panel.setLayout(new BorderLayout());
		JLabel label = new JLabel("CONNECTION DISCONNECTED ! ! !",SwingConstants.CENTER);
		label.setFont(new Font(null, Font.CENTER_BASELINE, 15));
		label.setForeground(Color.WHITE);
		panel.add(label,BorderLayout.CENTER);
		return panel;
	}
}
