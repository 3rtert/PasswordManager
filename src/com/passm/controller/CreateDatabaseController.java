package com.passm.controller;

import java.util.logging.Logger;

import com.passm.model.database.DatabaseCreator;
import com.passm.view.menu.login.CreateDatabaseView;

public class CreateDatabaseController extends Controller {

	private final static Logger LOGGER = Logger.getLogger(CreateDatabaseController.class.getName());
	
	CreateDatabaseView createDatabaseView;
	
	public CreateDatabaseController(CreateDatabaseView createDatabaseView) {
		super(null);
		this.createDatabaseView = createDatabaseView;
	}

	@Override
	public void run() {
		createDatabaseView.setController(this);
		createDatabaseView.init();
	}

	public void createDatabase(String password) {
		LOGGER.info("Starting creating initial database");
		DatabaseCreator databaseCreator = new DatabaseCreator();
		boolean createdSuccessfully = databaseCreator.createDatabase(password);
		if(createdSuccessfully) {
			LOGGER.info("Initial database created");
		} else {
			LOGGER.warning("Creating initial database failed");
		}
	}
}
