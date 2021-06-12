package com.passm.model.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.passm.model.entity.Entity;
import com.passm.model.entity.PasswordAssignment;
import com.passm.model.entity.PasswordEntity;

public class Password extends BusinessObject {
	
	private PasswordEntity passwordEntity;

	private String password;
	private String name;
	private String description;
	
	private Password(int id, String password, String name, String description) {
		passwordEntity = PasswordEntity.createEntity(id);
		this.password = password;
		this.name = name;
		this.description = description;
	}
	
	public static Password createObject(int id, String password, String name, String description) {
		return new Password(id, password, name, description);
	}
	
	public static Password createObject(String password, String name, String description) {
		return new Password(0, password, name, description);
	}
	
	public static Password createObject(int id) {
		return new Password(id, null, null, null);
	}
	
	public static Password createObject() {
		return new Password(0, null, null, null);
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	protected Entity getEntity() {
		return passwordEntity;
	}

	@Override
	public boolean load(Connection connection) throws SQLException {
		boolean result = true;
		Statement statement = connection.createStatement();
		result = passwordEntity.load(statement);
		if(result) {
			setPassword(passwordEntity.getPassword());
			setName(passwordEntity.getName());
			setDescription(passwordEntity.getDescription());
		}
		return result;
	}

	@Override
	public boolean update(Connection connection) throws SQLException {
		boolean result = true;
		passwordEntity.setPassword(password);
		passwordEntity.setName(name);
		passwordEntity.setDescription(description);
		Statement statement = connection.createStatement();
		result = passwordEntity.update(statement);
		return result;
	}

	@Override
	public boolean delete(Connection connection) throws SQLException {
		boolean result = true;
		Statement statement = connection.createStatement();
		if(passwordEntity.exist(statement)) {
			List<PasswordAssignment> passwordAssignments = PasswordAssignment.getAssignemntsByPasswordId(statement, passwordEntity.getId());
			for (PasswordAssignment passwordAssignment : passwordAssignments) {
				result = result && passwordAssignment.delete(statement);
			}
			result = result && passwordEntity.delete(statement);
		}
		return result;
	}
}