package com.castle.repo.jpa;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.castle.repo.domain.Tenant;
import com.castle.repo.domain.MultiTenant;

@MappedSuperclass
public class TenantHierarchicalEntity<U, I extends Serializable, T, O extends Tenant> extends HierarchicalEntity<U, I, T>
		implements MultiTenant<O> {

	private static final long serialVersionUID = 3064492817473661543L;

	@ManyToOne(fetch = FetchType.LAZY)
	private O org;

	@Override
	public O getTenant() {
		return org;
	}

	@Override
	public void setTenant(O org) {
		this.org = org;
	}

}
