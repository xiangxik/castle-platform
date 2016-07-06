package com.whenling.castle.main.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public interface AreaSupport {

	AreaEntity getArea();

	void setArea(AreaEntity area);

	String getAreaName();

	void setAreaName(String areaName);

	@PrePersist
	default void prePersist() {
		if (getArea() != null) {
			setAreaName(getArea().getFullName());
		}
	}

	@PreUpdate
	default void preUpdate() {
		if (getArea() != null) {
			setAreaName(getArea().getFullName());
		}
	}
}
