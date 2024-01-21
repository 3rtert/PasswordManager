package com.passm.view.console.content;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import com.passm.view.ListenerOfActionsThread;
import com.passm.view.console.Action;
import com.passm.view.console.Console;
import com.passm.view.console.window.SwingConsoleFrame;

public class SwingConsole implements Console {

	private final static Logger LOGGER = Logger.getLogger(SwingConsole.class.getName());

	private final static String NEW_LINE = "\n";
	private final static String EMPTY = "";
	private final static String UNDERSCORE = "_";
	private final static String DEFAULT_NAME = "Console";
	private final static int DEFAULT_WIDTH_IN_CHARACTERS = 80;
	private final static int DEFAULT_HEIGHT_IN_CHARACTERS = 25;
	private final static int DEFAULT_FONT_SIZE = 16;

	private final SwingConsoleFrame frame;
	private String consoleText;
	private final StringBuilder consoleBufferedText;
	private boolean underscoreAtTheEnd;
	private final boolean withScroll;
	private final InputListener inputListener;

	private final ListenerOfActionsThread listenerOfActionsThread;

	private final Function<String, Boolean> LINE_WITH_ENTER_FILLTER = currentLine -> currentLine.length() > 0
			&& (currentLine.charAt(0) == InputListener.ENTER
			|| currentLine.charAt(currentLine.length() - 1) == InputListener.ENTER);

	private final Function<String, Boolean> ANY_INPUT_FILLTER = currentLine -> currentLine.length() > 0;

	public static SwingConsole create() {
		return new SwingConsole(DEFAULT_NAME, false, false);
	}

	public static SwingConsole create(String name) {
		return new SwingConsole(name, false, false);
	}

	public static SwingConsole createWithUnderscore(String name) {
		return new SwingConsole(name, true, false);
	}

	public static SwingConsole create(String name, boolean underscoreAtTheEnd, boolean withScroll) {
		return new SwingConsole(name, underscoreAtTheEnd, withScroll);
	}

	private SwingConsole(String name, boolean underscoreAtTheEnd, boolean withScroll) {
		LOGGER.info("Initializing of SwingConsole");
		consoleBufferedText = new StringBuilder();
		inputListener = new InputListener(consoleBufferedText, this);
		frame = SwingConsoleFrame.create(name, inputListener, withScroll);
		frame.setResizable(false);
		frame.setVisible(true);
		setFontSize(DEFAULT_FONT_SIZE);
		setSizeInCharacters(DEFAULT_WIDTH_IN_CHARACTERS, DEFAULT_HEIGHT_IN_CHARACTERS);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int)((screenSize.getWidth() - frame.getWidth()) / 2), (int)((screenSize.getHeight() - frame.getHeight()) / 2));
		clear();
		clearActions();
		setUnderscoreAtTheEnd(underscoreAtTheEnd);
		this.withScroll = withScroll;
		this.listenerOfActionsThread = new ListenerOfActionsThread(this);
		listenerOfActionsThread.start();
		LOGGER.info("SwingConsole inizialized");
	}

	public void setUnderscoreAtTheEnd(boolean underscoreAtTheEnd) {
		this.underscoreAtTheEnd = underscoreAtTheEnd;
		refresh();
	}

	@Override
	public void setIconImage(Image image) {
		frame.setIconImage(image);
	}

	@Override
	public void setName(String name) {
		frame.setName(name);
	}

	@Override
	public void refresh() {
		String textToShow = consoleText + consoleBufferedText;
		if (underscoreAtTheEnd) {
			textToShow += UNDERSCORE;
		}
		frame.setText(textToShow, withScroll);
		LOGGER.info("Console refreshed");
	}

	@Override
	public void print(String text) {
		print(text, false, true);
	}

	@Override
	public void println(String text) {
		print(text, true, true);
	}

	@Override
	public void print(String text, boolean withRefresh) {
		print(text, false, withRefresh);
	}

	@Override
	public void println(String text, boolean withRefresh) {
		print(text, true, withRefresh);
	}

	@Override
	public void print(String text, boolean newLine, boolean withRefresh) {
		consoleText += text;
		if (newLine) {
			consoleText += NEW_LINE;
		}
		if(withRefresh) {
			refresh();
		}
	}

	@Override
	public void ln() {
		ln(true);
	}

	@Override
	public void ln(boolean withRefresh) {
		print(EMPTY, true, withRefresh);
	}

	@Override
	public void clear(boolean withRefresh) {
		consoleText = EMPTY;
		consoleBufferedText.setLength(0);
		if(withRefresh) {
			refresh();
		}
		LOGGER.info("Console cleared");
	}

	@Override
	public void clear() {
		clear(false);
	}

	@Override
	public String readLine() {
		return readLine(false);
	}

	@Override
	public String readLine(boolean hide) {
		String result = null;
		try {
			StringBuilder line = inputListener.getLineObject();
			synchronized (line) {
				inputListener.enableListening(LINE_WITH_ENTER_FILLTER, hide);
				line.wait();
				inputListener.diableListening();
				line.deleteCharAt(line.length() - 1);
				result = line.toString();
				line.setLength(0);
				consoleText += consoleBufferedText.toString();
				consoleBufferedText.setLength(0);
				return result;
			}
		} catch (Exception e) {
			LOGGER.warning(e.getMessage());
		}
		return result;
	}

	@Override
	public char read() {
		try {
			StringBuilder line = inputListener.getLineObject();
			synchronized (line) {
				inputListener.enableListening(ANY_INPUT_FILLTER, true);
				line.wait();
				inputListener.diableListening();
				return line.toString().charAt(0);
			}
		} catch (Exception e) {
			LOGGER.warning(e.getMessage());
		}
		return 0;
	}

	@Override
	public void registerAction(char activator, Action action) {
		inputListener.registerAction(activator, action);
	}

	@Override
	public void enableAction(char activator) {
		inputListener.enableAction(activator);
	}

	@Override
	public void disableAction(char activator) {
		inputListener.disableAction(activator);
	}

	@Override
	public void enableAllActions() {
		inputListener.enableAllActions();
	}

	@Override
	public void disableAllActions() {
		inputListener.disableAllActions();
	}

	@Override
	public void setCaseSensitive(boolean caseSensitive) {
		inputListener.setCaseSensitive(caseSensitive);
	}

	@Override
	public void clearActions() {
		inputListener.clearActions();
	}

	@Override
	public void clearActions(char activator) {
		inputListener.clearActions(activator);
	}

	@Override
	public List<Action> getActions() {
		return inputListener.getActions();
	}

	@Override
	public int getHeight() {
		return frame.getHeight();
	}

	@Override
	public int getWidth() {
		return frame.getWidth();
	}

	@Override
	public int getHeightInCharacters() {
		return frame.getHeightInCharacters();
	}

	@Override
	public int getWidthInCharacters() {
		return frame.getWidthInCharacters();
	}

	@Override
	public void setSize(int width, int height) {
		frame.setSize(width, height);
	}

	@Override
	public void setSizeInCharacters(int width, int height) {
		frame.setSizeInCharacters(width, height);
	}

	@Override
	public void setLocation(int x, int y) {
		frame.setLocation(x, y);
	}

	@Override
	public void setFontSize(int fontSize) {
		frame.setFontSize(fontSize);
	}

	@Override
	public void stop() {
		LOGGER.info("Stopping console...");
		if(listenerOfActionsThread.isAlive()) {
			listenerOfActionsThread.interrupt();
		}
		System.exit(0);
	}

	@Override
	public void diableListening() {
		inputListener.diableListening();
		inputListener.clearBuffer();
	}
}

