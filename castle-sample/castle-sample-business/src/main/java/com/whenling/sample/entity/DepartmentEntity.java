package com.whenling.sample.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.whenling.castle.repo.jpa.HierarchicalEntity;

@Entity
@Table(name = "tbl_department")
public class DepartmentEntity extends HierarchicalEntity<AdminEntity, Long, DepartmentEntity> {

	private static final long serialVersionUID = -919944153679576971L;

	@NotNull
	private String name;
	private String phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
