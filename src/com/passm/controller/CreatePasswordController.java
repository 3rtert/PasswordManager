package com.passm.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.passm.model.bo.Password;
import com.passm.model.bo.User;
import com.passm.model.config.Configuration;
import com.passm.model.database.DatabaseConnection;
import com.passm.view.CreatePasswordView;

public class CreatePasswordController extends ConsoleController<CreatePasswordController, CreatePasswordView> {
	
	private final static Logger LOGGER = Logger.getLogger(CreatePasswordController.class.getName());
	
	public CreatePasswordController(CreatePasswordView createPasswordView, ConsoleController<?,?> previousController, Configuration configuration) {
		super(previousController, createPasswordView, configuration);
	}
	
	public void createNewPassword(String login, String password, String name) {
		LOGGER.info("Startinging creating new password");
		try(DatabaseConnection databaseConnection = new DatabaseConnection(configuration)) {
			Connection connection = databaseConnection.createEncryptedConnection();
			Password passwordBO = Password.createObject(password, login, name);
			User mainUserBO = User.createObject(1);
			mainUserBO.load(connection);
			mainUserBO.addPassword(passwordBO);
			passwordBO.update(connection);
			mainUserBO.update(connection);
			connection.commit();
			LOGGER.info("Password created successfully");
		} catch (SQLException e) {
			LOGGER.warning(e.getMessage());
		}
		startPreviousView(view.getConsole(), view);
	}
	
	@Override
	public CreatePasswordController getInstance() {
		return this;
	}

}
