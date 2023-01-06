package com.passm.model.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.passm.model.crypt.DatabaseEncrypterDecrypter;

public class EncryptedDatabaseConnection extends DatabaseConnection {
	
	DatabaseEncrypterDecrypter databaseEncrypterDecrypter;

	public EncryptedDatabaseConnection(char[] password) {
		databaseEncrypterDecrypter = new DatabaseEncrypterDecrypter(password);
	}
	
	@Override
	public Connection createConnection() throws SQLException {
		databaseEncrypterDecrypter.decrypt();
		return super.createConnection();
	}

	@Override
	public void close() throws SQLException {
		databaseEncrypterDecrypter.encrypt();
		super.close();
	}
}
