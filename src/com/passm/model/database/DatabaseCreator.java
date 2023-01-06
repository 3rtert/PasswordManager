package com.passm.model.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

import com.passm.model.bo.Password;
import com.passm.model.bo.User;
import com.passm.model.crypt.DatabaseEncrypterDecrypter;
import com.passm.model.database.tables.DatabaseTable;
import com.passm.model.database.tables.PasswordAssignmentTable;
import com.passm.model.database.tables.PasswordTable;
import com.passm.model.database.tables.UserTable;

public class DatabaseCreator {
	
	private final static Logger LOGGER = Logger.getLogger(DatabaseCreator.class.getName());
	
	private final static String DATABASE_NAME = "passman.db";
	private final static String DATABASE_URL = "jdbc:sqlite:" + DATABASE_NAME;

	List<DatabaseTable> tables = List.of(
				new PasswordTable(),
				new UserTable(),
				new PasswordAssignmentTable()
			);
			
	public static String getDatabaseUrl() {
		return DATABASE_URL;
	}
	
	public static String getDatabaseName() {
		return DATABASE_NAME;
	}
	
	public void createDatabase() {
		try(DatabaseConnection databaseConnection = new DatabaseConnection()) {
			Connection connection = databaseConnection.createConnection();
			createTables(connection);
			connection.commit();
			
			//temporary until creation of the first password will be no completed
			connection = databaseConnection.createConnection();
			Password password = Password.createObject("pass", "Main", "MainTemporaryPassword");
			password.update();
			User admin = User.createObject("Admin", password);
			admin.update();
			connection.commit();
		} catch (Exception e) {
			LOGGER.warning(e.getMessage());
		}
	}
	
	public void createDatabase(String password) {
		createDatabase();
		DatabaseEncrypterDecrypter databaseEncrypterDecrypter = new DatabaseEncrypterDecrypter(password.toCharArray());
		databaseEncrypterDecrypter.encrypt();
	}
	
	private void createTables(Connection connection) {
		for(DatabaseTable table : tables) {
			try (Statement statement = connection.createStatement()) {
				table.createTable(statement);
			} catch (SQLException e) {
				LOGGER.warning(e.getMessage());
			}
		}
	}
}
