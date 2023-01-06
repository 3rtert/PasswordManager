package com.passm.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements AutoCloseable {
	
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
		connection = DriverManager.getConnection(DatabaseCreator.getDatabaseUrl());
		connection.setAutoCommit(false);
		return connection;
	}

	@Override
	public void close() throws SQLException {
		connection.close();
	}
}
