package com.passm.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.passm.model.bo.Password;
import com.passm.model.bo.User;
import com.passm.model.config.Configuration;
import com.passm.model.database.DatabaseConnection;
import com.passm.view.PasswordsListView;

public class PasswordsListController extends ConsoleController<PasswordsListController, PasswordsListView> {

	private final static Logger LOGGER = Logger.getLogger(PasswordsListController.class.getName());
	
	private List<Password> passwords;
	
	public PasswordsListController(ConsoleController<?, ?> previousController, PasswordsListView passwordsListview,
			Configuration configuration) {
		super(previousController, passwordsListview, configuration);
	}
	
	@Override
	public void run() {
		loadPasswordsList();
		super.run();
	}

	private void loadPasswordsList() {
		try(DatabaseConnection databaseConnection = new DatabaseConnection(configuration)) {
			Connection connection = databaseConnection.createEncryptedConnection();
			User mainUserBO = User.createObject(1);
			mainUserBO.load(connection);
			passwords = mainUserBO.getPasswords();
		} catch (SQLException e) {
			LOGGER.warning(e.getMessage());
		}
	}
	
	public List<String> getPasswordsNameList() {
		return passwords.stream().map(Password::getName).collect(Collectors.toList());
	}
	
	public String getPassword(int numberOfPassword) {
		String password = getUnhiddenPassword(numberOfPassword);
		return configuration.isHidePasswordOnPasswordList() ? "" : password;
	}
	
	public String getUnhiddenPassword(int numberOfPassword) {
		return passwords.get(numberOfPassword).getPassword();
	}
	
	public String getLogin(int numberOfPassword) {
		return passwords.get(numberOfPassword).getLogin();
	}
	
	public long getClipboardClearingDelay() {
		return configuration.getClipboardClearingDelay();
	}
	
	@Override
	public PasswordsListController getInstance() {
		return this;
	}

}
