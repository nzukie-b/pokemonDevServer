package com.example.s1_project_server.models;

public enum UserType {
	COLLECTOR, TRAINER;
	
	public static UserType fromString(String text) {
		return UserType.valueOf(text.toUpperCase());
	}
}
