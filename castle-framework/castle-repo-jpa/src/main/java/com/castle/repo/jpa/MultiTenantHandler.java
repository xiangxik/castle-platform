package com.castle.repo.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.castle.repo.domain.MultiTenant;
import com.castle.repo.domain.Tenant;

@Component
public class MultiTenantHandler<T extends Tenant> implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(MultiTenantHandler.class);

	@Autowired(required = false)
	private MultiTenantAware<T> multiTenantAware;

	public void markCreated(Object source) {
		touch(source, true);
	}

	public void markModified(Object source) {
		touch(source, false);
	}

	@SuppressWarnings("unchecked")
	private void touch(Object target, boolean isNew) {
		if (target == null) {
			return;
		}
		if (target instanceof MultiTenant) {
			touchWithOrg((MultiTenant<T>) target, isNew);
		}

	}

	private Object touchWithOrg(MultiTenant<T> orgSupport, boolean isNew) {

		if (null == multiTenantAware) {
			return null;
		}

		T orgBean = multiTenantAware.getCurrentTenant();

		if (isNew) {
			orgSupport.setTenant(orgBean);
		}

		return orgBean;
	}

	public void afterPropertiesSet() {

		if (multiTenantAware == null) {
			LOGGER.debug("No MultiTenantAware set! Auditing will not be applied!");
		}
	}
}
