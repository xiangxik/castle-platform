package com.castle.repo.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.util.ClassUtils;

import com.google.common.base.Strings;

public class BaseDoc implements Persistable<String> {

	private static final long serialVersionUID = -4787123015101912920L;

	@Id
	private String id;

	@Override
	public String getId() {
		return id;
	}

	protected void setId(final String id) {
		this.id = id;
	}

	@Override
	public boolean isNew() {
		return Strings.isNullOrEmpty(getId());
	}

	@Override
	public String toString() {
		return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
	}

	@Override
	public int hashCode() {
		int hashCode = 17;
		hashCode += null == getId() ? 0 : getId().hashCode() * 31;
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {

		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		Class<?> thisClass = ClassUtils.getUserClass(getClass());
		Class<?> objClass = ClassUtils.getUserClass(obj.getClass());

		if (!(ClassUtils.isAssignable(thisClass, objClass) || ClassUtils.isAssignable(objClass, thisClass))) {
			return false;
		}

		BaseDoc that = (BaseDoc) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());

	}

}
