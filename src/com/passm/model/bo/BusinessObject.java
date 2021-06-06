package com.passm.model.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.passm.model.database.DatabaseConnection;
import com.passm.model.entity.Entity;

public abstract class BusinessObject {
	
	abstract protected Entity getEntity();
	
	abstract public boolean load();
	
	abstract public boolean update();
	
	abstract public boolean delete();
	
	public boolean exist() throws SQLException {
		boolean result = false;
		try(DatabaseConnection databaseConnection = new DatabaseConnection()) {
			Connection connection = databaseConnection.createConnection();
			Statement statement = connection.createStatement();
			result = getEntity().exist(statement);
			connection.commit();
		}
		return result;
	}
}
