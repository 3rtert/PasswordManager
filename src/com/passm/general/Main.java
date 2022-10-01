package com.passm.general;

import java.util.List;

import com.passm.model.bo.Password;
import com.passm.model.bo.User;
import com.passm.model.database.DatabaseCreator;
import com.passm.view.console.Action;
import com.passm.view.console.Console;
import com.passm.view.console.content.SwingConsole;
import com.passm.view.console.window.SwingConsoleFrame;

public class Main {

	public static void main(String[] args) {
		
		Console console = SwingConsole.createWithUnderscore("Password Manager");
		console.registerAction('a', new TestAction());
		console.registerAction('s', new TestAction());
		console.registerAction('d', new TestAction());
		
		console.disableAction('s');
		
		console.disableAllActions();
		
		console.enableAction('d');
		
		console.clearActions();
		
		console.enableAllActions();
		
		
		
		
		/*
		DatabaseCreator databaseCreator = new DatabaseCreator();
		databaseCreator.createDatabase();
		
		Password password = Password.createObject("test_password_123", "name_123", "this is my first test password");
		password.update();
		Password adminPassword = Password.createObject("admin123", "admin", "password for admin account");
		adminPassword.update();		
		
		User user = User.createObject("Me", password);
		user.update();
		
		System.out.println("Saved");
		
		
		User admin = User.createObject("You", adminPassword);
		//user1.load();
		//admin.setName("new Name");
		admin.update();
		
		System.out.println("Updated");
		
		
		Password password1 = Password.createObject("test_password_1234", "name_1234", "this is my second test password");
		password1.update();
		Password password2 = Password.createObject("test_password_12345", "name_12345", "this is my third test password");
		password2.update();
		
		admin.addPassword(password1);
		admin.addPassword(password2);
		admin.update();
		
		System.out.println("Passwords added");
		
		
		List<Password> passwords = admin.getPasswords();
		Password deletedPassword = passwords.remove(0);
		admin.update();
		
		System.out.println("Password unassigned");
		
		
		deletedPassword.delete();
		System.out.println("Password deleted");


		user.delete();
		System.out.println("User deleted");
		
		Password againPassword2 = Password.createObject(2);
		againPassword2.load();
		System.out.println("Description: " + againPassword2.getDescription());
		
		
		User againAdmin = User.createObject(2);
		againAdmin.load();
		System.out.println("Name: " + againAdmin.getName());
		*/
	}
}


