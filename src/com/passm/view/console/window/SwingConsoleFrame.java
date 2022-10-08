package com.passm.view.console.window;

import java.awt.Image;
import javax.swing.JFrame;

import com.passm.view.console.content.InputListener;

public class SwingConsoleFrame extends JFrame {

	private static final long serialVersionUID = -2505351731381244036L;
	
	private static final int FRAME_WIDTH = 23;
	private static final int FRAME_HEIGHT = 42;
	
	private ConsolePanel panel;

	public SwingConsoleFrame(String name, InputListener inputListener) {
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new ConsolePanel(inputListener);
		add(panel);
	}
	
	public void setIconImage(Image image) {
		setIconImage(image);
	}
	
	public void setText(String text) {
		panel.setText(text);
	}
	
	public void setFontSize(int fontSize) {
		panel.setFontSize(fontSize);
	}

	public void setSizeInCharacters(int width, int height) {
		setSize(width * panel.getFontWidth() + FRAME_WIDTH, height * panel.getFontHeight() + FRAME_HEIGHT);
	}
	
	public int getWidthInCharacters() {
		return (getWidth() - FRAME_WIDTH) / panel.getFontWidth();
	}
	
	public int getHeightInCharacters() {
		return (getHeight() - FRAME_HEIGHT) / panel.getFontHeight();
	}
}
