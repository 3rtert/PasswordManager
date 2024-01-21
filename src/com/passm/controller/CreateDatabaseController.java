package com.passm.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.passm.model.bo.Password;
import com.passm.model.bo.User;
import com.passm.model.config.Configuration;
import com.passm.model.crypt.DatabaseEncrypterDecrypter;
import com.passm.model.database.DatabaseConnection;
import com.passm.model.database.DatabaseCreator;
import com.passm.view.menu.login.CreateDatabaseView;

public class CreateDatabaseController extends ConsoleController<CreateDatabaseController, CreateDatabaseView> {

	private final static Logger LOGGER = Logger.getLogger(CreateDatabaseController.class.getName());
	
	private final static String MAIN_ACCOUNT_DEFAULT_NAME = "Admin";
	
	private final boolean databaseAlreadyExist;
	
	public CreateDatabaseController(ConsoleController<?,?> previousController, CreateDatabaseView createDatabaseView, Configuration configuration, boolean databaseAlreadyExist) {
		super(previousController, createDatabaseView, configuration);
		this.databaseAlreadyExist = databaseAlreadyExist;
	}
	
	public void prepareDatabase(String newPassword) {
		if(databaseAlreadyExist) {
			updateDatabasePassword(newPassword);
		} else {
			createDatabasePassword(newPassword);
		}
		previousController.run();
	}
	
	private void createDatabasePassword(String newPassword) {
		LOGGER.info("Starting creating initial database");
		DatabaseCreator databaseCreator = new DatabaseCreator();
		boolean createdSuccessfully = databaseCreator.createDatabase(newPassword);
		configuration.setDatabasePassword(newPassword);
		createdSuccessfully = createdSuccessfully && prepareMainAccount();
		if(createdSuccessfully) {
			LOGGER.info("Initial database created");
		} else {
			LOGGER.warning("Creating initial database failed");
		}
	}
	
	private void updateDatabasePassword(String newPassword) {
		LOGGER.info("Starting updating database password");
		String oldPassword = configuration.getDatabasePassword();
		DatabaseEncrypterDecrypter databaseDecrypter = new DatabaseEncrypterDecrypter(oldPassword.toCharArray());
		DatabaseEncrypterDecrypter databaseEncrypter = new DatabaseEncrypterDecrypter(newPassword.toCharArray());
		configuration.setDatabasePassword(newPassword);
		boolean createdSuccessfully = databaseDecrypter.decrypt();
		createdSuccessfully = createdSuccessfully && databaseEncrypter.encrypt();
		createdSuccessfully = createdSuccessfully && prepareMainAccount();
		if(createdSuccessfully) {
			LOGGER.info("Main password updated");
		} else {
			LOGGER.warning("Updating database password failed");
		}
	}

	private boolean prepareMainAccount() {
		boolean createdCorrectly;
		try(DatabaseConnection databaseConnection = new DatabaseConnection(configuration)) {
			Connection connection = databaseConnection.createEncryptedConnection();
			if(!mainAccountAlreadyExist(connection)) {
				createdCorrectly = createMainAccount(connection);
			} else {
				createdCorrectly = updatePasswordForMainAccount(connection);
			}
			connection.commit();
		} catch (SQLException e) {
			LOGGER.warning(e.getMessage());
			createdCorrectly = false;
		}
		return createdCorrectly;
	}
	
	private boolean mainAccountAlreadyExist(Connection connection) {
		User mainUser = User.createObject(1);
		return mainUser.exist(connection);
	}
	
	private boolean createMainAccount(Connection connection) throws SQLException {
		Password password = Password.createObject(configuration.getDatabasePassword(), MAIN_ACCOUNT_DEFAULT_NAME, "");
		boolean createdCorrectly = password.update(connection);
		User mainUser = User.createObject(MAIN_ACCOUNT_DEFAULT_NAME, password);
		createdCorrectly &= mainUser.update(connection);
		return createdCorrectly;
	}
	
	private boolean updatePasswordForMainAccount(Connection connection) throws SQLException {
		User mainUser = User.createObject(1);
		boolean createdCorrectly = mainUser.load(connection);
		mainUser.getMainPassword().setPassword(configuration.getDatabasePassword());
		createdCorrectly &= mainUser.update(connection);
		return createdCorrectly;
	}

	@Override
	public CreateDatabaseController getInstance() {
		return this;
	}
}
