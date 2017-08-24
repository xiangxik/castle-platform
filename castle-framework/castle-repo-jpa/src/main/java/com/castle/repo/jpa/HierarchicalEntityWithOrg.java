package com.castle.repo.jpa;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.castle.repo.domain.OrganizationBean;
import com.castle.repo.domain.OrganizationSupport;

@MappedSuperclass
public class HierarchicalEntityWithOrg<U, I extends Serializable, T, O extends OrganizationBean> extends HierarchicalEntity<U, I, T>
		implements OrganizationSupport<O> {

	private static final long serialVersionUID = 3064492817473661543L;

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
