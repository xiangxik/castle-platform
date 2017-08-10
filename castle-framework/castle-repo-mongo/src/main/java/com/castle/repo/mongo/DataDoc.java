package com.castle.repo.mongo;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.data.domain.Auditable;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class DataDoc<U> extends BaseDoc implements Auditable<U, String> {

	private static final long serialVersionUID = -6114318783615243294L;

	@DBRef(lazy = true)
	private U createdBy;

	private Date createdDate;

	@DBRef(lazy = true)
	private U lastModifiedBy;

	private Date lastModifiedDate;

	@Override
	public U getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(final U createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public DateTime getCreatedDate() {
		return null == createdDate ? null : new DateTime(createdDate);
	}

	@Override
	public void setCreatedDate(final DateTime createdDate) {
		this.createdDate = null == createdDate ? null : createdDate.toDate();
	}

	@Override
	public U getLastModifiedBy() {
		return lastModifiedBy;
	}

	@Override
	public void setLastModifiedBy(final U lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Override
	public DateTime getLastModifiedDate() {
		return null == lastModifiedDate ? null : new DateTime(lastModifiedDate);
	}

	@Override
	public void setLastModifiedDate(DateTime lastModifiedDate) {
		this.lastModifiedDate = null == lastModifiedDate ? null : lastModifiedDate.toDate();
	}
}
