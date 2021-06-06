package com.passm.model.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.passm.model.database.DatabaseConnection;
import com.passm.model.entity.Entity;
import com.passm.model.entity.PasswordAssignment;
import com.passm.model.entity.UserEntity;

public class User extends BusinessObject {
	
	private UserEntity userEntity;

	private String name;
	private List<Password> passwords;
	private Password mainPassword;

	private User(int id, String name, List<Password> passwords, Password mainPassword) {
		userEntity = UserEntity.createEntity(id);
		this.name = name;
		this.passwords = passwords;
		this.mainPassword = mainPassword;
	}
	
	public static User createObject(int id, String name, List<Password> passwords, Password mainPassword) {
		return new User(id, name, passwords, mainPassword);
	}
	
	public static User createObject(String name, List<Password> passwords, Password mainPassword) {
		return new User(0, name, passwords, mainPassword);
	}
	
	public static User createObject(int id) {
		return new User(id, null, null, null);
	}
	
	public static User createObject() {
		return new User(0, null, null, null);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Password> getPasswords() {
		return passwords;
	}
	
	public void setPasswords(List<Password> passwords) {
		this.passwords = passwords;
	}
	
	public Password getMainPassword() {
		return mainPassword;
	}
	
	public void setMainPassword(Password mainPassword) {
		this.mainPassword = mainPassword;
	}

	@Override
	protected Entity getEntity() {
		return userEntity;
	}

	@Override
	public boolean load() {
		boolean result = true;
		try(DatabaseConnection databaseConnection = new DatabaseConnection()) {
			Connection connection = databaseConnection.createConnection();
			Statement statement = connection.createStatement();
			result = result && userEntity.load(statement);
			List<PasswordAssignment> passwordAssignments = PasswordAssignment.getAssignemntsByUserId(statement, userEntity.getId());
			List<Password> passwords = new ArrayList<>();
			for (PasswordAssignment passwordAssignment : passwordAssignments) {
				int passwordId = passwordAssignment.getPasswordId();
				Password password = Password.createObject(passwordId);
				result = result && password.load();
				if(userEntity.getMainPassword() == passwordId)
					setMainPassword(password);
				else
					passwords.add(password);
			}
			setPasswords(passwords);
			setName(userEntity.getName());
			connection.commit();
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean update() {
		boolean result = true;
		try(DatabaseConnection databaseConnection = new DatabaseConnection()) {
			Connection connection = databaseConnection.createConnection();
			Statement statement = connection.createStatement();
			
			Set<Integer> passwordIdsInBase = PasswordAssignment.getAssignemntsByUserId(statement, userEntity.getId()).stream()
					.map(passwordAssignment -> passwordAssignment.getPasswordId())
					.collect(Collectors.toSet());
			
			List<Password> allPasswordsInBO = List.copyOf(getPasswords());
			allPasswordsInBO.add(mainPassword);
			for(Password password : allPasswordsInBO) {
				if(!passwordIdsInBase.contains(password.getEntity().getId())) {
					PasswordAssignment passwordAssignment = PasswordAssignment.createEntity(userEntity.getId(), password.getEntity().getId());
					result = result && passwordAssignment.update(statement);
				}
			}
			
			Set<Integer> passwordIdsInBO = passwords.stream()
					.map(password -> password.getEntity().getId())
					.collect(Collectors.toSet());
			
			for (int passwordId : passwordIdsInBase) {
				if(!passwordIdsInBO.contains(passwordId) || passwordId == mainPassword.getEntity().getId()) {
					PasswordAssignment passwordAssignment = PasswordAssignment.createEntity(passwordId);
					result = result && passwordAssignment.delete(statement);
				}
			}
			userEntity.setName(name);
			userEntity.setMainPassword(mainPassword.getEntity().getId());
			result = result && userEntity.update(statement);
			connection.commit();
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}

	@Override
	public boolean delete() {
		boolean result = true;
		try(DatabaseConnection databaseConnection = new DatabaseConnection()) {
			Connection connection = databaseConnection.createConnection();
			Statement statement = connection.createStatement();
			List<PasswordAssignment> passwordAssignments = PasswordAssignment.getAssignemntsByUserId(statement, userEntity.getId());
			for (PasswordAssignment passwordAssignment : passwordAssignments) {
				int passwordId = passwordAssignment.getPasswordId();
				Password password = Password.createObject(passwordId);
				result = result && password.delete();
			}
			result = result && userEntity.delete(statement);
			connection.commit();
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return result;
	}
}
