package com.passm.view.console.window;

import java.awt.Image;

import javax.swing.JFrame;

import com.passm.view.console.content.InputListener;

public class SwingConsoleFrame extends JFrame {

	private static final long serialVersionUID = -2505351731381244036L;
	
	private ConsolePanel panel;

	public SwingConsoleFrame(String name, InputListener inputListener) {
		super(name);
		panel = new ConsolePanel(inputListener);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(850, 470);
		setLocation(100, 50);
		add(panel);
		setResizable(false);
		setVisible(true);
	}
	
	public void setIconImage(Image image) {
		setIconImage(image);
	}
	
	public void setText(String text) {
		panel.setText(text);
	}
}
