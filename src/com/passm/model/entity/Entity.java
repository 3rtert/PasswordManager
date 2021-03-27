package com.passm.model.entity;

import java.sql.SQLException;
import java.sql.Statement;

import com.passm.model.database.tables.DatabaseTable;

public abstract class Entity {
	
	private int id;
	
	public Entity() {
		setId(0);
	}
	
	public Entity(int id) {
		setId(id);
	}

	abstract public boolean load(Statement statement);
	
	public boolean exist(Statement statement) {
		try {
			return getDatabaseTable().exist(statement, getId());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
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
				int newId = table.insert(statement, fields);
				setId(newId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	abstract public boolean validate();
}
