package com.passm.view.console;

import java.awt.Image;

public interface Console {

	public void setIconImage(Image image);
	public void setName(String name);
	public void print(String text);
	public void println(String text);
	public void print(String text, boolean newLine);
	public void ln();
	public void clear();
	public String readLine();
	public String readLine(boolean hide);
	public char read();
	public void registerAction(char activator, Action action);
	public void enableAction(char activator);
	public void disableAction(char activator);
	public void enableAllActions();
	public void disableAllActions();

}
