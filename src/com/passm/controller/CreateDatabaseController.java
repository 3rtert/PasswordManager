package com.passm.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.passm.model.bo.Password;
import com.passm.model.bo.User;
import com.passm.model.config.Configuration;
import com.passm.model.database.DatabaseConnection;
import com.passm.model.database.DatabaseCreator;
import com.passm.view.menu.login.CreateDatabaseView;

public class CreateDatabaseController extends ConsoleController<CreateDatabaseController, CreateDatabaseView> {

	private final static Logger LOGGER = Logger.getLogger(CreateDatabaseController.class.getName());
	
	private final static String MAIN_ACCOUNT_DEFAULT_NAME = "Admin";
	
	public CreateDatabaseController(CreateDatabaseView createDatabaseView, Configuration configuration) {
		super(null, createDatabaseView, configuration);
	}
	public void createDatabase(String password) {
		LOGGER.info("Starting creating initial database");
		DatabaseCreator databaseCreator = new DatabaseCreator();
		boolean createdSuccessfully = databaseCreator.createDatabase(password);
		configuration.setDatabasePassword(password);
		createdSuccessfully = createdSuccessfully && createMainAccount();
		if(createdSuccessfully) {
			LOGGER.info("Initial database created");
		} else {
			LOGGER.warning("Creating initial database failed");
		}
	}

	private boolean createMainAccount() {
		boolean createdCorrectly = false;
		try(DatabaseConnection databaseConnection = new DatabaseConnection(configuration)) {
			Connection connection = databaseConnection.createEncryptedConnection();
			Password password = Password.createObject(configuration.getDatabasePassword(), MAIN_ACCOUNT_DEFAULT_NAME, "");
			createdCorrectly = password.update(connection);
			User mainUser = User.createObject(MAIN_ACCOUNT_DEFAULT_NAME, password);
			createdCorrectly = mainUser.update(connection);
			connection.commit();
		} catch (SQLException e) {
			LOGGER.warning(e.getMessage());
			createdCorrectly = false;
		}
		return createdCorrectly;
	}
	@Override
	public CreateDatabaseController getInstance() {
		return this;
	}
}
