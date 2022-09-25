package com.passm.view.console.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.passm.view.console.content.InputListener;

public class ConsolePanel extends JPanel {

	private static final long serialVersionUID = -7669891546520723020L;
	
	private static final String FONT_TYPE = "Lucida Console";
	private static final int FONT_SIZE = 16;
	
	private final JTextPane pane;
	private final StyledDocument textField;
	private final Style whiteStyle;
	
	protected ConsolePanel(InputListener inputListener) {
		super();
		setLayout(new BorderLayout());
		pane = new JTextPane();
		pane.addKeyListener(inputListener);
		pane.setEditable(false);
		pane.setBackground(Color.BLACK);
		pane.setFont(new Font(FONT_TYPE, Font.PLAIN, FONT_SIZE));
		textField = pane.getStyledDocument();
		whiteStyle = pane.addStyle("", null);
		StyleConstants.setForeground(whiteStyle, Color.WHITE);
		add(pane);
	}
	
	protected void setText(String text) {
		try {
			textField.remove(0, textField.getLength());
			textField.insertString(textField.getLength(), text, whiteStyle);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

}
