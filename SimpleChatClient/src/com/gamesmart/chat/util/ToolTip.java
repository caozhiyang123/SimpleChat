package com.gamesmart.chat.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.border.EtchedBorder;

public class ToolTip {
	private int _width = 300;
	private int _height = 100;
	private int _step = 30;
	private int _stepTime = 30;
	private int _displayTime = 6000;
	private int _countOfToolTip = 0;
	private int _maxToolTip = 0;
	private int _maxToolTipSceen;
	private Font _font;
	private Color _bgColor;
	private Color _border;
	private Color _messageColor;

	int _gap;
	boolean _useTop = true;
	
	private static ToolTip toolTip = null;

	private ToolTip() {
		_font = new Font(null, 0, 12);
		_bgColor = new Color(255, 255, 225);
		_border = Color.BLACK;
		_messageColor = Color.BLACK;
		_useTop = true;
		try {
			JWindow.class.getMethod("setAlwaysOnTop",
			new Class[] { Boolean.class });
		} catch (Exception e) {
			_useTop = false;
		}
	}
	
	public static ToolTip getInstance() {
		if(toolTip == null) {
			synchronized(ToolTip.class) {
				if(toolTip == null) {
					toolTip = new ToolTip();
				}
			}
		}
		return toolTip;
	}

	class ToolTipSingle extends JWindow {
		private static final long serialVersionUID = 1L;
		private JLabel _iconLabel = new JLabel();
		private JTextArea _message = new JTextArea();
		
		public ToolTipSingle() {
			initComponents();
		}

		private void initComponents() {
			setSize(_width, _height);
			_message.setFont(getMessageFont());
			JPanel externalPanel = new JPanel(new BorderLayout());
			externalPanel.setBackground(_bgColor);

			JPanel innerPanel = new JPanel(new BorderLayout());
			innerPanel.setBackground(_bgColor);
			_message.setBackground(_bgColor);
			_message.setMargin(new Insets(4, 4, 4, 4));
			_message.setLineWrap(true);
			_message.setWrapStyleWord(true);

			EtchedBorder etchedBorder = (EtchedBorder) BorderFactory.createEtchedBorder();

			externalPanel.setBorder(etchedBorder);
			externalPanel.add(innerPanel);

			_message.setForeground(getMessageColor());

			innerPanel.add(_iconLabel,BorderLayout.WEST);
			innerPanel.add(_message,BorderLayout.CENTER);

			getContentPane().add(externalPanel);
		}

		public void animate() {
			new Animation(this).start();
		}
	}

	class Animation extends Thread {
		ToolTipSingle _single;
		public Animation(ToolTipSingle single) {
			this._single = single;
		}

		private void animateVertically(int posx, int startY, int endY)throws InterruptedException {
			_single.setLocation(posx, startY);
			if (endY < startY) {
				for (int i = startY; i > endY; i -= _step) {
					_single.setLocation(posx, i);
					Thread.sleep(_stepTime);
				}
			} else {
				for (int i = startY; i < endY; i += _step) {
					_single.setLocation(posx, i);
					Thread.sleep(_stepTime);
				}
			}

			_single.setLocation(posx, endY);
		}

		public void run() {
			try {
				boolean animate = true;
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				Rectangle screenRect = ge.getMaximumWindowBounds();

				int screenHeight = (int) screenRect.height;
				int startYPosition;
				int stopYPosition;
				if (screenRect.y > 0) {
					animate = false;
				}

				_maxToolTipSceen = screenHeight / _height;
				int posx = (int) screenRect.width - _width - 1;
				_single.setLocation(posx, screenHeight);
				_single.setVisible(true);

				if (_useTop) {
					_single.setAlwaysOnTop(true);
				}

				if (animate) {
					startYPosition = screenHeight;
					stopYPosition = startYPosition - _height - 1;

					if (_countOfToolTip > 0) {
						stopYPosition = stopYPosition
						- (_maxToolTip % _maxToolTipSceen * _height);
					} else {
						_maxToolTip = 0;
					}
				} else {
					startYPosition = screenRect.y - _height;
					stopYPosition = screenRect.y;
					if (_countOfToolTip > 0) {
						stopYPosition = stopYPosition + (_maxToolTip % _maxToolTipSceen * _height);
					} else {
						_maxToolTip = 0;
					}
				}

				_countOfToolTip++;
				_maxToolTip++;
				animateVertically(posx, startYPosition, stopYPosition);

				Thread.sleep(_displayTime);

				animateVertically(posx, stopYPosition, startYPosition);

				_countOfToolTip--;
				_single.setVisible(false);
				_single.dispose();

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void setToolTip(Icon icon, String msg) {
		ToolTipSingle single = new ToolTipSingle();

		if (icon != null) {
			single._iconLabel.setIcon(icon);
		}

		single._message.setText(msg);
		single.animate();
	}

	public void setToolTip(String msg) {setToolTip(null, msg);}
	public Font getMessageFont() {return _font;}
	public void setMessageFont(Font font) {_font = font;}
	public Color getBorderColor() {return _border;}
	public void setBorderColor(Color borderColor) {this._border = borderColor;}
	public int getDisplayTime() {return _displayTime;}
	public void setDisplayTime(int displayTime) {this._displayTime = displayTime;}
	public int getGap() {return _gap;}
	public void setGap(int gap) {this._gap = gap;}
	public Color getMessageColor() {return _messageColor;}
	public void setMessageColor(Color messageColor) {this._messageColor = messageColor;}
	public int getStep() {return _step;}
	public void setStep(int _step) {this._step = _step;}
	public int getStepTime() {return _stepTime;}
	public void setStepTime(int _stepTime) {this._stepTime = _stepTime;}
	public Color getBackgroundColor() {return _bgColor;}
	public void setBackgroundColor(Color bgColor) {this._bgColor = bgColor;}
	public int getHeight() {return _height;}
	public void setHeight(int height) {this._height = height;}
	public int getWidth() {return _width;}
	public void setWidth(int width) {this._width = width;}
	//test
	public static void main(String[] args) {
		ToolTip tip = new ToolTip();
		tip.setToolTip(new ImageIcon("config/msg.PNG"),"TEST"+"\r\n"+"hello");
	}
}