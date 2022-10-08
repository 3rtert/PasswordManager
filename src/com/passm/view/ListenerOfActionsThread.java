package com.passm.view;

import java.util.List;

import com.passm.view.console.Action;
import com.passm.view.console.Console;

public class ListenerOfActionsThread extends Thread {
	
	//private double random = Math.random();
	
	private final Console console;
	
	public ListenerOfActionsThread(Console console) {
		this.console = console;
	}
	
	@Override
	public void run() {
		//System.out.println("Started " + random);
		boolean isRunning = true;
		List<Action> actions = console.getActions();
		while(isRunning) {
			try {
				synchronized (actions) {
					actions.wait();
					//System.out.println("Actions: " + actions.size() + " " + random);
					if(!actions.isEmpty()) {
						performActionsInThread(actions);
					}
				}
			} catch (InterruptedException e) {
				isRunning = false;
				//System.out.println("Stoped " + random);
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
		
		Action action;
		
		private PerformerOfActionsThread(Action action) {
			this.action = action;
		}
		
		@Override
		public void run() {
			action.activate(console);
		}
	}
}
