package com.passm.view.menu;

import java.util.logging.Logger;

import com.passm.view.ClipboardUpdater;
import com.passm.view.menu.selector.Selectable;

public class UnselectorThread extends Thread {

	private final static Logger LOGGER = Logger.getLogger(UnselectorThread.class.getName());
	private static ClipboardUpdater clipboardUpdater;
	
	private double ID = Math.random();
	
	private long waitFrom;
	private long waitUntil;
	private String oldValue;
	private Selectable selectable;
	
	public UnselectorThread(long waitUntil, String oldValue, Selectable selectable) {
		this.waitFrom = System.currentTimeMillis();
		this.waitUntil = waitUntil;
		this.oldValue = oldValue;
		this.selectable = selectable;
		clipboardUpdater = new ClipboardUpdater();
	}
	
	public void setWaitUntil(long waitUntil) {
		this.waitUntil = waitUntil;
	}
	
	@Override
	public void run() {
		while(waitUntil > System.currentTimeMillis()) {
			long delay = waitUntil - System.currentTimeMillis();
			LOGGER.info("Clear clipboard in " + delay + "ms in thread: " + ID);
			try {
				sleep(delay);
			} catch (InterruptedException e) {
				LOGGER.warning(e.getMessage());
			}
		}
		clipboardUpdater.clear(oldValue);
		selectable.select();
		LOGGER.info("Clipboard cleared after " + (waitUntil - waitFrom) + "in thread: " + ID);
	}
}