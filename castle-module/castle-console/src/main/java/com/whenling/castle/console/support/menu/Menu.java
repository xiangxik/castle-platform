package com.whenling.castle.console.support.menu;

import java.util.List;

public class Menu {

	private String name;

	private String type;

	private String description;

	private List<MenuItem> roots;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<MenuItem> getRoots() {
		return roots;
	}

	public void setRoots(List<MenuItem> roots) {
		this.roots = roots;
	}

}
