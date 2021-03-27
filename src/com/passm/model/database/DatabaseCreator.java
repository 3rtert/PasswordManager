package com.passm.model.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.passm.model.database.tables.DatabaseTable;
import com.passm.model.database.tables.PasswordTable;
import com.passm.model.database.tables.UserTable;

public class DatabaseCreator {
	
	private final static String DATABASE_URL = "jdbc:sqlite:passman.db";

	List<DatabaseTable> tables = List.of(
				new PasswordTable(),
				new UserTable()
			);
			
	public static String getDatabaseUrl() {
		return DATABASE_URL;
	}
	
	public void createDatabase() {
		try(DatabaseConnection databaseConnection = new DatabaseConnection()) {
			createTables(databaseConnection.createConnection());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void createTables(Connection connection) {
		for(DatabaseTable table : tables) {
			try (Statement statement = connection.createStatement()){
				table.createTable(statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
