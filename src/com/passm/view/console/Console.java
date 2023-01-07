package com.passm.view.console;

import java.awt.Image;
import java.util.List;

public interface Console {

	public void setIconImage(Image image);
	public void setName(String name);
	public void print(String text);
	public void println(String text, boolean withRefresh);
	public void println(String text);
	public void print(String text, boolean newLine);
	public void print(String text, boolean newLine, boolean withRefresh);
	public void ln();
	public void ln(boolean withRefresh);
	public void refresh();
	public void clear(boolean withRefresh);
	public void clear();
	public String readLine();
	public String readLine(boolean hide);
	public char read();
	public void registerAction(char activator, Action action);
	public void clearActions();
	public void clearActions(char activator);
	public void enableAction(char activator);
	public void disableAction(char activator);
	public void enableAllActions();
	public void disableAllActions();
	public List<Action> getActions();
	public void setCaseSensitive(boolean caseSensitive);
	public void setSize(int width, int height);
	public int getHeight();
	public int getWidth();
	public int getHeightInCharacters();
	public int getWidthInCharacters();
	public void setSizeInCharacters(int width, int height);
	public void setLocation(int x, int y);
	public void setFontSize(int fontSize);
	public void stop();
	public void diableListening();

}
