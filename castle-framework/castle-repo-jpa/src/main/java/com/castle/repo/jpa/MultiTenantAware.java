package com.castle.repo.jpa;

import com.castle.repo.domain.Tenant;

public interface MultiTenantAware<T extends Tenant> {

	T getCurrentTenant();
}
