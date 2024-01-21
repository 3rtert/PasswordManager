package com.passm.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.passm.controller.confirmation.ConfirmationController;
import com.passm.model.bo.Password;
import com.passm.model.bo.User;
import com.passm.model.config.Configuration;
import com.passm.model.database.DatabaseConnection;
import com.passm.view.RemovePasswordView;
import com.passm.view.confirmation.ConfirmationView;

public class RemovePasswordController extends ConsoleController<RemovePasswordController, RemovePasswordView> implements Recipient {

	private final static Logger LOGGER = Logger.getLogger(RemovePasswordController.class.getName());
	
	private final static String CONFIRMATION_MESSAGE = " Do you want to remove password %s? ";
	
	private List<Password> passwords;
	private int indexPasswordToRemove;
	
	public RemovePasswordController(ConsoleController<?, ?> previousController, RemovePasswordView view,
			Configuration configuration) {
		super(previousController, view, configuration);
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

	@Override
	public void receive(Object object) {
		boolean confirmed = (boolean) object;
		if(confirmed && indexPasswordToRemove > 0) {
			boolean result = removePassword(indexPasswordToRemove);
			if(result) {
				passwords.remove(indexPasswordToRemove);
				indexPasswordToRemove = 0;
			}
		}
		view.reset();
		view.init();
	}
	
	public void askConfirmation(String passwordName, int index) {
		indexPasswordToRemove = index;
		view.reset();
		String message = String.format(CONFIRMATION_MESSAGE, passwordName);
		ConfirmationView confirmationView = new ConfirmationView(view.getConsole(), message);
		ConfirmationController confirmationController = new ConfirmationController(this, confirmationView, configuration, this);
		confirmationController.run();
	}
	
	private boolean removePassword(int index) {
		LOGGER.info("Starting removing password: '" + passwords.get(index).getName() + "' id: " + passwords.get(index).getId());
		boolean result;
		try(DatabaseConnection databaseConnection = new DatabaseConnection(configuration)) {
			Connection connection = databaseConnection.createEncryptedConnection();
			Password passwordBO = Password.createObject(passwords.get(index).getId());
			result = passwordBO.delete();
			if(result) {
				connection.commit();
				LOGGER.info("Password '" + passwords.get(index).getName() + "' removed");
			} else {
				LOGGER.warning("Password not removed");
			}
		} catch (SQLException e) {
			result = false;
			LOGGER.warning(e.getMessage());
		}
		return result;
	}

	@Override
	public RemovePasswordController getInstance() {
		return this;
	}

}
