package com.passm.model.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.passm.model.entity.Entity;
import com.passm.model.entity.PasswordAssignment;
import com.passm.model.entity.PasswordEntity;

public class Password extends BusinessObject {
	
	private final PasswordEntity passwordEntity;

	private String password;
	private String login;
	private String name;
	
	private Password(int id, String password, String login, String name) {
		passwordEntity = PasswordEntity.createEntity(id);
		this.password = password;
		this.login = login;
		this.name = name;
	}
	
	public static Password createObject(int id, String password, String login, String name) {
		return new Password(id, password, login, name);
	}
	
	public static Password createObject(String password, String login, String name) {
		return new Password(0, password, login, name);
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
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected Entity getEntity() {
		return passwordEntity;
	}

	@Override
	public boolean load(Connection connection) throws SQLException {
		boolean result;
		Statement statement = connection.createStatement();
		result = passwordEntity.load(statement);
		if(result) {
			setPassword(passwordEntity.getPassword());
			setLogin(passwordEntity.getLogin());
			setName(passwordEntity.getName());
		}
		return result;
	}

	@Override
	public boolean update(Connection connection) throws SQLException {
		boolean result;
		passwordEntity.setPassword(password);
		passwordEntity.setLogin(login);
		passwordEntity.setName(name);
		Statement statement = connection.createStatement();
		result = passwordEntity.update(statement);
		return result;
	}

	@Override
	public boolean delete(Connection connection) throws SQLException {
		boolean result = true;
		Statement statement = connection.createStatement();
		if(passwordEntity.exist(statement)) {
			List<PasswordAssignment> passwordAssignments = PasswordAssignment.getAssignmentsByPasswordId(statement, passwordEntity.getId());
			for (PasswordAssignment passwordAssignment : passwordAssignments) {
				result = result && passwordAssignment.delete(statement);
			}
			result = result && passwordEntity.delete(statement);
		}
		return result;
	}
}