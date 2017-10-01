package com.castle.repo.jpa;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.castle.repo.domain.MultiTenant;
import com.castle.repo.domain.Tenant;

@MappedSuperclass
public class TenantEntity<I extends Serializable, O extends Tenant> extends BaseEntity<I> implements MultiTenant<O> {

	private static final long serialVersionUID = 142922686974183102L;

	@ManyToOne(fetch = FetchType.LAZY)
	private O tenant;

	@Override
	public O getTenant() {
		return tenant;
	}

	@Override
	public void setTenant(O tenant) {
		this.tenant = tenant;
	}

}
