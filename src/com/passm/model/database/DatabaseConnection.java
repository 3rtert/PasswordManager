package com.passm.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.passm.model.config.Configuration;
import com.passm.model.crypt.DatabaseEncrypterDecrypter;

public class DatabaseConnection implements AutoCloseable {
	
	private Connection connection;
	
	private DatabaseEncrypterDecrypter databaseEncrypterDecrypter;
	private boolean isConnectionEncrypted;
	
	public DatabaseConnection() {}
	
	public DatabaseConnection(Configuration configuration) {
		databaseEncrypterDecrypter = new DatabaseEncrypterDecrypter(configuration.getDatabasePassword().toCharArray());
	}
	
	private Connection createConnection(boolean encrypted) throws SQLException {
		isConnectionEncrypted = encrypted;
		if(isConnectionEncrypted) {
			databaseEncrypterDecrypter.decrypt();
		}
		
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
	
	public Connection createConnection() throws SQLException {
		return createConnection(false);
	}
	
	public Connection createEncryptedConnection() throws SQLException {
		return createConnection(true);
	}

	@Override
	public void close() throws SQLException {
		connection.close();
		if(isConnectionEncrypted) {
			databaseEncrypterDecrypter.encrypt();
		}
	}
}
