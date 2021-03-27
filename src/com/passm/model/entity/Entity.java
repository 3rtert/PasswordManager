package com.passm.model.entity;

import java.sql.SQLException;
import java.sql.Statement;

import com.passm.model.database.tables.DatabaseTable;

public abstract class Entity {

	abstract public boolean load(Statement statement);
	
	public boolean update(Statement statement) {
		if(!validate()) {
			return false;
		}
		DatabaseTable table = getDatabaseTable();
		int id = getId();
		Object[] fields = getFields();
		try {
			if(table.exist(statement, id)) {
				table.update(statement, id, fields);
			} else {
				table.insert(statement, fields);
			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	public boolean delete(Statement statement) {
		DatabaseTable table = getDatabaseTable();
		int id = getId();
		try {
			if(!table.exist(statement, id)) {
				return false;
			}
			table.delete(statement, id);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	abstract protected DatabaseTable getDatabaseTable();
	
	abstract protected Object[] getFields();
	
	abstract protected int getId();
	
	abstract public boolean validate();
}
