package com.whenling.castle.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.whenling.castle.repo.jpa.HierarchicalEntity;

/**
 * 部门
 * @author kongxiangxi
 *
 */
@Entity
@Table(name = "sys_department")
public class DepartmentEntity extends HierarchicalEntity<AdminEntity, Long, DepartmentEntity> {

	private static final long serialVersionUID = 4787002872170818536L;

	@NotNull
	@Size(min = 2, max = 100)
	@Column(nullable = false, length = 200)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
