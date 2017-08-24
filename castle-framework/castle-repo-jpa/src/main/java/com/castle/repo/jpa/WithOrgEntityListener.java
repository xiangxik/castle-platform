package com.castle.repo.jpa;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Configurable;

import com.castle.core.SpringContext;

@Configurable
public class WithOrgEntityListener {

	private ObjectFactory<WithOrgHandler<?>> handler = new ObjectFactory<WithOrgHandler<?>>() {

		@Override
		public WithOrgHandler<?> getObject() throws BeansException {
			return SpringContext.getBean(WithOrgHandler.class);
		}
	};

	@PrePersist
	public void touchForCreate(Object target) {
		if (handler.getObject() != null) {
			handler.getObject().markCreated(target);
		}
	}

	@PreUpdate
	public void touchForUpdate(Object target) {
		if (handler.getObject() != null) {
			handler.getObject().markModified(target);
		}
	}
}
