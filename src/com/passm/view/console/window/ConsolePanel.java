package com.passm.view.console.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.passm.view.console.content.InputListener;

public class ConsolePanel extends JPanel {
	
	private final static Logger LOGGER = Logger.getLogger(ConsolePanel.class.getName());

	private static final long serialVersionUID = -7669891546520723020L;
	
	private static final String FONT_NAME = "Lucida Console";

	private int fontWidth;
	private int fontHeight; 
	
	private final JTextPane pane;
	private final StyledDocument textField;
	private final Style whiteStyle;
	
	protected ConsolePanel(InputListener inputListener) {
		super();
		LOGGER.info("Initializing of ConsolePanel");
		setLayout(new BorderLayout());
		pane = new JTextPane();
		pane.addKeyListener(inputListener);
		pane.setEditable(false);
		pane.setBackground(Color.BLACK);
		textField = pane.getStyledDocument();
		whiteStyle = pane.addStyle("", null);
		StyleConstants.setForeground(whiteStyle, Color.WHITE);
		add(pane);
		LOGGER.info("ConsolePanel inizialized");
	}
	
	protected void setText(String text) {
		try {
			textField.remove(0, textField.getLength());
			textField.insertString(textField.getLength(), text, whiteStyle);
		} catch (BadLocationException e) {
			LOGGER.warning(e.getMessage());
		}
	}
	
	protected void setFontSize(int fontSize) {
		LOGGER.info("Font set: " + fontSize);
		Font font = new Font(FONT_NAME, Font.PLAIN, fontSize);
		pane.setFont(font);
		Rectangle2D r2d = getGraphics().getFontMetrics(font).getStringBounds(" ", getGraphics());
		fontWidth = (int) r2d.getWidth();
		fontHeight = (int) r2d.getHeight();
		
	}
	
	protected int getFontWidth() {
		return fontWidth;
	}
	
	protected int getFontHeight() {
		return fontHeight;
	}
}
