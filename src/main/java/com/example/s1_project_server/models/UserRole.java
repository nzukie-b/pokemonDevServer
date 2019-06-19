package com.example.s1_project_server.models;

public enum UserRole {
	COLLECTOR, TRAINER;
	
	public static UserRole fromString(String text) {
		return UserRole.valueOf(text.toUpperCase());
	}
}
