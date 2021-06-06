package com.passm.general;

import java.sql.Connection;
import java.sql.Statement;

import com.passm.model.database.DatabaseConnection;
import com.passm.model.database.DatabaseCreator;
import com.passm.model.entity.PasswordEntity;
import com.passm.model.entity.UserEntity;

public class ProgramStarter {

	public static void main(String[] args) {
		DatabaseCreator databaseCreator = new DatabaseCreator();
		databaseCreator.createDatabase();
		
		try(DatabaseConnection databaseConnection = new DatabaseConnection()) {
			Connection connection = databaseConnection.createConnection();
			Statement statement1 = connection.createStatement();
			PasswordEntity passwordEntity = PasswordEntity.createEntity("test_password_123", "name_123", "this is my first test password");
			passwordEntity.update(statement1);
			
			Statement statement2 = connection.createStatement();
			UserEntity userEntity = UserEntity.createEntity("Me", passwordEntity.getId());
			userEntity.update(statement2);
			
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Saved");
		
		try(DatabaseConnection databaseConnection = new DatabaseConnection()) {
			Connection connection = databaseConnection.createConnection();
			Statement statement2 = connection.createStatement();
			UserEntity userEntity = UserEntity.createEntity(1);
			userEntity.load(statement2);
			userEntity.setName("new Name");
			userEntity.update(statement2);
			
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Updated");
		
		try(DatabaseConnection databaseConnection = new DatabaseConnection()) {
			Connection connection = databaseConnection.createConnection();
			Statement statement = connection.createStatement();
			UserEntity userEntity = UserEntity.createEntity(1);
			userEntity.load(statement);
			System.out.println(userEntity.getName());
			userEntity.delete(statement);
			
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Deleted");
		
		try(DatabaseConnection databaseConnection = new DatabaseConnection()) {
			Connection connection = databaseConnection.createConnection();
			Statement statement = connection.createStatement();
			UserEntity userEntity = UserEntity.createEntity(1);
			System.out.println(userEntity.exist(statement));
			userEntity.load(statement);
			
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
