package com.passm.model.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.passm.model.entity.Entity;
import com.passm.model.entity.PasswordAssignment;
import com.passm.model.entity.UserEntity;

public class User extends BusinessObject {
	
	private final UserEntity userEntity;

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
	
	public static User createObject(String name, Password mainPassword) {
		return new User(0, name, new ArrayList<>(), mainPassword);
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
	
	public void addPassword(Password password) {
		this.passwords.add(password);
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
	public boolean load(Connection connection) throws SQLException {
		boolean result;
		Statement statement = connection.createStatement();
		result = userEntity.load(statement);
		List<PasswordAssignment> passwordAssignments = PasswordAssignment.getAssignmentsByUserId(statement, userEntity.getId());
		List<Password> passwords = new ArrayList<>();
		for (PasswordAssignment passwordAssignment : passwordAssignments) {
			int passwordId = passwordAssignment.getPasswordId();
			Password password = Password.createObject(passwordId);
			result = result && password.load(connection);
			passwords.add(password);
		}
		Password mainPassword = Password.createObject(userEntity.getMainPassword());
		result = result && mainPassword.load(connection);
		setMainPassword(mainPassword);
		setPasswords(passwords);
		setName(userEntity.getName());
		return result;
	}

	@Override
	public boolean update(Connection connection) throws SQLException {
		boolean result;
		Statement statement = connection.createStatement();
		
		userEntity.setName(name);
		userEntity.setMainPassword(mainPassword.getId());
		result = userEntity.update(statement);
		
		List<PasswordAssignment> passwordAssignmentsInBase = PasswordAssignment.getAssignmentsByUserId(statement, userEntity.getId());
		
		List<Integer> passwordIdsInBase = passwordAssignmentsInBase.stream()
				.map(PasswordAssignment::getPasswordId)
				.collect(Collectors.toList());
		
		for(Password password : getPasswords()) {
			if(!passwordIdsInBase.contains(password.getId())) {
				PasswordAssignment passwordAssignment = PasswordAssignment.createEntity(userEntity.getId(), password.getId());
				result = result && passwordAssignment.update(statement);
			}
		}
		
		Set<Integer> passwordIdsInBO = passwords.stream()
				.map(BusinessObject::getId)
				.collect(Collectors.toSet());
		
		for (int passwordId : passwordIdsInBase) {
			if(!passwordIdsInBO.contains(passwordId)) {
				int passwordAssignmentId = passwordAssignmentsInBase.get(passwordIdsInBase.indexOf(passwordId)).getId();
				PasswordAssignment passwordAssignment = PasswordAssignment.createEntity(passwordAssignmentId, userEntity.getId(), passwordId);
				result = result && passwordAssignment.delete(statement);
			}
		}
		return result;
	}

	@Override
	public boolean delete(Connection connection) throws SQLException {
		boolean result = true;
		Statement statement = connection.createStatement();
		List<PasswordAssignment> passwordAssignments = PasswordAssignment.getAssignmentsByUserId(statement, userEntity.getId());
		for (PasswordAssignment passwordAssignment : passwordAssignments) {
			int passwordId = passwordAssignment.getPasswordId();
			Password password = Password.createObject(passwordId);
			result = result && password.delete(connection);
		}
		mainPassword.delete(connection);
		result = result && userEntity.delete(statement);
		return result;
	}
}
