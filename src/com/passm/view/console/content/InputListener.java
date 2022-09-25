package com.passm.view.console.content;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.function.Function;

public class InputListener implements KeyListener {

	private final static int BACKSPACE = 8;
	public final static int ENTER = 10;
	private final static int DELETE = 127;
	private final static String PLACEHOLDER_WHILE_HIDING = "*";
	
	private boolean listening;
	private boolean hideInput;
	private StringBuilder line;
	private StringBuilder consoleBufferedText;
	private SwingConsole console;
	Function<String, Boolean> conditionToReturnInput;
	
	protected InputListener(StringBuilder consoleBufferedText, SwingConsole console) {
		this.console = console;
		diableListening();
		line = new StringBuilder();
		this.consoleBufferedText = consoleBufferedText;
	}
	
	protected void enableListening(Function<String, Boolean> conditionToReturnInput, boolean hide) {
		this.conditionToReturnInput = conditionToReturnInput;
		hideInput = hide;
		listening = true;
	}
	
	protected void diableListening() {
		listening = false;
	}
	
	protected StringBuilder getLineObject() {
		return line;
	}
	
	protected String getLine() {
		return line.toString();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		synchronized (line) {
			char input = e.getKeyChar();
			processInput(input);
			if(conditionToReturnInput.apply(line.toString())) {
				line.notify();
			}
		}
		
	}
	
	private void processInput(char input) {
		if(listening && input != DELETE) {
			if(input == BACKSPACE) {
				removeLastCharacter();
			}
			else {
				line.append(input);
			}
			fillConsoleBufferedText();
		}
	}
	
	private void removeLastCharacter() {
		if(line.length() > 0) {
			line.deleteCharAt(line.length() - 1);
		}
	}
	
	private void fillConsoleBufferedText() {
		String lineAsString = line.toString();
		if(line.length() > 0 && line.charAt(line.length() - 1) == ENTER) {
			lineAsString = lineAsString.substring(0, lineAsString.length() - 1);
		}
		consoleBufferedText.setLength(0);
		if(hideInput) {
			consoleBufferedText.append(String.join("", Collections.nCopies(lineAsString.length(), PLACEHOLDER_WHILE_HIDING)));
		}
		else {
			consoleBufferedText.append(line);
		}
		console.refreshConsoleText();
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

}
