package com.castle.repo.jpa;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.castle.repo.domain.OrganizationBean;
import com.castle.repo.domain.OrganizationSupport;

@MappedSuperclass
public class BaseEntityWithOrg<I extends Serializable, O extends OrganizationBean> extends BaseEntity<I> implements OrganizationSupport<O> {

	private static final long serialVersionUID = 142922686974183102L;

	@ManyToOne(fetch = FetchType.LAZY)
	private O org;

	@Override
	public O getOrg() {
		return org;
	}

	@Override
	public void setOrg(O org) {
		this.org = org;
	}

}
