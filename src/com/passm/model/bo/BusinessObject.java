package com.passm.model.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import com.passm.model.database.DatabaseConnection;
import com.passm.model.entity.Entity;

public abstract class BusinessObject {
	
	private final static Logger LOGGER = Logger.getLogger(BusinessObject.class.getName());
	
	abstract protected Entity getEntity();
	
	public boolean load() {
		boolean result = false;
		try(DatabaseConnection databaseConnection = new DatabaseConnection()) {
			Connection connection = databaseConnection.createConnection();
			result = load(connection);
		} catch(SQLException e) {
			LOGGER.warning(e.getMessage());
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
		} catch(SQLException e) {
			LOGGER.warning(e.getMessage());
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
		} catch(SQLException e) {
			LOGGER.warning(e.getMessage());
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
		}
		return result;
	}
	
	public boolean exist(Connection connection) {
		boolean result = false;
		try {
			Statement statement = connection.createStatement();
			result = getEntity().exist(statement);
		} catch(SQLException e) {
			LOGGER.warning(e.getMessage());
			return false;
		}
		return result;
	}
	
	public int getId() {
		return getEntity().getId();
	}
}
