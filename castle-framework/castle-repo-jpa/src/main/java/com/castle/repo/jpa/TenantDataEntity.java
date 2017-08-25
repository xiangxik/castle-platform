package com.castle.repo.jpa;

import java.io.Serializable;

import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.castle.repo.domain.MultiTenant;
import com.castle.repo.domain.Tenant;

@MappedSuperclass
@EntityListeners(value = { TenantEntityListener.class })
public class TenantDataEntity<U, I extends Serializable, O extends Tenant> extends DataEntity<U, I> implements MultiTenant<O> {

	private static final long serialVersionUID = 5326836642707704479L;

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
