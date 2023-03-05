package com.passm.view;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Logger;

public class ClipboardUpdater {
	
	private final static Logger LOGGER = Logger.getLogger(ClipboardUpdater.class.getName());
	
	public void update(String newClipboardValue) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection data = new StringSelection(newClipboardValue);
		clipboard.setContents(data, data);
		LOGGER.info("Clipboard updated");
	}
	
	public void clear(String oldClipboardValue) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		try {
			String currentValue = (String) clipboard.getData(DataFlavor.stringFlavor);
			if(oldClipboardValue.equals(currentValue)) {
				StringSelection data = new StringSelection("");
				clipboard.setContents(data, data);
				LOGGER.info("Clipboard cleared");
			}
		} catch (UnsupportedFlavorException | IOException e) {
			LOGGER.warning(e.getMessage());
		}
	}
}
