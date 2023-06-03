package com.passm.view.menu;

import java.util.Random;
import java.util.logging.Logger;

import com.passm.view.ClipboardUpdater;
import com.passm.view.menu.selector.Selectable;

public class UnselectorThread extends Thread {

	private final static Logger LOGGER = Logger.getLogger(UnselectorThread.class.getName());
	private static ClipboardUpdater clipboardUpdater;
	
	private int ID = new Random().nextInt();
	
	private long waitFrom;
	private long waitUntil;
	private String oldValue;
	private Selectable selectable;
	private boolean isCanceled;
	
	public UnselectorThread(long waitUntil, String oldValue, Selectable selectable) {
		this.waitFrom = System.currentTimeMillis();
		this.waitUntil = waitUntil;
		this.oldValue = oldValue;
		this.selectable = selectable;
		clipboardUpdater = new ClipboardUpdater();
		isCanceled = false;
	}
	
	public void extendDelay(long waitUntil, String oldValue) {
		this.oldValue = oldValue;
		extendDelay(waitUntil);
		isCanceled = false;
	}
	
	public void extendDelay(long waitUntil) {
		this.waitUntil = waitUntil;
	}
	
	public void cancel() {
		isCanceled = true;
		clipboardUpdater.clear(oldValue);
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
		if(!isCanceled) {
			clipboardUpdater.clear(oldValue);
			selectable.select();
			LOGGER.info("Unselected after " + (waitUntil - waitFrom) + "ms in thread: " + ID);
		}
		else {
			LOGGER.info("Canceled after " + (waitUntil - waitFrom) + "ms in thread: " + ID);
		}
	}
}
