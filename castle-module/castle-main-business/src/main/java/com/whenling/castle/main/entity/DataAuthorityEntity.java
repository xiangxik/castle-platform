package com.whenling.castle.main.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.whenling.castle.repo.jpa.BaseEntity;

@Entity
@Table(name = "sys_data_authority")
public class DataAuthorityEntity extends BaseEntity<Long> {

	private static final long serialVersionUID = -1795714437229357885L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_id")
	private DataResourceEntity resource;

	private String businessActions;

	public DataResourceEntity getResource() {
		return resource;
	}

	public void setResource(DataResourceEntity resource) {
		this.resource = resource;
	}

	public String getBusinessActions() {
		return businessActions;
	}

	public void setBusinessActions(String businessActions) {
		this.businessActions = businessActions;
	}

}
