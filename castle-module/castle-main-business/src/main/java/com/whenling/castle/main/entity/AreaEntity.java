package com.whenling.castle.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.jpa.HierarchicalEntity;

@Entity
@Table(name = "sys_area")
public class AreaEntity extends HierarchicalEntity<UserEntity, Long, AreaEntity> {

	private static final long serialVersionUID = 4432936174595993994L;

	@NotNull
	@Size(min = 1, max = 50)
	@Column(nullable = false, length = 100)
	private String name;

	@NotNull
	@Size(min = 1, max = 250)
	@Column(nullable = false, length = 500)
	private String fullName;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AreaType type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public AreaType getType() {
		return type;
	}

	public void setType(AreaType type) {
		this.type = type;
	}

	public enum AreaType {
		country, province, city, region
	}
}
