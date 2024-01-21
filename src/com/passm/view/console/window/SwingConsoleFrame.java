package com.passm.view.console.window;

import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.passm.view.console.content.InputListener;

public class SwingConsoleFrame extends JFrame {

	private static final long serialVersionUID = -2505351731381244036L;

	private static final int FRAME_WIDTH = 23;
	private static final int FRAME_HEIGHT = 43;
	private static final int SCROLL_SPEED = 10;

	private final ConsolePanel panel;
	private JScrollPane scrollPane;

	private final boolean withScroll;

	private SwingConsoleFrame(String name, InputListener inputListener, boolean withScroll) {
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new ConsolePanel(inputListener);
		add(panel);
		this.withScroll = withScroll;
		if(withScroll) {
			scrollPane = new JScrollPane(panel.getTextPane(),
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.getVerticalScrollBar().addAdjustmentListener(inputListener);
			scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);
			scrollPane.setBorder(BorderFactory.createEmptyBorder());
			add(scrollPane);
		}
	}

	public static SwingConsoleFrame create(String name, InputListener inputListener, boolean withScroll) {
		return new SwingConsoleFrame(name, inputListener, withScroll);
	}

	public static SwingConsoleFrame create(String name, InputListener inputListener) {
		return new SwingConsoleFrame(name, inputListener, false);
	}

	public void setIconImage(Image image) {
		setIconImage(image);
	}

	public void setText(String text, boolean scrollDown) {
		panel.setText(text, scrollDown);
	}

	public void setFontSize(int fontSize) {
		panel.setFontSize(fontSize);
	}

	public void setSizeInCharacters(int width, int height) {
		setSize(width * panel.getFontWidth() + FRAME_WIDTH + getWithOfVerticalScrollBar(), height * (panel.getFontHeight() + 1) + FRAME_HEIGHT);
	}

	public int getWidthInCharacters() {
		return (getWidth() - FRAME_WIDTH - getWithOfVerticalScrollBar()) / panel.getFontWidth();
	}

	public int getHeightInCharacters() {
		return (getHeight() - FRAME_HEIGHT) / (panel.getFontHeight() + 1);
	}

	private int getWithOfVerticalScrollBar() {
		return withScroll ? scrollPane.getVerticalScrollBar().getWidth() : 0;
	}
}
