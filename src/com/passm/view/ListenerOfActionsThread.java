package com.passm.view;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import com.passm.view.console.Action;
import com.passm.view.console.Console;

public class ListenerOfActionsThread extends Thread {
	
	private final static Logger LOGGER = Logger.getLogger(ListenerOfActionsThread.class.getName());
	
	private final int ID = new Random().nextInt();
	
	private final Console console;
	
	public ListenerOfActionsThread(Console console) {
		this.console = console;
		LOGGER.info("Listener thread created: " + ID);
	}
	
	@Override
	public void run() {
		LOGGER.info("Listener thread started: " + ID);
		boolean isRunning = true;
		List<Action> actions = console.getActions();
		while(isRunning) {
			try {
				synchronized (console.getActions()) {
					actions.wait();
					LOGGER.info("Action detected: " + ID);
					if(!actions.isEmpty()) {
						performActionsInThread(actions);
					}
				}
			} catch (InterruptedException e) {
				isRunning = false;
				LOGGER.info("Listener thread stopped: " + ID);
			}
		}
	}
	
	private void performActionsInThread(List<Action> actions) {
		while(!actions.isEmpty()) {
			Action action = actions.remove(0);
			PerformerOfActionsThread performerOfActionsThread = new PerformerOfActionsThread(action);
			performerOfActionsThread.start();
		}
	}

	private class PerformerOfActionsThread extends Thread {
		
		private final int ID = new Random().nextInt();
		
		private final Action action;
		
		private PerformerOfActionsThread(Action action) {
			this.action = action;
		}
		
		@Override
		public void run() {
			LOGGER.info("Perform action in thread: " + ID);
			action.activate(console);
		}
	}
}
