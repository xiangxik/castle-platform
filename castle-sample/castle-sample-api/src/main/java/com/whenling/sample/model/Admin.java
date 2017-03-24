package com.whenling.sample.model;

public class Admin extends Domain<Long> {

	private static final long serialVersionUID = 1216009126272535713L;

	private String username;
	private String name;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
