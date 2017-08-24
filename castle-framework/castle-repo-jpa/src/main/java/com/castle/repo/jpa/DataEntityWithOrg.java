package com.castle.repo.jpa;

import java.io.Serializable;

import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.castle.repo.domain.OrganizationBean;
import com.castle.repo.domain.OrganizationSupport;

@MappedSuperclass
@EntityListeners(value = { WithOrgEntityListener.class })
public class DataEntityWithOrg<U, I extends Serializable, O extends OrganizationBean> extends DataEntity<U, I> implements OrganizationSupport<O> {

	private static final long serialVersionUID = 5326836642707704479L;

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
