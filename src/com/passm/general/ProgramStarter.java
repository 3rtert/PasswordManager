package com.passm.general;

import com.passm.model.database.DatabaseCreator;

public class ProgramStarter {

	public static void main(String[] args) {
		DatabaseCreator databaseCreator = new DatabaseCreator();
		databaseCreator.createDatabase();
	}
}
