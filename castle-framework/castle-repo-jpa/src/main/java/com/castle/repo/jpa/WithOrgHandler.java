package com.castle.repo.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.castle.repo.domain.OrganizationBean;
import com.castle.repo.domain.OrganizationSupport;

@Component
public class WithOrgHandler<T extends OrganizationBean> implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(WithOrgHandler.class);

	@Autowired
	private WithOrgAware<T> withOrgAware;

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
		if (target instanceof OrganizationSupport) {
			touchWithOrg((OrganizationSupport<T>) target, isNew);
		}

	}

	private Object touchWithOrg(OrganizationSupport<T> orgSupport, boolean isNew) {

		if (null == withOrgAware) {
			return null;
		}

		T orgBean = withOrgAware.getCurrentOrg();

		if (isNew) {
			orgSupport.setOrg(orgBean);
		}

		return orgBean;
	}

	public void afterPropertiesSet() {

		if (withOrgAware == null) {
			LOGGER.debug("No WithOrgAware set! Auditing will not be applied!");
		}
	}
}
