package com.passm.view.console.content;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.passm.view.console.Action;

public class InputListener implements KeyListener {
	
	private final static Logger LOGGER = Logger.getLogger(InputListener.class.getName());

	private final static char BACKSPACE = 8;
	public final static char ENTER = 10;
	public final static char ESCAPE = 27;
	private final static char DELETE = 127;
	private final static String PLACEHOLDER_WHILE_HIDING = "*";
	private final static int ARROW_CHAR_VALUE = 65535;
	private final static int ARROW_UP_CODE = 38;
	private final static int ARROW_DOWN_CODE = 40;
	private final static int ARROW_LEFT_CODE = 37;
	private final static int ARROW_RIGHT_CODE = 39;
	public final static char ARROW_UP_CHAR_CODE = 1;
	public final static char ARROW_DOWN_CHAR_CODE = 2;
	public final static char ARROW_LEFT_CHAR_CODE = 3;
	public final static char ARROW_RIGHT_CHAR_CODE = 4;
	
	private final Map<Integer, Character> ARROW_CODE_TO_CHAR_CODE_MAP; 

	private boolean listening;
	private boolean hideInput;
	private StringBuilder line;
	private StringBuilder consoleBufferedText;
	private SwingConsole console;
	Function<String, Boolean> conditionToReturnInput;

	private Map<Character, List<Action>> registeredActions;
	private Map<Character, Boolean> activeInputs;
	private boolean inputCaseSensitive;
	
	private List<Action> actions;

	protected InputListener(StringBuilder consoleBufferedText, SwingConsole console) {
		this.console = console;
		diableListening();
		line = new StringBuilder();
		actions = new LinkedList<>();
		this.consoleBufferedText = consoleBufferedText;
		inputCaseSensitive = false;
		ARROW_CODE_TO_CHAR_CODE_MAP = new HashMap<>();
		ARROW_CODE_TO_CHAR_CODE_MAP.put(ARROW_UP_CODE, ARROW_UP_CHAR_CODE);
		ARROW_CODE_TO_CHAR_CODE_MAP.put(ARROW_DOWN_CODE, ARROW_DOWN_CHAR_CODE);
		ARROW_CODE_TO_CHAR_CODE_MAP.put(ARROW_LEFT_CODE, ARROW_LEFT_CHAR_CODE);
		ARROW_CODE_TO_CHAR_CODE_MAP.put(ARROW_RIGHT_CODE, ARROW_RIGHT_CHAR_CODE);
		LOGGER.info("InputListener inicialized");
	}

	protected void enableListening(Function<String, Boolean> conditionToReturnInput, boolean hide) {
		this.conditionToReturnInput = conditionToReturnInput;
		hideInput = hide;
		listening = true;
		LOGGER.info("Listening enabled");
	}

	protected void diableListening() {
		listening = false;
		LOGGER.info("Listening disabled");
	}

	protected StringBuilder getLineObject() {
		return line;
	}
	
	protected List<Action> getActions(){
		return actions;
	}

	protected String getLine() {
		return line.toString();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char input = e.getKeyChar();
		boolean inputProcessed = false;
		synchronized (line) {
			if (listening) {
				inputProcessed = processInput(input);
				if (conditionToReturnInput.apply(line.toString())) {
					line.notifyAll();
				}
			}
		}
		if (!listening || !inputProcessed) {
			synchronized (actions) {
				processActions(input);
				if(!actions.isEmpty()) {
					actions.notifyAll();
				}
			}
		}
	}

	private boolean processInput(char input) {
		LOGGER.info("Procesing input");
		boolean inputProcessed = false;
		if (input == BACKSPACE) {
			removeLastCharacter();
			inputProcessed = true;
		} else if (input != DELETE && input != ESCAPE) {
			line.append(input);
			inputProcessed = true;
		}
		fillConsoleBufferedText();
		return inputProcessed;
	}

	private void processActions(char input) {
		LOGGER.info("Procesing action: " + input);
		actions.addAll(activeInputs.keySet().stream()
				.filter(activator -> shouldInputTrigger(activator, input) && activeInputs.get(activator))
				.map(activator -> registeredActions.get(activator))
				.flatMap(list -> list.stream())
				.collect(Collectors.toList()));
	}

	private boolean shouldInputTrigger(char activator, char input) {
		return inputCaseSensitive ? activator == input
				: Character.toLowerCase(activator) == Character.toLowerCase(input);
	}

	private void removeLastCharacter() {
		if (line.length() > 0) {
			line.deleteCharAt(line.length() - 1);
		}
	}

	private void fillConsoleBufferedText() {
		String lineAsString = line.toString();
		if (line.length() > 0 && line.charAt(line.length() - 1) == ENTER) {
			lineAsString = lineAsString.substring(0, lineAsString.length() - 1);
		}
		consoleBufferedText.setLength(0);
		if (hideInput) {
			consoleBufferedText
					.append(String.join("", Collections.nCopies(lineAsString.length(), PLACEHOLDER_WHILE_HIDING)));
		} else {
			consoleBufferedText.append(line);
		}
		console.refresh();
	}

	protected void registerAction(char activator, Action action) {
		LOGGER.info("Register action: " + activator);
		if (registeredActions.containsKey(activator)) {
			registeredActions.get(activator).add(action);
		}
		List<Action> listOfActions = new LinkedList<>();
		listOfActions.add(action);
		registeredActions.put(activator, listOfActions);
		enableAction(activator);
	}

	protected void enableAction(char activator) {
		activeInputs.put(activator, Boolean.TRUE);
		LOGGER.info("Action enabled: " + activator);
	}

	protected void disableAction(char activator) {
		activeInputs.put(activator, Boolean.FALSE);
		LOGGER.info("Action disabled: " + activator);
	}

	protected void enableAllActions() {
		activeInputs.keySet().forEach(action -> activeInputs.put(action, Boolean.TRUE));
		LOGGER.info("All action enabled");
	}

	protected void disableAllActions() {
		activeInputs.keySet().forEach(action -> activeInputs.put(action, Boolean.FALSE));
		LOGGER.info("All action disabled");
	}

	protected void setCaseSensitive(boolean caseSensitive) {
		inputCaseSensitive = caseSensitive;
	}

	protected void clearActions() {
		registeredActions = new HashMap<>();
		activeInputs = new HashMap<>();
		LOGGER.info("All action cleared");
	}

	protected void clearActions(char activator) {
		registeredActions.remove(activator);
		activeInputs.remove(activator);
		LOGGER.info("Action cleared: " + activator);
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyChar() == ARROW_CHAR_VALUE && ARROW_CODE_TO_CHAR_CODE_MAP.containsKey(e.getKeyCode()) && !listening) {
			processActions(ARROW_CODE_TO_CHAR_CODE_MAP.get(e.getKeyCode()));
		}
	}

}
