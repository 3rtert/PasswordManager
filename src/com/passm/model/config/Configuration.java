package com.passm.model.config;

public class Configuration {

	private String databasePassword;
	private boolean hidePasswordOnPasswordList = true;
	private long clipboardClearingDelay = 10000;
	
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}
	
	public String getDatabasePassword() {
		return databasePassword;
	}

	public boolean isHidePasswordOnPasswordList() {
		return hidePasswordOnPasswordList;
	}

	public void setHidePasswordOnPasswordList(boolean hidePasswordOnPasswordList) {
		this.hidePasswordOnPasswordList = hidePasswordOnPasswordList;
	}

	public long getClipboardClearingDelay() {
		return clipboardClearingDelay;
	}

	public void setClipboardClearingDelay(long clipboardClearingDelay) {
		this.clipboardClearingDelay = clipboardClearingDelay;
	}
}
