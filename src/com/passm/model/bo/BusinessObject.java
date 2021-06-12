package com.passm.model.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.passm.model.database.DatabaseConnection;
import com.passm.model.entity.Entity;

public abstract class BusinessObject {
	
	abstract protected Entity getEntity();
	
	public boolean load() {
		boolean result = false;
		try(DatabaseConnection databaseConnection = new DatabaseConnection()) {
			Connection connection = databaseConnection.createConnection();
			result = load(connection);
			connection.commit();
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}
	
	abstract public boolean load(Connection connection) throws SQLException;
	
	public boolean update() {
		boolean result = false;
		try(DatabaseConnection databaseConnection = new DatabaseConnection()) {
			Connection connection = databaseConnection.createConnection();
			result = update(connection);
			connection.commit();
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}
	
	abstract public boolean update(Connection connection) throws SQLException;
	
	public boolean delete() {
		boolean result = false;
		try(DatabaseConnection databaseConnection = new DatabaseConnection()) {
			Connection connection = databaseConnection.createConnection();
			result = delete(connection);
			connection.commit();
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}
	
	abstract public boolean delete(Connection connection) throws SQLException;
	
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
	
	public int getId() {
		return getEntity().getId();
	}
}
