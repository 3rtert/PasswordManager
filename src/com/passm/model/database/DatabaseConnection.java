package com.passm.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements AutoCloseable {
	
	private final static String DATABASE_URL = "jdbc:sqlite:passman.db";
	
	private Connection connection;
	
	public Connection createConnection() throws SQLException {
		if(connection != null && !connection.isClosed()) {
			connection.close();
		}
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		connection = DriverManager.getConnection(DATABASE_URL);
		connection.setAutoCommit(false);
		return connection;
	}

	@Override
	public void close() throws Exception {
		connection.close();
	}
	
	public static String getDatabaseUrl() {
		return DATABASE_URL;
	}
}
